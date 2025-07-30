package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.ChatEntity;
import com.springmvc.domain.ChatListDTO;
import com.springmvc.domain.ChatMessage;

@Repository
public class ChatRepositoryImpl implements ChatRepository {

    @Autowired
    private JdbcTemplate template;

    // 💬 메시지 저장
    @Override
    public void saveMessage(ChatEntity message) {
        String sql = """
            INSERT INTO chat_message
            (room_id, sender_id, receiver_id, message, type, created_at, file_url, `read`, deleted)
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
    public List<ChatEntity> findMessagesByRoomId(String roomId) {
        String sql = "SELECT * FROM chat_message WHERE room_id = ? AND deleted = FALSE ORDER BY created_at ASC";
        return template.query(sql, new ChatRowMapper(), roomId);
    }

    // 📩 채팅방 목록 조회
    @Override
    public List<ChatListDTO> findChatListByUserId(String memberId) {
        String sql = """
            SELECT cm.room_id,
                   m.username AS partner_name,
                   m.profile_image AS partner_profile_image,
                   cm.message AS last_message,
                   cm.created_at AS last_message_time,
                   (
                       SELECT COUNT(*) 
                       FROM chat_message cm2 
                       WHERE cm2.room_id = cm.room_id 
                         AND cm2.receiver_id = ? 
                         AND cm2.read = false
                   ) AS unread_count
            FROM chat_message cm
            JOIN (
                SELECT room_id, MAX(created_at) AS max_time
                FROM chat_message
                WHERE sender_id = ? OR receiver_id = ?
                GROUP BY room_id
            ) latest ON cm.room_id = latest.room_id AND cm.created_at = latest.max_time
            JOIN member m ON m.member_id = (
                CASE WHEN cm.sender_id = ? THEN cm.receiver_id ELSE cm.sender_id END
            )
            ORDER BY cm.created_at DESC
        """;

        return template.query(sql, (rs, rowNum) -> {
            ChatListDTO dto = new ChatListDTO();
            dto.setRoomId(rs.getString("room_id"));
            dto.setPartnerName(rs.getString("partner_name"));

            String rawImage = rs.getString("partner_profile_image");
            if (rawImage != null && !rawImage.trim().isEmpty()) {
                if (!rawImage.startsWith("/upload/")) {
                    rawImage = "/upload/profile/" + rawImage;
                }
                dto.setPartnerProfileImage(rawImage);
            } else {
                dto.setPartnerProfileImage("/resources/images/default-profile.png");
            }

            dto.setLastMessage(rs.getString("last_message"));
            dto.setLastMessageTime(rs.getTimestamp("last_message_time").toLocalDateTime());

            // ✅ unreadCount도 세팅
            dto.setUnreadCount(rs.getInt("unread_count"));

            return dto;
        }, memberId, memberId, memberId, memberId); // 파라미터 순서 주의
    }




    // 🧠 채팅방 존재 확인
    @Override
    public String findRoomIdByUserIds(String user1Id, String user2Id) {
        String sql = """
            SELECT room_id FROM chat_room
            WHERE (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)
        """;
        List<String> result = template.query(sql,
                (rs, rowNum) -> rs.getString("room_id"),
                user1Id, user2Id, user2Id, user1Id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    // ➕ 채팅방 생성
    @Override
    public String createChatRoom(String user1Id, String user2Id) {
        // ✅ room_id를 직접 생성 (예: "user1_user2" 형식으로)
        String roomId = user1Id + "_" + user2Id;

        String sql = "INSERT INTO chat_room (room_id, user1_id, user2_id) VALUES (?, ?, ?)";
        template.update(sql, roomId, user1Id, user2Id);

        return roomId; // ✅ 생성된 room_id를 그대로 반환
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
    
    @Override
    public List<ChatEntity> findAllMessagesByMemberId(String memberId) {
        String sql = """
            SELECT cm.*, 
                   s.nickname AS sender_name,
                   s.profile_image AS sender_profile_image,
                   r.nickname AS receiver_name,
                   r.profile_image AS receiver_profile_image
            FROM chat_message cm
            JOIN member s ON cm.sender_id = s.member_id
            JOIN member r ON cm.receiver_id = r.member_id
            WHERE cm.room_id IN (
                SELECT DISTINCT room_id
                FROM chat_message
                WHERE sender_id = ? OR receiver_id = ?
            )
            ORDER BY cm.room_id, cm.created_at ASC
        """;

        return template.query(sql, new ChatRowMapper(), memberId, memberId);
    }

	@Override
	public void deleteChatRoomById(String roomId) {
	    String deleteMessagesSql = "DELETE FROM chat_message WHERE room_id = ?";
	    template.update(deleteMessagesSql, roomId);

	    String deleteRoomSql = "DELETE FROM chat_room WHERE room_id = ?";
	    template.update(deleteRoomSql, roomId);
	}

	@Override
    public int markMessagesAsRead(String roomId, String receiverId) {
        String sql = "UPDATE chat_message " +
                     "SET `read` = true " +
                     "WHERE room_id = ? AND receiver_id = ? AND `read` = false";
        return template.update(sql, roomId, receiverId);
    }
	
	@Override
	public int countUnreadMessages(String roomId, String receiverId) {
	    String sql = "SELECT COUNT(*) FROM chat_message " +
	                 "WHERE room_id = ? AND receiver_id = ? AND `read` = false";
	    return template.queryForObject(sql, Integer.class, roomId, receiverId);
	}
	
	@Override
	public List<String> findSendersWithUnreadMessages(String roomId, String receiverId) {
	    String sql = "SELECT DISTINCT sender_id " +
	                 "FROM chat_message " +
	                 "WHERE room_id = ? AND receiver_id = ? AND `read` = FALSE";

	    return template.queryForList(sql, String.class, roomId, receiverId);
	}


}
