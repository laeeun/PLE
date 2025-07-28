package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.springmvc.domain.ChatEntity;

public class ChatRowMapper implements RowMapper<ChatEntity> {

    @Override
    public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChatEntity entity = new ChatEntity();
        entity.setId(rs.getLong("id"));
        entity.setRoomId(rs.getLong("room_id")); // Long 타입일 경우
        entity.setSenderId(rs.getString("sender_id"));
        entity.setReceiverId(rs.getString("receiver_id"));
        entity.setContent(rs.getString("message"));
        entity.setType(rs.getString("type"));
        entity.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // ✅ 추가된 필드들
        entity.setFileUrl(rs.getString("file_url"));
        entity.setRead(rs.getBoolean("read"));
        entity.setDeleted(rs.getBoolean("deleted"));

        return entity;
    }
}
