package com.springmvc.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.GoalDTO;
import com.springmvc.repository.GoalRepository;

@Service
public class GoalServiceImpl implements GoalService {
    
    @Autowired
    private GoalRepository goalRepository;
    
    @Override
    public void createGoal(GoalDTO goal) {
    	System.out.println("서비스 createGoal 입장");
    	LocalDate now = LocalDate.now();

        if ("WEEKLY".equals(goal.getGoalType())) {
            // 이번 주 월요일 ~ 일요일
            LocalDate start = now.with(DayOfWeek.MONDAY);
            LocalDate end = start.plusDays(6);
            goal.setStartDate(start);
            goal.setEndDate(end);
        } else if ("MONTHLY".equals(goal.getGoalType())) {
            // 이번 달 1일 ~ 말일
            LocalDate start = now.withDayOfMonth(1);
            LocalDate end = now.with(TemporalAdjusters.lastDayOfMonth());
            goal.setStartDate(start);
            goal.setEndDate(end);
        }
        
        // 생성일, 활성화 여부 등도 함께 설정 (권장)
        goal.setCreatedAt(now);
        goal.setUpdatedAt(now);
        goal.isActive(); // 혹시 DTO에서 기본값 안 줬다면 명시적으로 설정
        System.out.println("서비스DTO:"+goal);
        goalRepository.createGoal(goal);
    }
    
    @Override
    public List<GoalDTO> findActiveGoalsByUserId(String userId) {
        return goalRepository.findActiveGoalsByUserId(userId);
    }
    
    @Override
    public List<GoalDTO> findGoalsByPeriod(String userId, LocalDate startDate, LocalDate endDate) {
        return goalRepository.findGoalsByPeriod(userId, startDate, endDate);
    }
    
    @Override
    public void updateGoal(GoalDTO goal) {
        LocalDate now = LocalDate.now();

        if ("WEEKLY".equals(goal.getGoalType())) {
            LocalDate start = now.with(DayOfWeek.MONDAY);
            LocalDate end = start.plusDays(6);
            goal.setStartDate(start);
            goal.setEndDate(end);
        } else if ("MONTHLY".equals(goal.getGoalType())) {
            LocalDate start = now.withDayOfMonth(1);
            LocalDate end = now.with(TemporalAdjusters.lastDayOfMonth());
            goal.setStartDate(start);
            goal.setEndDate(end);
        }

        goal.setUpdatedAt(now);
        goalRepository.updateGoal(goal);
    }
    
    @Override
    public void deleteGoal(Long goalId, String userId) {
        goalRepository.deleteGoal(goalId, userId);
    }
    
    
    @Override
    public Map<String, Object> getMonthlyGoalProgress(String userId, int year, int month) {
        return goalRepository.getMonthlyGoalProgress(userId, year, month);
    }
    
    @Override
    public Map<String, Object> getWeeklyGoalProgress(String userId, LocalDate weekStart) {
        return goalRepository.getWeeklyGoalProgress(userId, weekStart);
    }
    
    
    @Override
    public List<GoalDTO> findActiveGoalsByType(String userId, String goalType) {
        return goalRepository.findActiveGoalsByType(userId, goalType);
    }

	@Override
	public int toggleGoalCompletion(Long goalId) {
		return goalRepository.toggleGoalCompletion(goalId);
	}

	@Override
	public List<GoalDTO> getGoalsByDateRange(String userId, LocalDate start, LocalDate end) {
		return goalRepository.getGoalsByDateRange(userId, start, end);
	}
	
} 