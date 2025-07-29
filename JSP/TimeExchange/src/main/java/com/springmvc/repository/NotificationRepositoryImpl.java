package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.NotificationDTO;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository{
	
	private JdbcTemplate template;
	
	@Autowired
	NotificationRepositoryImpl(JdbcTemplate template){
		this.template = template;
	}

	 @Override
	    public void createNotification(NotificationDTO noti) {
	        String sql = "INSERT INTO notification (receiver_username, sender_username, type, content, target_id, target_type, is_read, created_at) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, false, NOW())";
	        template.update(sql,
	                noti.getReceiver_username(),
	                noti.getSender_username(),
	                noti.getType(),
	                noti.getContent(),
	                noti.getTargetId(),
	                noti.getTarget_type()
	        );
	    }

	 @Override
	 public List<NotificationDTO> getNotificationsByTypeAndPage(String receiverUsername, String type, int offset, int limit) {
	     String sql;
	     Object[] params;
	     // ✅ type이 null 또는 공백일 경우 전체 알림
	     if (type == null || type.isBlank()) {
	         sql = "SELECT * FROM notification " +
	               "WHERE receiver_username = ? " +
	               "ORDER BY created_at DESC " +
	               "LIMIT ? OFFSET ?";
	         params = new Object[] { receiverUsername, limit, offset };
	     } else {
	         // ✅ 특정 type으로 필터링
	         sql = "SELECT * FROM notification " +
	               "WHERE receiver_username = ? AND type = ? " +
	               "ORDER BY created_at DESC " +
	               "LIMIT ? OFFSET ?";
	         params = new Object[] { receiverUsername, type, limit, offset };
	     }
	     return template.query(sql, new NotificationRowMapper(), params);
	 }

	    @Override
	    public int countNotifications(String receiverUsername, String type) {
	        String sql = "SELECT COUNT(*) FROM notification " +
	                     "WHERE receiver_username = ? " +
	                     "AND (? IS NULL OR type = ?)";
	        return template.queryForObject(sql, Integer.class, receiverUsername, type, type);
	    }

	    @Override
	    public NotificationDTO getDetailNotification(long notificationId) {
	        String sql = "SELECT * FROM notification WHERE notification_id = ?";
	        return template.queryForObject(sql, new NotificationRowMapper(), notificationId);
	    }

	    @Override
	    public void markAsRead(long notificationId) {
	        String sql = "UPDATE notification SET is_read = true WHERE notification_id = ?";
	        template.update(sql, notificationId);
	    }

	    @Override
	    public void markAllAsRead(String receiverUsername) {
	        String sql = "UPDATE notification SET is_read = true WHERE receiver_username = ?";
	        template.update(sql, receiverUsername);
	    }

	    @Override
	    public int countUnread(String receiverUsername) {
	        String sql = "SELECT COUNT(*) FROM notification WHERE receiver_username = ? AND is_read = false";
	        return template.queryForObject(sql, Integer.class, receiverUsername);
	    }

	    @Override
	    public boolean isRead(long notificationId) {
	        String sql = "SELECT is_read FROM notification WHERE notification_id = ?";
	        return template.queryForObject(sql, Boolean.class, notificationId);
	    }

	    @Override
	    public void deleteNotification(long notificationId) {
	        String sql = "DELETE FROM notification WHERE notification_id = ?";
	        template.update(sql, notificationId);
	    }
	
	
}
