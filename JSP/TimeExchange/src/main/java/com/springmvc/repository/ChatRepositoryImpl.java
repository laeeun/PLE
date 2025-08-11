package com.springmvc.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
				    (room_id, sender_id, receiver_id, message, type, created_at, file_url, `read`, deleted)
				    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		template.update(sql, message.getRoomId(), message.getSenderId(), message.getReceiverId(), message.getContent(),
				message.getType(), message.getCreatedAt(), message.getFileUrl(), message.isRead(), message.isDeleted());
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
				    JOIN member m ON m.member_id = (
				        CASE WHEN cm.sender_id = ? THEN cm.receiver_id ELSE cm.sender_id END
				    )
				    WHERE cm.id IN (
				        SELECT MAX(id)
				        FROM chat_message
				        WHERE sender_id = ? OR receiver_id = ?
				        GROUP BY room_id
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
			dto.setLastMessageTime(rs.getObject("last_message_time", LocalDateTime.class));
			dto.setUnreadCount(rs.getInt("unread_count"));
			return dto;
		}, memberId, memberId, memberId, memberId);
	}

	// 🧠 채팅방 존재 확인
	@Override
	public String findRoomIdByUserIds(String user1Id, String user2Id) {
		String[] users = { user1Id, user2Id };
		Arrays.sort(users);
		String roomId = users[0] + "_" + users[1];

		String sql = "SELECT room_id FROM chat_room WHERE room_id = ?";
		List<String> result = template.query(sql, (rs, rowNum) -> rs.getString("room_id"), roomId);

		return result.isEmpty() ? null : result.get(0);
	}

	// ➕ 채팅방 생성
	@Override
	public String createChatRoom(String user1Id, String user2Id) {
		// ✅ 항상 같은 순서로 정렬
		String[] users = { user1Id, user2Id };
		Arrays.sort(users); // 알파벳 순 정렬 (A_Z → roomId 고정됨)

		String roomId = users[0] + "_" + users[1];

		String sql = "INSERT INTO chat_room (room_id, user1_id, user2_id) VALUES (?, ?, ?)";
		template.update(sql, roomId, users[0], users[1]);

		return roomId;
	}

	@Override
	public boolean existsRoom(String user1Id, String user2Id) {
		String[] users = { user1Id, user2Id };
		Arrays.sort(users);
		String roomId = users[0] + "_" + users[1];

		String sql = "SELECT COUNT(*) FROM chat_room WHERE room_id = ?";
		Integer count = template.queryForObject(sql, Integer.class, roomId);
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
				    ORDER BY cm.created_at DESC
				""";

		List<ChatEntity> allMessages = template.query(sql, new ChatRowMapper(), memberId, memberId);

		// ✅ roomId 기준으로 가장 최근 메시지만 남기기
		Map<String, ChatEntity> latestByRoom = new LinkedHashMap<>();
		for (ChatEntity msg : allMessages) {
			String roomId = msg.getRoomId();
			if (!latestByRoom.containsKey(roomId)) {
				latestByRoom.put(roomId, msg); // 이미 정렬돼 있어서 제일 앞 메시지가 최신임
			}
		}

		return new ArrayList<>(latestByRoom.values());
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
		String sql = "UPDATE chat_message " + "SET `read` = true "
				+ "WHERE room_id = ? AND receiver_id = ? AND `read` = false";
		return template.update(sql, roomId, receiverId);
	}

	@Override
	public int countUnreadMessages(String roomId, String receiverId) {
		String sql = "SELECT COUNT(*) FROM chat_message " + "WHERE room_id = ? AND receiver_id = ? AND `read` = false";
		return template.queryForObject(sql, Integer.class, roomId, receiverId);
	}

	@Override
	public List<String> findSendersWithUnreadMessages(String roomId, String receiverId) {
		String sql = "SELECT DISTINCT sender_id " + "FROM chat_message "
				+ "WHERE room_id = ? AND receiver_id = ? AND `read` = FALSE";

		return template.queryForList(sql, String.class, roomId, receiverId);
	}

	@Override
	public boolean existsRoom(String roomId) {
		String sql = "SELECT COUNT(*) FROM chat_room WHERE room_id = ?";
		Integer count = template.queryForObject(sql, Integer.class, roomId);
		return count != null && count > 0;
	}

	@Override
	public int countUnreadMessages(String receiverId) {
		String sql = """
				    SELECT COUNT(*)
				    FROM chat_message
				    WHERE receiver_id = ?
				      AND `read` = false
				""";

		return template.queryForObject(sql, Integer.class, receiverId);
	}

}
