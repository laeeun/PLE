package com.springmvc.repository;

import java.util.List;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;

public interface ChatRepository {

    // 💬 메시지 저장
    void saveMessage(ChatEntity message);

    // 💬 채팅방 내 메시지 전체 조회
    List<ChatEntity> findMessagesByRoomId(String roomId);

    // 📩 채팅방 목록 (내가 참여한 방들)
    List<ChatListDTO> findChatListByUserId(String userId);

    // 🧠 채팅방 ID 찾기 (이미 있는지 확인)
    String findRoomIdByUserIds(String user1Id, String user2Id);

    // ➕ 없으면 새 방 생성
    String createChatRoom(String user1Id, String user2Id);

    // 🧠 혹시 chat_room 전체 조회 필요 시
    boolean existsRoom(String user1Id, String user2Id);
    
    List<ChatEntity> findAllMessagesByMemberId(String memberId);
}
