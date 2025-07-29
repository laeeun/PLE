package com.springmvc.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;
import com.springmvc.domain.Member;
import com.springmvc.domain.MessageType;
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

        // ✅ 이미 존재하는 방인지 확인
        String roomId = chatService.findRoomIdByUserIds(message.getSenderId(), message.getReceiverId());

        // ❗ 없으면 새로 생성
        if (roomId == null) {
            roomId = chatService.createChatRoom(message.getSenderId(), message.getReceiverId());
        }

        // ✅ 메시지에 roomId 설정
        message.setRoomId(roomId);

        // ✅ 메시지 저장 및 전송
        chatService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }

    
    @GetMapping("/list")
    public String chatListPage(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("loggedInId");
        
        // ✅ 디버깅용 로그 출력
        System.out.println("로그인한 유저 ID: " + memberId);

        List<ChatListDTO> chatList = chatService.findChatListByUserId(memberId);
        System.out.println("조회된 채팅방 수: " + chatList.size()); // 로그 찍기

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

        // ✅ roomId에서 sender/receiver 추출하는 로직이 필요해!
        // 예: 222_111 → 둘 중 하나가 sender, 하나가 receiver니까
        String[] parts = roomId.split("_");
        String receiverId = parts[0].equals(senderId) ? parts[1] : parts[0];

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
            messages.add(msg);
        }

        model.addAttribute("roomId", roomId);
        model.addAttribute("receiverId", receiverId);
        model.addAttribute("senderId", senderId);
        model.addAttribute("messages", messages);
        return "chat";
    }



    // 예시: 1:1 채팅방 ID 생성 규칙
    private String generateRoomId(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
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
            msg.setType(MessageType.valueOf(entity.getType())); // enum 변환
            msg.setCreatedAt(entity.getCreatedAt());
            // 필요 시 프로필 이미지 등 추가 세팅
            messages.add(msg);
        }

        return messages;
    }
}
