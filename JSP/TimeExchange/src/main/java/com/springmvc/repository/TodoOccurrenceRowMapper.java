package com.springmvc.repository;

import com.springmvc.domain.TodoOccurrenceDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class TodoOccurrenceRowMapper implements RowMapper<TodoOccurrenceDTO> {

    @Override
    public TodoOccurrenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        Set<String> cols = columnLabels(rs);

        TodoOccurrenceDTO dto = new TodoOccurrenceDTO();
        dto.setOccurrenceId(rs.getLong("occurrence_id"));

        // 스키마에 따라 컬럼명이 todo_id 또는 recurrence_id일 수 있음 → 둘 다 대응
        if (cols.contains("todo_id")) {
            dto.setTodoId(getLongObj(rs, "todo_id"));
        } else if (cols.contains("recurrence_id")) {
            dto.setTodoId(getLongObj(rs, "recurrence_id"));
        }

        if (cols.contains("occur_date")) {
            Date d = rs.getDate("occur_date");
            dto.setOccurDate(d != null ? d.toLocalDate() : null);
        }

        // boolean은 NULL이면 false가 되므로 필요 시 getObject(Boolean.class) 사용
        if (cols.contains("completed")) {
            try {
                Boolean b = rs.getObject("completed", Boolean.class);
                dto.setCompleted(b != null ? b : Boolean.FALSE);
            } catch (SQLFeatureNotSupportedException | AbstractMethodError e) {
                dto.setCompleted(rs.getBoolean("completed"));
            }
        }
        if (cols.contains("hidden")) {
            try {
                Boolean b = rs.getObject("hidden", Boolean.class);
                dto.setHidden(b != null ? b : Boolean.FALSE);
            } catch (SQLFeatureNotSupportedException | AbstractMethodError e) {
                dto.setHidden(rs.getBoolean("hidden"));
            }
        }

        if (cols.contains("created_at")) {
            Timestamp ts = rs.getTimestamp("created_at");
            dto.setCreatedAt(ts != null ? ts.toLocalDateTime() : null);
        }
        
        if (cols.contains("title")) dto.setTitle(rs.getString("title"));
        if (cols.contains("writer_id")) dto.setWriterId(rs.getString("writer_id"));
        if (cols.contains("receiver_id")) dto.setReceiverId(rs.getString("receiver_id"));

        if (cols.contains("deadline")) {
            Date d = rs.getDate("deadline");
            dto.setDeadline(d != null ? d.toLocalDate() : null);
        }
        if (cols.contains("freq")) {
            String f = rs.getString("freq");
            dto.setFreq(com.springmvc.enums.RecurrenceFreq.fromDb(f));
        }
        if (cols.contains("start_date")) {
            Date sd = rs.getDate("start_date");
            dto.setStartDate(sd != null ? sd.toLocalDate() : null);
        }
        if (cols.contains("end_date")) {
            Date ed = rs.getDate("end_date");
            dto.setEndDate(ed != null ? ed.toLocalDate() : null);
        }
        if (cols.contains("all_day")) {
            try {
                Boolean ad = rs.getObject("all_day", Boolean.class);
                dto.setAllDay(ad != null ? ad : Boolean.TRUE);
            } catch (SQLFeatureNotSupportedException | AbstractMethodError e) {
                dto.setAllDay(rs.getBoolean("all_day"));
            }
        }
        return dto;
    }

    private static Long getLongObj(ResultSet rs, String col) throws SQLException {
        try {
            return rs.getObject(col, Long.class);
        } catch (SQLFeatureNotSupportedException | AbstractMethodError e) {
            Object v = rs.getObject(col);
            return v != null ? rs.getLong(col) : null;
        }
    }

    private static Set<String> columnLabels(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int n = md.getColumnCount();
        Set<String> set = new HashSet<>(n * 2);
        for (int i = 1; i <= n; i++) {
            set.add(md.getColumnLabel(i).toLowerCase(Locale.ROOT));
        }
        return set;
    }
}
