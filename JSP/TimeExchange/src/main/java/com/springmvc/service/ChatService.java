package com.springmvc.service;

import java.util.List;

import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;

public interface ChatService {
    void saveMessage(ChatMessage message);
    
    List<ChatListDTO> getChatListByMemberId(String memberId);
}
