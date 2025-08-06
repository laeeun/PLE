package com.springmvc.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TodoOccurrenceDTO;

@Repository
public class TodoOccurrenceRepositoryImpl implements TodoOccurrenceRepository {

    private final JdbcTemplate jdbc;
    private final TodoOccurrenceRowMapper rowMapper = new TodoOccurrenceRowMapper();

    public TodoOccurrenceRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /* ====================================================
     * 기본 CRUD
     * ==================================================== */

    @Override
    public Optional<TodoOccurrenceDTO> findById(Long occurrenceId) {
        final String sql = """
            SELECT o.*
            FROM todo_occurrence o
            WHERE o.occurrence_id = ?
            """;
        List<TodoOccurrenceDTO> list = jdbc.query(sql, rowMapper, occurrenceId);
        return list.stream().findFirst();
    }

    @Override
    public List<TodoOccurrenceDTO> findByTodoId(Long todoId) {
        final String sql = """
            SELECT o.*
            FROM todo_occurrence o
            WHERE o.todo_id = ?
            ORDER BY o.occur_date ASC, o.occurrence_id ASC
            """;
        return jdbc.query(sql, rowMapper, todoId);
    }

    @Override
    public int deleteById(Long occurrenceId) {
        return jdbc.update("DELETE FROM todo_occurrence WHERE occurrence_id = ?", occurrenceId);
    }

    @Override
    public int deleteByTodoId(Long todoId) {
        return jdbc.update("DELETE FROM todo_occurrence WHERE todo_id = ?", todoId);
    }

    /* ====================================================
     * 생성/배치 생성
     *  - (todo_id, occur_date)에 UNIQUE가 있으므로 upsert 가능
     * ==================================================== */

    @Override
    public int insertOne(Long todoId, LocalDate occurDate) {
        final String sql = """
            INSERT INTO todo_occurrence (todo_id, occur_date, completed, hidden, created_at)
            VALUES (?, ?, FALSE, FALSE, NOW())
            ON DUPLICATE KEY UPDATE todo_id = VALUES(todo_id), occur_date = VALUES(occur_date)
            """;
        return jdbc.update(sql, ps -> {
            ps.setLong(1, todoId);
            ps.setDate(2, Date.valueOf(occurDate));
        });
    }

    @Override
    public void batchInsert(Long todoId, List<LocalDate> occurDates) {
        if (occurDates == null || occurDates.isEmpty()) return;
        final String sql = """
            INSERT INTO todo_occurrence (todo_id, occur_date, completed, hidden, created_at)
            VALUES (?, ?, FALSE, FALSE, NOW())
            ON DUPLICATE KEY UPDATE todo_id = VALUES(todo_id), occur_date = VALUES(occur_date)
            """;
        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, todoId);
                ps.setDate(2, Date.valueOf(occurDates.get(i)));
            }
            @Override public int getBatchSize() { return occurDates.size(); }
        });
    }

    /* ====================================================
     * 상태 토글
     * ==================================================== */

    @Override
    public int setCompleted(Long occurrenceId, boolean completed) {
        final String sql = "UPDATE todo_occurrence SET completed=? WHERE occurrence_id=?";
        return jdbc.update(sql, completed, occurrenceId);
    }

    @Override
    public int setHidden(Long occurrenceId, boolean hidden) {
        final String sql = "UPDATE todo_occurrence SET hidden=? WHERE occurrence_id=?";
        return jdbc.update(sql, hidden, occurrenceId);
    }

    /* ====================================================
     * 달력 범위 조회
     *  - FullCalendar start~end(배타) 관례 반영
     *  - hideExpired=true면 데드라인 다음날 0시부터 숨김
     *  - 조인 컬럼(title, writer_id, receiver_id, deadline, freq, start_date, end_date, all_day)
     *    -> RowMapper가 선택적으로 매핑하도록 대응
     * ==================================================== */

    @Override
    public List<TodoOccurrenceDTO> findRangeByReceiverWithTodoInfo(
            String receiverId,
            LocalDate startInclusive,
            LocalDate endExclusive,
            boolean hideExpired
    ) {
        final String sql = """
            SELECT
                o.*,
                t.title,
                t.writer_id,
                t.receiver_id,
                t.deadline,
                t.freq,
                t.start_date,
                t.end_date,
                t.all_day,
                t.completed
            FROM todo_occurrence o
            JOIN todo t ON t.todo_id = o.todo_id
            WHERE t.receiver_id = ?
              AND o.hidden = FALSE
              AND o.occur_date >= ?
              AND o.occur_date < ?
              %s
            ORDER BY o.occur_date ASC, o.occurrence_id ASC
            """.formatted(
                hideExpired
                    ? "AND (t.deadline IS NULL OR CURDATE() < DATE_ADD(t.deadline, INTERVAL 1 DAY))"
                    : ""
            );

        return jdbc.query(sql, rowMapper,
                receiverId,
                Date.valueOf(startInclusive),
                Date.valueOf(endExclusive)
        );
    }

    /* ====================================================
     * 유틸
     * ==================================================== */

    @Override
    public int countByTodoId(Long todoId) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM todo_occurrence WHERE todo_id = ?",
                Integer.class, todoId
        );
        return n == null ? 0 : n;
    }

    @Override
    public int countExpiredTodosByReceiver(String receiverId) {
        final String sql = """
            SELECT COUNT(DISTINCT t.todo_id)
            FROM todo_occurrence o
            JOIN todo t ON t.todo_id = o.todo_id
            WHERE t.receiver_id = ?
              AND t.deadline IS NOT NULL
              AND CURDATE() >= DATE_ADD(t.deadline, INTERVAL 1 DAY)
            """;
        Integer n = jdbc.queryForObject(sql, Integer.class, receiverId);
        return n == null ? 0 : n;
    }
    
    @Override
    public List<Map<String, Object>> getOccurrenceStatsByDateRange(String receiverId, LocalDate start, LocalDate end) {
        String sql = """
            SELECT occur_date,
                   COUNT(*) AS total,
                   SUM(CASE WHEN is_completed = TRUE THEN 1 ELSE 0 END) AS completed
            FROM todo_occurrence
            WHERE receiver_id = ?
              AND occur_date BETWEEN ? AND ?
            GROUP BY occur_date
            ORDER BY occur_date
        """;

        return jdbc.query(sql,
            (ResultSet rs, int rowNum) -> Map.of(
                "occur_date", rs.getDate("occur_date").toLocalDate(),
                "total", rs.getInt("total"),
                "completed", rs.getInt("completed")
            ),
            receiverId, start, end
        );
    }
    
    @Override
    public boolean isCompleted(Long occurrenceId) {
        String sql = "SELECT completed FROM todo_occurrence WHERE occurrence_id = ?";
        return Boolean.TRUE.equals(jdbc.queryForObject(sql, Boolean.class, occurrenceId));
    }

    @Override
    public void setOccurrenceCompleted(Long occurrenceId, boolean completed) {
        String sql = "UPDATE todo_occurrence SET completed = ? WHERE occurrence_id = ?";
        jdbc.update(sql, completed, occurrenceId);
    }
}