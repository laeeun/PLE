package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.TodoDTO;

public class TodoRowMapper implements RowMapper<TodoDTO> {

    @Override
    public TodoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoDTO dto = new TodoDTO();
        dto.setTodoId(rs.getLong("todo_id"));
        dto.setTradeId(rs.getObject("trade_id") != null ? rs.getLong("trade_id") : null);
        dto.setWriterId(rs.getString("writer_id"));
        dto.setReceiverId(rs.getString("receiver_id"));
        dto.setTitle(rs.getString("title"));
        dto.setContent(rs.getString("content"));
        dto.setPriority(rs.getInt("priority"));
        dto.setCompleted(rs.getBoolean("completed"));
        dto.setPersonal(rs.getBoolean("is_personal"));
        java.sql.Date d = rs.getDate("deadline");
        dto.setDeadline(d != null ? d.toLocalDate() : null);
        dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        dto.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        return dto;
    }
}
