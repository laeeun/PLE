package com.springmvc.repository;

import com.springmvc.domain.GoalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class GoalRepositoryImpl implements GoalRepository {
    
    @Autowired
    private JdbcTemplate template;
    
    private final GoalRowMapper goalMapper = new GoalRowMapper();
    
    @Override
    public void createGoal(GoalDTO goal) {
    	System.out.println("레파지토리createGoal입장");
    	System.out.println("레파지토리createGoal DTO:"+goal);
    	String sql = """
    		    INSERT INTO goals (
    		        user_id, title, description, goal_type, start_date, end_date, 
    		        completed, is_active, created_at, updated_at
    		    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    		""";
    		
    		template.update(sql,
    		    goal.getUserId(),
    		    goal.getTitle(),
    		    goal.getDescription(),
    		    goal.getGoalType(),
    		    goal.getStartDate(),
    		    goal.getEndDate(),
    		    goal.isCompleted(),    // ✅ 새 필드
    		    goal.isActive(),
    		    goal.getCreatedAt(),
    		    goal.getUpdatedAt()
    		);
    }
    
    @Override
    public List<GoalDTO> findActiveGoalsByUserId(String userId) {
    	System.out.println("레파지토리 findActiveGoalsByUserId");
        String sql = "SELECT * FROM goals WHERE user_id = ? AND is_active = TRUE ORDER BY created_at DESC";
        System.out.println("레파지토리계층"+template.query(sql, goalMapper, userId));
        return template.query(sql, goalMapper, userId);
    }
    
    @Override
    public List<GoalDTO> findGoalsByPeriod(String userId, LocalDate startDate, LocalDate endDate) {
        String sql = """
            SELECT * FROM goals 
            WHERE user_id = ? AND is_active = TRUE 
            AND ((start_date BETWEEN ? AND ?) OR (end_date BETWEEN ? AND ?))
            ORDER BY created_at DESC
        """;
        return template.query(sql, goalMapper, userId, startDate, endDate, startDate, endDate);
    }
    
    @Override
    public void updateGoal(GoalDTO goal) {
    	System.out.println("updateGoal 레퍼지토리 입장");
    	System.out.println("updateGoal 레퍼지토리 DTO:"+goal.toString());
    	String sql = """
    		    UPDATE goals 
    		    SET title = ?, description = ?, goal_type = ?, start_date = ?, 
    		        end_date = ?, completed = ?, is_active = ?, 
    		        updated_at = CURRENT_DATE 
    		    WHERE goal_id = ? AND user_id = ?
    		""";

    		template.update(sql,
    		    goal.getTitle(),
    		    goal.getDescription(),
    		    goal.getGoalType(),
    		    goal.getStartDate(),
    		    goal.getEndDate(),
    		    goal.isCompleted(),     // ✅ 추가
    		    goal.isActive(),
    		    goal.getGoalId(),
    		    goal.getUserId()
    		);
    }
    
    @Override
    public void deleteGoal(Long goalId, String userId) {
    	 String sql = "DELETE FROM goals WHERE goal_id = ? AND user_id = ?";
    	 template.update(sql, goalId, userId);
    }
    
    @Override
    public int toggleGoalCompletion(Long goalId) {
        String sql = "UPDATE goals SET completed = NOT completed, updated_at = CURRENT_TIMESTAMP WHERE goal_id = ?";
        return template.update(sql, goalId);
    }
    
    @Override
    public Map<String, Object> getMonthlyGoalProgress(String userId, int year, int month) {
        String sql = """
            SELECT 
                COUNT(*) AS total_goals,
                SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS achieved_goals
            FROM goals
            WHERE user_id = ? 
            AND goal_type = 'MONTHLY' 
            AND is_active = TRUE
            AND YEAR(start_date) = ? AND MONTH(start_date) = ?
        """;
        return template.queryForMap(sql, userId, year, month);
    }
    
    @Override
    public Map<String, Object> getWeeklyGoalProgress(String userId, LocalDate weekStart) {
        LocalDate weekEnd = weekStart.plusDays(6);

        String sql = """
            SELECT 
                COUNT(*) AS total_goals,
                SUM(CASE WHEN completed = TRUE THEN 1 ELSE 0 END) AS achieved_goals
            FROM goals
            WHERE user_id = ? 
            AND goal_type = 'WEEKLY' 
            AND is_active = TRUE
            AND start_date >= ? AND end_date <= ?
        """;
        return template.queryForMap(sql, userId, weekStart, weekEnd);
    }
    
 
    @Override
    public List<GoalDTO> findActiveGoalsByType(String userId, String goalType) {
        String sql = """
            SELECT * FROM goals 
            WHERE user_id = ? AND goal_type = ? AND is_active = TRUE 
            ORDER BY created_at DESC
        """;
        return template.query(sql, goalMapper, userId, goalType);
    }
    
    @Override
	public List<GoalDTO> getGoalsByDateRange(String userId, LocalDate start, LocalDate end) {
    	String sql = "SELECT * FROM goals WHERE user_id = ? AND start_date >= ? AND end_date <= ? ORDER BY start_date DESC";
        return template.query(sql, new GoalRowMapper(), userId, start, end);
	}

	private static class GoalRowMapper implements RowMapper<GoalDTO> {
        @Override
        public GoalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            GoalDTO goal = new GoalDTO();
            goal.setGoalId(rs.getLong("goal_id"));
            goal.setUserId(rs.getString("user_id"));
            goal.setTitle(rs.getString("title"));
            goal.setDescription(rs.getString("description"));
            goal.setGoalType(rs.getString("goal_type"));
            goal.setStartDate(rs.getDate("start_date").toLocalDate());
            goal.setEndDate(rs.getDate("end_date").toLocalDate());
            goal.setCompleted(rs.getBoolean("completed")); // ✅ 새 필드
            goal.setActive(rs.getBoolean("is_active"));
            LocalDate created = rs.getObject("created_at", LocalDate.class);
            goal.setCreatedAt(created);
            LocalDate updated = rs.getObject("updated_at", LocalDate.class);
            goal.setUpdatedAt(updated);
            return goal;
        }
    }
} 