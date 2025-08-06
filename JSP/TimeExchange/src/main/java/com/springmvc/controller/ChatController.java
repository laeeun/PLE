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
import com.springmvc.domain.MessageType;
import com.springmvc.service.ChatService;

@Controller // 스프링이 이 클래스를 웹 요청 처리 컨트롤러로 인식하도록 지정
@RequestMapping("/chat") // 이 클래스 내의 모든 요청 URL은 "/chat"으로 시작함
public class ChatController {

	@Autowired
	private ChatService chatService; // 채팅 관련 비즈니스 로직을 처리하는 서비스 계층

	@Autowired
	private SimpMessagingTemplate messagingTemplate; // WebSocket 메시지를 전송하는 객체

	/**
     * 📩 클라이언트로부터 메시지 수신 → DB 저장 및 실시간 전송
     */
    @MessageMapping("/chat.sendMessage") // 클라이언트에서 "/app/chat.sendMessage"로 보낸 메시지를 처리
    public void sendMessage(@Payload ChatMessage message) {
        message.setCreatedAt(LocalDateTime.now()); // 메시지 보낸 시각 설정

        // 🔸 roomId 확인 또는 생성
        String roomId = message.getRoomId();
        if (roomId == null || roomId.isBlank() || !chatService.existsRoom(roomId)) {
            roomId = chatService.findRoomIdByUserIds(message.getSenderId(), message.getReceiverId());
            if (roomId == null) {
                roomId = chatService.createChatRoom(message.getSenderId(), message.getReceiverId());
            }
        }
        message.setRoomId(roomId);

        // 🔸 DB에 메시지 저장
        chatService.saveMessage(message);

        // 🔸 발신자 정보 조회 후 세팅
        Member sender = chatService.findMemberById(message.getSenderId());
        if (sender != null) {
            message.setSenderName(sender.getUserName());
            String profile = sender.getProfileImage();
            message.setSenderProfileImage((profile != null && !profile.startsWith("/upload/")) ? "/upload/" + profile : profile);
        }

        // 🔸 수신자 정보 조회 후 세팅
        Member receiver = chatService.findMemberById(message.getReceiverId());
        if (receiver != null) {
            message.setReceiverName(receiver.getUserName());
            String profile = receiver.getProfileImage();
            message.setReceiverProfileImage((profile != null && !profile.startsWith("/upload/")) ? "/upload/" + profile : profile);
        }

        // 🔸 채팅방 구독자에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);

        // 🔸 수신자의 알림창에도 메시지 전송 (팝업 알림)
        messagingTemplate.convertAndSend("/topic/chat/" + message.getReceiverId(), message);
    }

	/**
	 * ✅ 채팅방 입장 처리 (읽음 처리 + 읽음 알림 전송)
	 */
	@MessageMapping("/chat.enter")
	public void enterRoom(ChatEnterDTO dto) {
		chatService.markMessagesAsRead(dto.getRoomId(), dto.getUserId()); // 안 읽은 메시지를 읽음 처리

		List<String> senderIds = chatService.findSendersWithUnreadMessages(dto.getRoomId(), dto.getUserId());
		for (String senderId : senderIds) {
			messagingTemplate.convertAndSend("/topic/read/" + senderId, dto.getRoomId()); // 보낸 사람에게 읽음 알림
		}
	}

	/**
	 * 📥 채팅 시작 요청 → 채팅방 생성 또는 조회 후 이동
	 */
	@GetMapping("/start")
	public String startChat(@RequestParam("receiverId") String receiverId, HttpSession session) {
		Member sender = (Member) session.getAttribute("loggedInUser");
		String senderId = sender.getMember_id();

		// 기존 채팅방 있는지 확인
		String roomId = chatService.findRoomIdByUserIds(senderId, receiverId);

		if (roomId == null) {
			// 없으면 새로 만듦
			roomId = chatService.createChatRoom(senderId, receiverId);
		}

		// 채팅방 페이지로 리다이렉트
		return "redirect:/chat/room?roomId=" + roomId;
	}

