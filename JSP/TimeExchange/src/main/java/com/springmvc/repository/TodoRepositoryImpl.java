package com.springmvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TodoDTO;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    @Autowired
    private JdbcTemplate template;
    
    private RowMapper<TodoDTO> todoMapper = new TodoRowMapper();

    // ✅ 새로운 할일 추가 (개인 또는 숙제)
    @Override
    public void createTODO(TodoDTO todo) {
    	String sql = "INSERT INTO todo (trade_id, writer_id, receiver_id, title, content, priority, completed, is_personal, deadline) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, COALESCE(?, CURDATE()))";

        template.update(sql,
            todo.getTradeId(),
            todo.getWriterId(),
            todo.getReceiverId(),
            todo.getTitle(),
            todo.getContent(),
            todo.getPriority(),
            todo.isCompleted(),
            todo.isPersonal(),
            (todo.getDeadline() != null ? java.sql.Date.valueOf(todo.getDeadline()) : null)
        );
    }

    // ✅ 내가 받은 모든 할일 조회 (receiver 기준, 중요도 → 최신순 정렬)
    @Override
    public List<TodoDTO> findByReceiverId(String receiverId) {
        String sql = "SELECT * FROM todo WHERE receiver_id = ? ORDER BY priority ASC, created_at DESC";
        return template.query(sql, todoMapper, receiverId);
    }

    // ✅ 내가 만든 개인 할일만 조회 (receiver == 나, is_personal = true)
    @Override
    public List<TodoDTO> findPersonalTodos(String receiverId) {
        String sql = "SELECT * FROM todo WHERE receiver_id = ? AND is_personal = true ORDER BY created_at DESC";
        return template.query(sql, todoMapper, receiverId);
    }

    // ✅ 판매자(상대)가 나에게 준 숙제만 조회 (is_personal = false)
    @Override
    public List<TodoDTO> findAssignedTodos(String receiverId) {
        String sql = "SELECT * FROM todo WHERE receiver_id = ? AND is_personal = false ORDER BY created_at DESC";
        return template.query(sql, todoMapper, receiverId);
    }

    // ✅ 완료 여부로 필터링 (내가 받은 것 중에서만)
    @Override
    public List<TodoDTO> findByCompleted(String receiverId, boolean completed) {
        String sql = "SELECT * FROM todo WHERE receiver_id = ? AND completed = ?";
        return template.query(sql, todoMapper, receiverId, completed);
    }

    // ✅ 제목, 내용, 우선순위, 완료 상태 등 전체 업데이트
    @Override
    public void updateTODO(TodoDTO todo) {
        String sql = "UPDATE todo SET title = ?, content = ?, priority = ?, completed = ?, deadline = ?, updated_at = NOW() " +
                     "WHERE todo_id = ?";

        template.update(sql,
            todo.getTitle(),
            todo.getContent(),
            todo.getPriority(),
            todo.isCompleted(),
            (todo.getDeadline() != null ? java.sql.Date.valueOf(todo.getDeadline()) : null),
            todo.getTodoId()
        );
    }

    // ✅ 완료 상태만 빠르게 변경 (toggle 용 AJAX 요청 대응)
    @Override
    public void updateCompleted(long todoId, boolean completed) {
        String sql = "UPDATE todo SET completed = ?, updated_at = NOW() WHERE todo_id = ?";
        template.update(sql, completed, todoId);
    }

    // ✅ 할일 삭제
    @Override
    public void deleteById(long todoId) {
        String sql = "DELETE FROM todo WHERE todo_id = ?";
        template.update(sql, todoId);
    }

    // ✅ 특정 할일 1개 조회 (PK 기준)
    @Override
    public TodoDTO findById(long todoId) {
        String sql = "SELECT * FROM todo WHERE todo_id = ?";
        return template.queryForObject(sql, todoMapper, todoId);
    }

    // ✅ 내가 만든 할일 전체 조회 (writer 기준, is_personal 여부 무관)
    @Override
    public List<TodoDTO> findByWriterId(String writerId) {
        String sql = "SELECT * FROM todo WHERE writer_id = ?";
        return template.query(sql, todoMapper, writerId);
    }

    // ✅ 특정 거래(trade_id)에 연결된 숙제 목록 조회
    @Override
    public List<TodoDTO> findByTradeId(Long tradeId) {
        String sql = "SELECT * FROM todo WHERE trade_id = ?";
        return template.query(sql, todoMapper, tradeId);
    }

    // ✅ 내가 만든 할일 중 완료 여부로 필터링 (마이페이지 필터탭 대응용)
    @Override
    public List<TodoDTO> findByWriterId(String writerId, Boolean completed) {
        String sql = "SELECT * FROM todo WHERE writer_id = ?";
        if (completed != null) {
            sql += " AND completed = ?";
            return template.query(sql + " ORDER BY created_at DESC", todoMapper, writerId, completed);
        }
        return template.query(sql + " ORDER BY created_at DESC", todoMapper, writerId);
    }
    
    @Override
    public List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed) {
        String sql = "SELECT * FROM todo WHERE receiver_id = ? AND is_personal = false";
        if (completed != null) {
            sql += " AND completed = ?";
            return template.query(sql + " ORDER BY created_at DESC", todoMapper, receiverId, completed);
        }
        return template.query(sql + " ORDER BY created_at DESC", todoMapper, receiverId);
    }
    // 마감일이 자정을 지난 투두들 초기화
	@Override
	public int resetTodosByDeadline() {
		 String sql = "UPDATE todo SET completed = FALSE WHERE deadline < CURDATE()";
	     return template.update(sql);
	}
	
	@Override
	public Map<String, Object> getTodayStats(String receiverId) {
	    String sql = "SELECT COUNT(*) AS total, " +
	                 "SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS completed " +
	                 "FROM todo WHERE receiver_id = ? AND deadline = CURDATE()";
	    return template.queryForMap(sql, receiverId);
	}
	
	@Override
	public Map<String, Object> getTodayStatsAll(String receiverId) {
	    String sql = "SELECT COUNT(*) AS total, " +
	                 "COALESCE(SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END), 0) AS completed " +
	                 "FROM todo WHERE receiver_id = ? AND deadline = CURDATE()";
	    return template.queryForMap(sql, receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsAssigned(String receiverId) {
	    String sql = "SELECT COUNT(*) AS total, " +
	                 "COALESCE(SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END), 0) AS completed " +
	                 "FROM todo WHERE receiver_id = ? AND is_personal = FALSE AND deadline = CURDATE()";
	    return template.queryForMap(sql, receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsCreated(String writerId) {
	    String sql = "SELECT COUNT(*) AS total, " +
	                 "COALESCE(SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END), 0) AS completed " +
	                 "FROM todo WHERE writer_id = ? AND deadline = CURDATE()";
	    return template.queryForMap(sql, writerId);
	}
}
