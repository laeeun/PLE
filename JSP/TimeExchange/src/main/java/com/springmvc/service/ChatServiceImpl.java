package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;
import com.springmvc.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
    private ChatRepository chatRepository;

    

	@Override
	public void saveMessage(ChatMessage message) {
	    Long roomId = Long.parseLong(message.getRoomId());

	    ChatEntity entity = new ChatEntity(
	        roomId,
	        message.getSenderId(),
	        message.getReceiverId(),
	        message.getContent(),
	        message.getType().name(),
	        message.getCreatedAt(),
	        message.getFileUrl(),
	        false,  // 읽음 여부
	        false   // 삭제 여부
	    );

	    chatRepository.saveMessage(entity);
	}



	@Override
	public List<ChatListDTO> getChatListByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
}
