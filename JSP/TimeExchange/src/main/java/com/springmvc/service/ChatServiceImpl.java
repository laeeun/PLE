package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;
import com.springmvc.domain.Member;
import com.springmvc.enums.MessageType;
import com.springmvc.repository.ChatRepository;
import com.springmvc.repository.MemberRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public List<ChatEntity> findMessagesByRoomId(String roomId) {
		return chatRepository.findMessagesByRoomId(roomId);
	}

	@Override
	public List<ChatListDTO> findChatListByUserId(String userId) {
		return chatRepository.findChatListByUserId(userId);
	}

	@Override
	public String findRoomIdByUserIds(String user1Id, String user2Id) {
		return chatRepository.findRoomIdByUserIds(user1Id, user2Id);
	}

	@Override
	public String createChatRoom(String user1Id, String user2Id) {
		return chatRepository.createChatRoom(user1Id, user2Id);
	}

	@Override
	public boolean existsRoom(String user1Id, String user2Id) {
		return chatRepository.existsRoom(user1Id, user2Id);
	}

	@Override
	public void saveMessage(ChatMessage message) {
		ChatEntity entity = new ChatEntity(message.getRoomId(), message.getSenderId(), message.getReceiverId(),
				message.getContent(), message.getType().name(), message.getCreatedAt(), message.getFileUrl(), false,
				false);
		chatRepository.saveMessage(entity);
	}

	@Override
	public List<ChatMessage> getAllMessagesByMemberId(String memberId) {
		List<ChatEntity> entities = chatRepository.findAllMessagesByMemberId(memberId);

		return entities.stream().map(this::toDto).toList();
	}

	private ChatMessage toDto(ChatEntity e) {
		ChatMessage m = new ChatMessage();
		m.setMessageId(e.getId() + "");
		m.setRoomId(e.getRoomId());
		m.setSenderId(e.getSenderId());
		m.setReceiverId(e.getReceiverId());
		m.setSenderName(e.getSenderName());
		m.setSenderProfileImage(e.getSenderProfileImage());
		m.setReceiverName(e.getReceiverName());
		m.setReceiverProfileImage(e.getReceiverProfileImage());
		m.setContent(e.getContent());
		m.setType(MessageType.valueOf(e.getType()));
		m.setCreatedAt(e.getCreatedAt());
		m.setRead(e.isRead());
		return m;
	}

	@Override
	public Member findMemberById(String memberId) {
		return memberRepository.findById(memberId);
	}

	@Override
	public void deleteChatRoomById(String roomId) {
		chatRepository.deleteChatRoomById(roomId);
	}

	@Override
	public void markMessagesAsRead(String roomId, String receiverId) {
		chatRepository.markMessagesAsRead(roomId, receiverId);
	}

	@Override
	public List<String> findSendersWithUnreadMessages(String roomId, String userId) {
		return chatRepository.findSendersWithUnreadMessages(roomId, userId);
	}

	@Override
	public boolean existsRoom(String roomId) {
		return chatRepository.existsRoom(roomId);
	}

	@Override
	public int countUnreadMessages(String memberId) {
		return chatRepository.countUnreadMessages(memberId);
	}

}
