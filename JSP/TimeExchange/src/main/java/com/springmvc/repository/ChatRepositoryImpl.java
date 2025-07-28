package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;

@Repository
public class ChatRepositoryImpl implements ChatRepository {

    @Autowired
    private JdbcTemplate template;

    // 💬 메시지 저장
    @Override
    public void saveMessage(ChatEntity message) {
        String sql = """
            INSERT INTO chat_message
            (room_id, sender_id, receiver_id, message, type, created_at, file_url, read, deleted)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        template.update(sql,
                message.getRoomId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getType(),
                message.getCreatedAt(),
                message.getFileUrl(),
                message.isRead(),
                message.isDeleted()
        );
    }

    // 💬 채팅방 메시지 조회
    @Override
    public List<ChatEntity> findMessagesByRoomId(Long roomId) {
        String sql = "SELECT * FROM chat_message WHERE room_id = ? AND deleted = FALSE ORDER BY created_at ASC";
        return template.query(sql, new ChatRowMapper(), roomId);
    }

    // 📩 채팅방 목록 조회
    @Override
    public List<ChatListDTO> findChatListByUserId(String memberId) {
        String sql = """
            SELECT cm.room_id,
                   CASE WHEN cm.sender_id = ? THEN cm.receiver_id ELSE cm.sender_id END AS partner_name,
                   cm.message AS last_message,
                   cm.created_at AS last_message_time
            FROM chat_message cm
            JOIN (
                SELECT room_id, MAX(created_at) AS max_time
                FROM chat_message
                WHERE sender_id = ? OR receiver_id = ?
                GROUP BY room_id
            ) latest ON cm.room_id = latest.room_id AND cm.created_at = latest.max_time
            ORDER BY cm.created_at DESC
        """;

        return template.query(sql, (rs, rowNum) -> {
            ChatListDTO dto = new ChatListDTO();
            dto.setRoomId(rs.getLong("room_id"));
            dto.setPartnerName(rs.getString("partner_name"));
            dto.setLastMessage(rs.getString("last_message"));
            dto.setLastMessageTime(rs.getTimestamp("last_message_time").toLocalDateTime());
            return dto;
        }, memberId, memberId, memberId);
    }

    // 🧠 채팅방 존재 확인
    @Override
    public Long findRoomIdByUserIds(String user1Id, String user2Id) {
        String sql = """
            SELECT room_id FROM chat_room
            WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)
        """;
        List<Long> result = template.query(sql,
                (rs, rowNum) -> rs.getLong("room_id"),
                user1Id, user2Id, user2Id, user1Id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    // ➕ 채팅방 생성
    @Override
    public Long createChatRoom(String user1Id, String user2Id) {
        String sql = "INSERT INTO chat_room (user1_id, user2_id) VALUES (?, ?)";
        template.update(sql, user1Id, user2Id);

        // 생성된 room_id 반환
        String getIdSql = "SELECT LAST_INSERT_ID()";
        return template.queryForObject(getIdSql, Long.class);
    }

    @Override
    public boolean existsRoom(String user1Id, String user2Id) {
        String sql = """
            SELECT COUNT(*) FROM chat_room
            WHERE (user1_id = ? AND user2_id = ?)
               OR (user1_id = ? AND user2_id = ?)
        """;

        Integer count = template.queryForObject(sql, Integer.class, user1Id, user2Id, user2Id, user1Id);
        return count != null && count > 0;
    }
    
    
}
