package com.springmvc.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.TodoHistoryDTO;

public class TodoHistoryRowMapper implements RowMapper<TodoHistoryDTO> {
    @Override
    public TodoHistoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        TodoHistoryDTO history = new TodoHistoryDTO();
        history.setHistoryId(rs.getLong("history_id"));
        history.setTodoId(rs.getLong("todo_id"));
        history.setWriterId(rs.getString("writer_id"));
        history.setReceiverId(rs.getString("receiver_id"));
        history.setTitle(rs.getString("title"));
        history.setCompleted(rs.getBoolean("completed"));
        history.setDeadline(rs.getDate("deadline").toLocalDate());
        history.setRecordedAt(rs.getTimestamp("recorded_at").toLocalDateTime());
        return history;
    }
}
