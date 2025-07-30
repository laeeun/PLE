package com.springmvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.springmvc.domain.TodoHistoryDTO;

public interface TodoHistoryService {
	// 특정 유저의 전체 이력 조회
    List<TodoHistoryDTO> findByReceiverId(String receiverId);

    // 최근 N일 통계 조회 (날짜, 전체 개수, 완료 개수)
    List<Map<String, Object>> findStatsByReceiverId(String receiverId);

    // 자정에 todo → todo_history 백업
    int backupFromTodoTable();
    
    List<Map<String, Object>> findStatsByDateRange(String receiverId, LocalDate start, LocalDate end);
}
