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
        ChatEntity entity = new ChatEntity(
                message.getRoomId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getType().name(),
                message.getCreatedAt()
            );

            chatRepository.save(entity);    
    }
    
    @Override
    public List<ChatListDTO> getChatListByMemberId(String memberId) {
        return chatRepository.findChatListByMemberId(memberId);
    }
    
}
