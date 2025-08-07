package com.springmvc.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.TodoDTO;
import com.springmvc.enums.RecurrenceFreq;

public class TodoRowMapper implements RowMapper<TodoDTO> {

    @Override
    public TodoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Set<String> cols = columnLabels(rs); // 컬럼 존재 여부 체크용 (소문자)
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
        if (cols.contains("freq")) {
            String f = rs.getString("freq");
            dto.setFreq(RecurrenceFreq.fromDb(f)); // NONE/DAILY/WEEKLY/MONTHLY 안전 매핑
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
            // getBoolean은 NULL일 때 false가 되므로, NULL 구분 원하면 getObject로
            try {
                Boolean allDay = rs.getObject("all_day", Boolean.class);
                dto.setAllDay(allDay != null ? allDay : Boolean.TRUE);
            } catch (AbstractMethodError | SQLFeatureNotSupportedException e) {
                dto.setAllDay(rs.getBoolean("all_day"));
            }
        }
        return dto;
    }
   

    /** 현재 ResultSet의 컬럼 라벨을 소문자로 모아두고, 존재 여부를 O(1)에 가깝게 확인 */
    private static Set<String> columnLabels(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int n = md.getColumnCount();
        Set<String> set = new HashSet<>(n * 2);
        for (int i = 1; i <= n; i++) {
            // 라벨(alias 우선) 사용
            set.add(md.getColumnLabel(i).toLowerCase(Locale.ROOT));
        }
        return set;
    }
}
