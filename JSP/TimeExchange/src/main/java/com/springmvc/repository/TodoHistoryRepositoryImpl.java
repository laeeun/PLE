package com.springmvc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TodoHistoryDTO;

@Repository	
public class TodoHistoryRepositoryImpl implements TodoHistoryRepository {
	
	JdbcTemplate template;
	
	@Autowired
	public TodoHistoryRepositoryImpl(JdbcTemplate template){
		this.template = template;
	}
	@Override
    public List<TodoHistoryDTO> findByReceiverId(String receiverId) {
        String sql = "SELECT * FROM todo_history WHERE receiver_id = ? ORDER BY recorded_at DESC";
        return template.query(sql, new TodoHistoryRowMapper(), receiverId);
    }

    @Override
    public List<Map<String, Object>> findStatsByReceiverId(String receiverId) {
        String sql = "SELECT DATE(recorded_at) AS date, COUNT(*) AS total, " +
                     "SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS completed " +
                     "FROM todo_history WHERE receiver_id = ? " +
                     "GROUP BY DATE(recorded_at) ORDER BY date DESC LIMIT 6";
        return template.queryForList(sql, receiverId);
    }

    @Override
    public int backupFromTodoTable() {
        String sql = "INSERT INTO todo_history (todo_id, writer_id, receiver_id, title, completed, deadline) " +
                     "SELECT todo_id, writer_id, receiver_id, title, completed, deadline " +
                     "FROM todo WHERE deadline < CURDATE()";
        return template.update(sql);
    }
    
    @Override
    public void backupSingleTodo(Long todoId) {
        String sql = """
            INSERT INTO todo_history (todo_id, writer_id, receiver_id, title, completed, deadline, recorded_at)
            SELECT todo_id, writer_id, receiver_id, title, completed, deadline, NOW()
            FROM todo 
            WHERE todo_id = ? AND deadline < CURDATE()
            ON DUPLICATE KEY UPDATE 
                completed = VALUES(completed),
                recorded_at = NOW()
        """;
        template.update(sql, todoId);
    }
	@Override
	public List<Map<String, Object>> findStatsByDateRange(String receiverId, LocalDate start, LocalDate end) {
		final String sql =
	            "SELECT DATE(recorded_at) AS stat_date, " +
	            "       COUNT(*) AS total, " +
	            "       COALESCE(SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END), 0) AS completed " +
	            "FROM todo_history " +
	            "WHERE receiver_id = ? " +
	            "  AND DATE(recorded_at) BETWEEN ? AND ? " +
	            "GROUP BY DATE(recorded_at) " +
	            "ORDER BY stat_date ASC";
		return template.query(
	            sql,
	            ps -> {
	                ps.setString(1, receiverId);
	                ps.setDate(2, java.sql.Date.valueOf(start));
	                ps.setDate(3, java.sql.Date.valueOf(end));
	            },
	            (rs, rowNum) -> {
	                Map<String, Object> row = new java.util.HashMap<>();
	                java.sql.Date d = rs.getDate("stat_date");
	                row.put("date", d != null ? d.toLocalDate().toString() : null); // 'yyyy-MM-dd'
	                row.put("total", rs.getInt("total"));
	                row.put("completed", rs.getInt("completed"));
	                return row;
	            }
	        );
	}   
}
