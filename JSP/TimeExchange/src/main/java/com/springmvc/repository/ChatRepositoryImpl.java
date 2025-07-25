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


	@Override
	public void save(ChatEntity message) {
		String sql = "INSERT INTO chat_message (room_id, sender_id, receiver_id, message, type, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        template.update(sql,
                message.getRoomId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getContent(),
                message.getType(),
                message.getCreatedAt()
        );
	}

	  @Override
	    public List<ChatEntity> findByRoomId(String roomId) {
	        String sql = "SELECT * FROM chat_message WHERE room_id = ? ORDER BY created_at ASC";
	        return template.query(sql, new ChatRowMapper(), roomId);
	    }

 
	  @Override
	  public List<ChatListDTO> findChatListByMemberId(String memberId) {
		  String sql = """
				    SELECT cm.room_id,
				           CASE
				               WHEN cm.sender_id = ? THEN cm.receiver_id
				               ELSE cm.sender_id
				           END AS partner_name,
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
				    dto.setRoomId(rs.getString("room_id"));
				    dto.setPartnerName(rs.getString("partner_name"));
				    dto.setLastMessage(rs.getString("last_message"));
				    dto.setLastMessageTime(rs.getTimestamp("last_message_time").toLocalDateTime());
				    return dto;
				}, memberId, memberId, memberId);

	  }
   
    
}
