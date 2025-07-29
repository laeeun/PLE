package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.NotificationDTO;

public class NotificationRowMapper implements RowMapper<NotificationDTO> {

    @Override
    public NotificationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationDTO dto = new NotificationDTO();

        dto.setNotificationId(rs.getLong("notification_id"));
        dto.setReceiver_username(rs.getString("receiver_username"));
        dto.setSender_username(rs.getString("sender_username"));
        dto.setType(rs.getString("type"));
        dto.setContent(rs.getString("content"));
        dto.setTargetId(rs.getLong("target_id"));
        dto.setTarget_type(rs.getString("target_type"));
        dto.setRead(rs.getBoolean("is_read"));

        // created_at은 nullable 가능성 고려
        java.sql.Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            dto.setCreated_at(ts.toLocalDateTime());
        }

        return dto;
    }
}
