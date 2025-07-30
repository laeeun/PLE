package com.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springmvc.repository.TodoRepository;

@Service
public class TodoScheduler {

    private final TodoHistoryService todoHistoryService;
    private final TodoRepository todoRepository;

    @Autowired
    public TodoScheduler(TodoHistoryService todoHistoryService, TodoRepository todoRepository) {
        this.todoHistoryService = todoHistoryService;
        this.todoRepository = todoRepository;
    }

    // 매일 자정(00:00)에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void backupAndResetTodos() {
        // 1. 완료율 기록(todo_history에 백업)
        int backedUp = todoHistoryService.backupFromTodoTable();
        System.out.println("[스케줄러] 자정 백업 완료 - 기록된 개수: " + backedUp);

        // 2. todo 테이블 초기화
        int updated = todoRepository.resetTodosByDeadline(); // resetTodosByDeadline과 동일
        System.out.println("[스케줄러] 자정 초기화 완료 - 초기화된 투두 개수: " + updated);
    }
}