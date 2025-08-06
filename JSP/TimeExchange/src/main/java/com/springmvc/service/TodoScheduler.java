package com.springmvc.service;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TodoDTO;
import com.springmvc.repository.TodoRepository;

@Service
public class TodoScheduler {

    private final TodoHistoryService todoHistoryService;
    private final TodoRepository todoRepository;
    private final NotificationService notificationService;

    @Autowired
    public TodoScheduler(TodoHistoryService todoHistoryService, TodoRepository todoRepository, NotificationService notificationService) {
        this.todoHistoryService = todoHistoryService;
        this.todoRepository = todoRepository;
        this.notificationService = notificationService;
    }
    
    
    // ✅ 서버 시작 시 누락된 백업 처리
    @PostConstruct
    public void handleMissedBackups() {
        System.out.println("[스케줄러] 서버 시작 - 누락된 백업 처리 시작");
        
        // 어제 날짜 계산
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        // 어제 백업이 안 된 데이터가 있는지 확인
        int missedBackups = todoRepository.countMissedBackups(yesterday);
        if (missedBackups > 0) {
            System.out.println("[스케줄러] 누락된 백업 발견: " + missedBackups + "개");
            int backedUp = todoHistoryService.backupFromTodoTable();
            System.out.println("[스케줄러] 누락된 백업 완료 - 기록된 개수: " + backedUp);
        }
        
        // 데드라인 지난 할일 초기화
        int updated = todoRepository.resetTodosByDeadline();
        System.out.println("[스케줄러] 데드라인 초기화 완료 - 초기화된 투두 개수: " + updated);
    }
    
    // 매일 자정(00:00)에 실행 (기존 유지)
    @Scheduled(cron = "0 5 0 * * *", zone = "Asia/Seoul")
    public void backupAndResetTodos() {
        System.out.println("[스케줄러] 자정(00:05 KST) 작업 시작 "
            + java.time.ZonedDateTime.now(java.time.ZoneId.of("Asia/Seoul")));
        int backedUp = todoHistoryService.backupFromTodoTable();
        System.out.println("[스케줄러] 자정 백업 완료 - 기록된 개수: " + backedUp);

        int updated = todoRepository.resetTodosByDeadline();
        System.out.println("[스케줄러] 자정 초기화 완료 - 초기화된 투두 개수: " + updated);
    }
    
    // ✅ 매일 오전 9시에 데드라인 알림 발송
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void sendDeadlineNotifications() {
        System.out.println("[스케줄러] 데드라인 알림 발송 시작");
        
        LocalDate today = LocalDate.now();
        List<TodoDTO> deadlineTodos = todoRepository.findByDeadline(today);
        
        for (TodoDTO todo : deadlineTodos) {
            if (!todo.isCompleted()) {
                String content = String.format("오늘 마감인 할일이 있습니다: %s", todo.getTitle());
                
                notificationService.createSimpleNotification(
                    "시스템", 
                    todo.getReceiverId(), 
                    "숙제", 
                    content, 
                    todo.getTodoId(), 
                    "todo"
                );
            }
        }
        
        System.out.println("[스케줄러] 데드라인 알림 발송 완료 - 발송된 알림 개수: " + deadlineTodos.size());
    }
}