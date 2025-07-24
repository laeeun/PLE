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
        dto.setMemberId(rs.getString("member_id"));
        dto.setTitle(rs.getString("title"));
        dto.setContent(rs.getString("content"));
        dto.setPriority(rs.getInt("priority"));
        dto.setCompleted(rs.getBoolean("completed"));
        dto.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
        dto.setUpdated_at(rs.getTimestamp("updated_at").toLocalDateTime());
        return dto;
    }
}
