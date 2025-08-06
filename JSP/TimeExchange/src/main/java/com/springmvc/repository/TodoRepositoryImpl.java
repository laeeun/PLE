package com.springmvc.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TodoCreateRequest;
import com.springmvc.domain.TodoDTO;
import com.springmvc.enums.RecurrenceFreq;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    @Autowired
    private JdbcTemplate template;
    
    private RowMapper<TodoDTO> todoMapper = new TodoRowMapper();

    // ✅ 새로운 할일 추가 (개인 또는 숙제)
    @Override
    public void createTODO(TodoDTO todo) {
        String sql = "INSERT INTO todo (trade_id, writer_id, receiver_id, title, content, priority, completed, is_personal, deadline, start_date, end_date, freq, all_day) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        template.update(sql,
            todo.getTradeId(),
            todo.getWriterId(),
            todo.getReceiverId(),
            todo.getTitle(),
            todo.getContent(),
            todo.getPriority(),
            todo.isCompleted(),
            todo.isPersonal(),
            (todo.getDeadline() != null ? java.sql.Date.valueOf(todo.getDeadline()) : null),
            java.sql.Date.valueOf(todo.getStartDate() != null ? todo.getStartDate() : LocalDate.now()),
            java.sql.Date.valueOf(todo.getEndDate() != null ? todo.getEndDate() : (todo.getStartDate() != null ? todo.getStartDate() : LocalDate.now())), // 🛠 end_date 추가
            (todo.getFreq() != null ? todo.getFreq().name() : RecurrenceFreq.NONE.name()),
            (todo.getAllDay() != null ? todo.getAllDay() : Boolean.TRUE)
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
        String sql = "UPDATE todo SET " +
                     "title = ?, " +
                     "content = ?, " +
                     "priority = ?, " +
                     "completed = ?, " +
                     "deadline = ?, " +
                     "start_date = ?, " +
                     "end_date = ?, " +
                     "freq = ?, " +
                     "all_day = ?, " +
                     "updated_at = NOW() " +
                     "WHERE todo_id = ?";

        template.update(sql,
            todo.getTitle(),
            todo.getContent(),
            todo.getPriority(),
            todo.isCompleted(),
            (todo.getDeadline() != null ? java.sql.Date.valueOf(todo.getDeadline()) : null),
            (todo.getStartDate() != null ? java.sql.Date.valueOf(todo.getStartDate()) : null),
            (todo.getEndDate() != null ? java.sql.Date.valueOf(todo.getEndDate()) : null),
            (todo.getFreq() != null ? todo.getFreq().name() : RecurrenceFreq.NONE.name()),
            (todo.getAllDay() != null ? todo.getAllDay() : Boolean.TRUE),
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
	    String sql = """
	        SELECT SUM(t.total) AS total,
	               SUM(t.completed) AS completed,
	               SUM(CASE WHEN t.priority = 1 THEN 1 ELSE 0 END) AS high_priority_total,
	               SUM(CASE WHEN t.priority = 1 AND t.completed = TRUE THEN 1 ELSE 0 END) AS high_priority_completed,
	               SUM(CASE WHEN t.priority = 2 THEN 1 ELSE 0 END) AS medium_priority_total,
	               SUM(CASE WHEN t.priority = 2 AND t.completed = TRUE THEN 1 ELSE 0 END) AS medium_priority_completed,
	               SUM(CASE WHEN t.priority = 3 THEN 1 ELSE 0 END) AS low_priority_total,
	               SUM(CASE WHEN t.priority = 3 AND t.completed = TRUE THEN 1 ELSE 0 END) AS low_priority_completed
	          FROM (
	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS completed,
	                     priority
	                FROM todo
	               WHERE receiver_id = ? AND deadline = CURDATE()
	               GROUP BY priority

	              UNION ALL

	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN o.completed = TRUE THEN 1 ELSE 0 END) AS completed,
	                     t.priority
	                FROM todo_occurrence o
	                JOIN todo t ON o.todo_id = t.todo_id
	               WHERE t.receiver_id = ? AND o.occur_date = CURDATE()
	               GROUP BY t.priority
	          ) t
	        """;
	    return template.queryForMap(sql, receiverId, receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsAssigned(String receiverId) {
	    String sql = """
	        SELECT SUM(t.total) AS total,
	               SUM(t.completed) AS completed
	          FROM (
	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS completed
	                FROM todo
	               WHERE receiver_id = ? AND is_personal = FALSE AND deadline = CURDATE()

	              UNION ALL

	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN o.completed = TRUE THEN 1 ELSE 0 END) AS completed
	                FROM todo_occurrence o
	                JOIN todo t ON o.todo_id = t.todo_id
	               WHERE t.receiver_id = ? AND t.is_personal = FALSE AND occur_date  = CURDATE()
	          ) t
	        """;
	    return template.queryForMap(sql, receiverId, receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsCreated(String writerId) {
	    String sql = """
	        SELECT SUM(t.total) AS total,
	               SUM(t.completed) AS completed
	          FROM (
	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS completed
	                FROM todo
	               WHERE writer_id = ? AND deadline = CURDATE()

	              UNION ALL

	              SELECT COUNT(*) AS total,
	                     SUM(CASE WHEN o.completed = TRUE THEN 1 ELSE 0 END) AS completed
	                FROM todo_occurrence o
	                JOIN todo t ON o.todo_id = t.todo_id
	               WHERE t.writer_id = ? AND occur_date  = CURDATE()
	          ) t
	        """;
	    return template.queryForMap(sql, writerId, writerId);
	}
	
	// 반복 주기 필터링 메서드들
	@Override
	public List<TodoDTO> findByWriterId(String writerId, Boolean completed, String freq) {
	    String sql = "SELECT * FROM todo WHERE writer_id = ?";
	    List<Object> params = new ArrayList<>();
	    params.add(writerId);
	    
	    if (completed != null) {
	        sql += " AND completed = ?";
	        params.add(completed);
	    }
	    
	    if (freq != null && !freq.isEmpty() && !"ALL".equals(freq)) {
	        sql += " AND freq = ?";
	        params.add(freq);
	    }
	    
	    return template.query(sql + " ORDER BY created_at DESC", todoMapper, params.toArray());
	}

	@Override
	public List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed, String freq) {
	    String sql = "SELECT * FROM todo WHERE receiver_id = ? AND is_personal = false";
	    List<Object> params = new ArrayList<>();
	    params.add(receiverId);
	    
	    if (completed != null) {
	        sql += " AND completed = ?";
	        params.add(completed);
	    }
	    
	    if (freq != null && !freq.isEmpty() && !"ALL".equals(freq)) {
	        sql += " AND freq = ?";
	        params.add(freq);
	    }
	    
	    return template.query(sql + " ORDER BY created_at DESC", todoMapper, params.toArray());
	}

	@Override
	public List<TodoDTO> findAllTodos(String receiverId, Boolean completed, String freq) {
	    String sql = "SELECT * FROM todo WHERE receiver_id = ?";
	    List<Object> params = new ArrayList<>();
	    params.add(receiverId);
	    
	    if (completed != null) {
	        sql += " AND completed = ?";
	        params.add(completed);
	    }
	    
	    if (freq != null && !freq.isEmpty() && !"ALL".equals(freq)) {
	        sql += " AND freq = ?";
	        params.add(freq);
	    }
	    
	    return template.query(sql + " ORDER BY created_at DESC", todoMapper, params.toArray());
	}
	
	@Override
	public List<TodoDTO> findByDeadline(LocalDate deadline) {
	    String sql = "SELECT * FROM todo WHERE deadline = ? AND completed = FALSE";
	    return template.query(sql, todoMapper, deadline);
	}
	
	@Override
	public int countMissedBackups(LocalDate date) {
	    String sql = """
	        SELECT COUNT(*) FROM todo 
	        WHERE deadline < ? 
	        AND todo_id NOT IN (
	            SELECT todo_id FROM todo_history 
	            WHERE DATE(recorded_at) = ?
	        )
	    """;
	    return template.queryForObject(sql, Integer.class, date, date);
	}
	
	@Override
	public Map<String, Object> getTodayStatsByPriority(String receiverId) {
	    String sql = """
	        SELECT 
	            SUM(CASE WHEN priority = 1 THEN 1 ELSE 0 END) AS high_total,
	            SUM(CASE WHEN priority = 1 AND completed = TRUE THEN 1 ELSE 0 END) AS high_completed,
	            SUM(CASE WHEN priority = 2 THEN 1 ELSE 0 END) AS medium_total,
	            SUM(CASE WHEN priority = 2 AND completed = TRUE THEN 1 ELSE 0 END) AS medium_completed,
	            SUM(CASE WHEN priority = 3 THEN 1 ELSE 0 END) AS low_total,
	            SUM(CASE WHEN priority = 3 AND completed = TRUE THEN 1 ELSE 0 END) AS low_completed
	        FROM todo 
	        WHERE receiver_id = ? AND deadline = CURDATE()
	    """;
	    return template.queryForMap(sql, receiverId);
	}
	
	@Override
	public Map<String, Object> getTodayStatsByTimeframe(String receiverId) {
	    String sql = """
	        SELECT 
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 6 AND 11 THEN 1 ELSE 0 END) AS morning_total,
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 6 AND 11 AND completed = TRUE THEN 1 ELSE 0 END) AS morning_completed,
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 12 AND 17 THEN 1 ELSE 0 END) AS afternoon_total,
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 12 AND 17 AND completed = TRUE THEN 1 ELSE 0 END) AS afternoon_completed,
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 18 AND 23 THEN 1 ELSE 0 END) AS evening_total,
	            SUM(CASE WHEN HOUR(created_at) BETWEEN 18 AND 23 AND completed = TRUE THEN 1 ELSE 0 END) AS evening_completed
	        FROM todo 
	        WHERE receiver_id = ? AND deadline = CURDATE()
	    """;
	    return template.queryForMap(sql, receiverId);
	}

	@Override
    public int updateTitleContent(long todoId, String title, String content, String ownerId) {
        String sql = """
            UPDATE todo
               SET title = ?, content = ?, updated_at = NOW()
             WHERE todo_id = ?
               AND ( ? IS NULL OR writer_id = ? OR receiver_id = ? )
            """;

        return template.update(sql, ps -> {
            int i = 1;
            ps.setString(i++, title);
            ps.setString(i++, content);
            ps.setLong(i++, todoId);

            // ownerId가 null이면 소유자 검증을 건너뜁니다.
            ps.setString(i++, ownerId);
            ps.setString(i++, ownerId);
            ps.setString(i++, ownerId);
        });
    }
	
 	@Override
    public Long insertTodo(TodoCreateRequest req) {
        String sql = """
            INSERT INTO todo 
              (trade_id, writer_id, receiver_id, title, content, priority, completed, is_personal, deadline, freq, start_date, end_date, all_day, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, FALSE, ?, ?, ?, ?, ?, ?, NOW(), NOW())
        """;

        // KeyHolder로 auto_increment PK(todo_id) 추출
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, req.getTradeId());
            ps.setString(2, req.getWriterId());
            ps.setString(3, req.getReceiverId());
            ps.setString(4, req.getTitle());
            ps.setString(5, req.getContent());
            ps.setInt(6, req.getPriority() != null ? req.getPriority() : 2);
            ps.setBoolean(7, req.getIsPersonal() != null ? req.getIsPersonal() : Boolean.TRUE);
            ps.setObject(8, req.getDeadline() != null ? java.sql.Date.valueOf(req.getDeadline()) : null);
            ps.setString(9, req.getFreq() != null ? req.getFreq().name() : RecurrenceFreq.NONE.name());
            ps.setObject(10, req.getStartDate());
            ps.setObject(11, req.getEndDate());
            ps.setBoolean(12, req.getAllDay() != null ? req.getAllDay() : Boolean.TRUE);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    // === 새로 추가된 findByIdAsDto 메서드 ===
    @Override
    public Optional<TodoDTO> findByIdAsDto(Long todoId) {
        String sql = "SELECT * FROM todo WHERE todo_id = ?";
        try {
            return Optional.of(template.queryForObject(sql, todoMapper, todoId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public int updateBasic(Long todoId, String title, String content, Integer priority, LocalDate deadline,
                           RecurrenceFreq freq, LocalDate startDate, LocalDate endDate, Boolean allDay) {

        String sql = """
            UPDATE todo
               SET title = ?, 
                   content = ?, 
                   priority = ?, 
                   deadline = ?, 
                   freq = ?, 
                   start_date = ?, 
                   end_date = ?, 
                   all_day = ?, 
                   updated_at = NOW()
             WHERE todo_id = ?
        """;

        return template.update(sql,
            title,
            content,
            priority,
            (deadline != null ? java.sql.Date.valueOf(deadline) : null),
            (freq != null ? freq.name() : null),
            (startDate != null ? java.sql.Date.valueOf(startDate) : null),
            (endDate != null ? java.sql.Date.valueOf(endDate) : null),
            (allDay != null ? allDay : Boolean.TRUE),
            todoId
        );
    }
    
    @Override
    public List<TodoDTO> findOccurrencesByReceiverId(String receiverId) {
        String sql = """
            SELECT o.todo_id, t.trade_id, t.writer_id, t.receiver_id, t.title, t.content,
                   t.priority, o.completed, t.is_personal, o.occur_date AS deadline,
                   t.created_at, t.updated_at, t.freq, t.start_date, t.end_date, t.all_day
              FROM todo_occurrence o
              JOIN todo t ON o.todo_id = t.todo_id
             WHERE t.receiver_id = ?
             ORDER BY o.occur_date DESC
        """;
        return template.query(sql, new TodoRowMapper(), receiverId);
    }
    
}
