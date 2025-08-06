package com.springmvc.service;

import com.springmvc.domain.GoalDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GoalService {
    // 목표 생성
    void createGoal(GoalDTO goal);
    
    // 사용자의 활성 목표 조회
    List<GoalDTO> findActiveGoalsByUserId(String userId);
    
    // 특정 기간의 목표 조회
    List<GoalDTO> findGoalsByPeriod(String userId, LocalDate startDate, LocalDate endDate);
    
    // 목표 업데이트
    void updateGoal(GoalDTO goal);
    
    // 목표 삭제
    void deleteGoal(Long goalId, String userId);
    
       
    // 월간 목표 진행률 조회
    Map<String, Object> getMonthlyGoalProgress(String userId, int year, int month);
    
    // 주간 목표 진행률 조회
    Map<String, Object> getWeeklyGoalProgress(String userId, LocalDate weekStart);
     
    
    List<GoalDTO> findActiveGoalsByType(String userId, String goalType);
    
    int toggleGoalCompletion(Long goalId);
    
    List<GoalDTO> getGoalsByDateRange(String userId, LocalDate start, LocalDate end);
} 