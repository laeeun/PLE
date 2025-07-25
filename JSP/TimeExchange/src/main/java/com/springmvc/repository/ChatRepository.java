package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;

public interface ChatRepository {
    void save(ChatEntity message);

    List<ChatEntity> findByRoomId(String roomId);
    
    List<ChatListDTO> findChatListByMemberId(String memberId);
}
