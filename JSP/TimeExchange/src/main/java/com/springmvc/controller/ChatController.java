package com.springmvc.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.domain.ChatEnterDTO;
import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;
import com.springmvc.domain.Member;
import com.springmvc.enums.MessageType;
import com.springmvc.service.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
    private ChatService chatService;
	

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage message) {
        message.setCreatedAt(LocalDateTime.now());

        // roomId 설정
        String roomId = chatService.findRoomIdByUserIds(message.getSenderId(), message.getReceiverId());
        if (roomId == null) {
            roomId = chatService.createChatRoom(message.getSenderId(), message.getReceiverId());
        }
        message.setRoomId(roomId);

        // DB 저장
        chatService.saveMessage(message);

        // sender 정보 세팅
        Member sender = chatService.findMemberById(message.getSenderId());
        if (sender != null) {
            message.setSenderName(sender.getUserName());
            String profile = sender.getProfileImage();
            message.setSenderProfileImage(
                (profile != null && !profile.startsWith("/upload/")) ? "/upload/" + profile : profile
            );
        }

        // receiver 정보 세팅
        Member receiver = chatService.findMemberById(message.getReceiverId());
        if (receiver != null) {
            message.setReceiverName(receiver.getUserName());
            String profile = receiver.getProfileImage();
            message.setReceiverProfileImage(
                (profile != null && !profile.startsWith("/upload/")) ? "/upload/" + profile : profile
            );
        }

        
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);

        // ✅ 알림 팝업을 보는 사용자에게도 메시지 전송 (받는 사람만!)
        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiverId(), message);
    }

    
    
    @MessageMapping("/chat.enter")
    public void enterRoom(ChatEnterDTO dto) {
        // 1. 안읽은 메시지 read = true + readAt 시간 업데이트
        chatService.markMessagesAsRead(dto.getRoomId(), dto.getUserId());

        // 2. 그 메시지들을 보낸 사람에게 STOMP로 "읽음 알림" 전송
        List<String> senderIds = chatService.findSendersWithUnreadMessages(dto.getRoomId(), dto.getUserId());

        for (String senderId : senderIds) {
            messagingTemplate.convertAndSend("/topic/read/" + senderId, dto.getRoomId());
        }
    }

    @GetMapping("/start")
    public String startChat(@RequestParam("receiverId") String receiverId, HttpSession session) {
        Member sender = (Member) session.getAttribute("loggedInUser");
        String senderId = sender.getMember_id();

        // ✅ 채팅방 있는지 확인
        String roomId = chatService.findRoomIdByUserIds(senderId, receiverId);

        if (roomId == null) {
            // ✅ 없으면 생성
            roomId = chatService.createChatRoom(senderId, receiverId);
        }

        // ✅ 채팅방으로 리디렉트
        return "redirect:/chat/room?roomId=" + roomId;
    }	


    
    @GetMapping("/list")
    public String chatListPage(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loggedInId");
        
        // ✅ 디버깅용 로그 출력
        System.out.println("로그인한 유저 ID: " + memberId);

        List<ChatListDTO> chatList = chatService.findChatListByUserId(memberId);
        System.out.println("조회된 채팅방 수: " + chatList.size()); // 로그 찍기
        for(int i = 0; i < chatList.size(); i++) {
        	System.out.println("마지막메시지타임 : "+chatList.get(i).getLastMessageTime());
        }
        model.addAttribute("chatList", chatList);
        
        return "chatList"; 
    }
    
    @GetMapping("/room")
    public String enterChatRoom(@RequestParam("roomId") String roomId,
                                HttpSession session,
                                Model model) {
        Member loginUser = (Member) session.getAttribute("loggedInUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        String senderId = loginUser.getMember_id();

        // ✅ roomId 유효성 체크 (null 또는 "_" 포함 안 된 경우 방어)
        if (roomId == null || !roomId.contains("_")) {
            System.out.println("❌ 유효하지 않은 roomId: " + roomId);
            return "redirect:/chat/list"; // 오류 시 채팅 목록으로
        }

        String[] parts = roomId.split("_");
        if (parts.length < 2) {
            System.out.println("❌ roomId split 실패: " + roomId);
            return "redirect:/chat/list"; // 방 생성 오류 방지
        }

        // ✅ 상대방 아이디 추출
        String receiverId = parts[0].equals(senderId) ? parts[1] : parts[0];

        // ✅ 읽음 처리
        chatService.markMessagesAsRead(roomId, senderId);

        // ✅ 메시지 불러오기
        List<ChatEntity> entities = chatService.findMessagesByRoomId(roomId);
        List<ChatMessage> messages = new ArrayList<>();
        for (ChatEntity entity : entities) {
            ChatMessage msg = new ChatMessage();
            msg.setRoomId(entity.getRoomId());
            msg.setSenderId(entity.getSenderId());
            msg.setReceiverId(entity.getReceiverId());
            msg.setContent(entity.getContent());
            msg.setType(MessageType.valueOf(entity.getType()));
            msg.setCreatedAt(entity.getCreatedAt());
            msg.setRead(entity.isRead());
            messages.add(msg);
        }

        if (!messages.isEmpty()) {
            System.out.println("📨 마지막 채팅 시간: " + messages.get(0).getCreatedAt());
        }
        
        

        // ✅ 뷰에 전달
        model.addAttribute("roomId", roomId);
        model.addAttribute("receiverId", receiverId);
        model.addAttribute("senderId", senderId);
        model.addAttribute("messages", messages);

        return "chat";
    }



    @GetMapping("/messages")
    @ResponseBody
    public List<ChatMessage> getMessages(@RequestParam("roomId") String roomId) {
        List<ChatEntity> entities = chatService.findMessagesByRoomId(roomId);
        List<ChatMessage> messages = new ArrayList<>();
        
        
        for (ChatEntity entity : entities) {
            ChatMessage msg = new ChatMessage();
            msg.setRoomId(entity.getRoomId());
            msg.setSenderId(entity.getSenderId());
            msg.setReceiverId(entity.getReceiverId());
            msg.setContent(entity.getContent());
            msg.setType(MessageType.valueOf(entity.getType()));
            msg.setCreatedAt(entity.getCreatedAt());
            msg.setRead(entity.isRead());

            // ✅ sender 정보 설정
            Member sender = chatService.findMemberById(entity.getSenderId());
            if (sender != null) {
                msg.setSenderName(sender.getUserName());

                String profile = sender.getProfileImage();
                if (profile != null) {
                    // ⚠ 기본 이미지나 사용자 이미지 모두 upload 경로 붙여서 처리
                	msg.setSenderProfileImage(profile.startsWith("/upload/profile/")
                		    ? profile
                		    : "/upload/profile/" + profile);
                }
            }
            
            
            // ✅ receiver 정보 설정
            Member receiver = chatService.findMemberById(entity.getReceiverId());
            if (receiver != null) {
                msg.setReceiverName(receiver.getUserName());

                String profile = receiver.getProfileImage();
                if (profile != null) {
                	msg.setReceiverProfileImage(profile.startsWith("/upload/profile/")
                		    ? profile
                		    : "/upload/profile/" + profile);
                }
            }

            messages.add(msg);
            
        }
        
        

        return messages;
    }

    @PostMapping("/deleteRoom")
    public String deleteChatRoom(@RequestParam("roomId") String roomId, RedirectAttributes redirectAttributes) {
        chatService.deleteChatRoomById(roomId);
        redirectAttributes.addFlashAttribute("success", "채팅방이 삭제되었습니다.");
        return "redirect:/chat/list";
    }

}