	/**
	 * 💬 채팅 목록 페이지 (내가 참여 중인 채팅방 리스트)
	 */
	@GetMapping("/list")
	public String chatListPage(HttpSession session, Model model) {
		String memberId = (String) session.getAttribute("loggedInId"); // 로그인한 유저 ID

		System.out.println("로그인한 유저 ID: " + memberId); // 디버깅 로그

		List<ChatListDTO> chatList = chatService.findChatListByUserId(memberId); // DB에서 채팅 목록 조회
		System.out.println("조회된 채팅방 수: " + chatList.size());
		for (ChatListDTO dto : chatList) {
			System.out.println("마지막메시지타임 : " + dto.getLastMessageTime()); // 마지막 메시지 시간 디버깅
		}

		model.addAttribute("chatList", chatList); // 뷰에 전달

		return "chatList"; // JSP: /WEB-INF/views/chatList.jsp
	}

	/**
	 * 💌 채팅방 입장 화면 (채팅 내역 로딩 + 읽음 처리)
	 */
	@GetMapping("/room")
	public String enterChatRoom(@RequestParam("roomId") String roomId, HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loggedInUser");
		if (loginUser == null)
			return "redirect:/login"; // 로그인 안 한 경우 로그인 페이지로

		String senderId = loginUser.getMember_id();

		// 🔸 roomId 형식 검증
		if (roomId == null || !roomId.contains("_"))
			return "redirect:/chat/list";
		String[] parts = roomId.split("_");
		if (parts.length < 2)
			return "redirect:/chat/list";

		// 🔸 상대방 ID 결정
		String receiverId = parts[0].equals(senderId) ? parts[1] : parts[0];

		// 🔸 읽음 처리
		chatService.markMessagesAsRead(roomId, senderId);

		// 🔸 메시지 불러오기 (엔티티 → 메시지 변환)
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
			System.out.println("📨 마지막 채팅 시간: " + messages.get(0).getCreatedAt()); // 디버깅
		}

		// 🔸 뷰로 데이터 전달
		model.addAttribute("roomId", roomId);
		model.addAttribute("receiverId", receiverId);
		model.addAttribute("senderId", senderId);
		model.addAttribute("messages", messages);

		return "chat"; // JSP: /WEB-INF/views/chat.jsp
	}

	/**
	 * 📤 AJAX 요청으로 채팅 메시지 불러오기 (비동기 메시지 로딩)
	 */
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

			// 🔸 발신자 정보 설정
			Member sender = chatService.findMemberById(entity.getSenderId());
			if (sender != null) {
				msg.setSenderName(sender.getUserName());
				String profile = sender.getProfileImage();
				if (profile != null) {
					msg.setSenderProfileImage(
							profile.startsWith("/upload/profile/") ? profile : "/upload/profile/" + profile);
				}
			}

			// 🔸 수신자 정보 설정
			Member receiver = chatService.findMemberById(entity.getReceiverId());
			if (receiver != null) {
				msg.setReceiverName(receiver.getUserName());
				String profile = receiver.getProfileImage();
				if (profile != null) {
					msg.setReceiverProfileImage(
							profile.startsWith("/upload/profile/") ? profile : "/upload/profile/" + profile);
				}
			}

			messages.add(msg);
		}

		return messages; // JSON 형태로 반환
	}

	/**
	 * 🗑️ 채팅방 삭제 요청 (내가 삭제하면 DB에서 삭제됨)
	 */
	@PostMapping("/deleteRoom")
	public String deleteChatRoom(@RequestParam("roomId") String roomId, RedirectAttributes redirectAttributes) {
		chatService.deleteChatRoomById(roomId); // DB에서 해당 채팅방 삭제
		redirectAttributes.addFlashAttribute("success", "채팅방이 삭제되었습니다.");
		return "redirect:/chat/list"; // 다시 채팅 리스트로
	}
}
