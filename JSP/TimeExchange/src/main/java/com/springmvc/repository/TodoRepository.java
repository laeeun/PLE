package com.springmvc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springmvc.domain.TodoCreateRequest;
import com.springmvc.domain.TodoDTO;
import com.springmvc.enums.RecurrenceFreq;

public interface TodoRepository {

    // ✅ 할일 생성 (개인 할일 or 숙제)
    void createTODO(TodoDTO todo);

    // ✅ 내가 받은 전체 할일 조회 (receiver_id 기준, 개인+숙제 모두 포함)
    List<TodoDTO> findByReceiverId(String receiverId);

    // ✅ 단일 할일 조회 (todo_id 기준)
    TodoDTO findById(long todoId);

    // ✅ 전체 항목 수정 (제목, 내용, 우선순위, 완료 여부 포함)
    void updateTODO(TodoDTO todo);

    // ✅ 할일 삭제 (todo_id 기준)
    void deleteById(long todoId);

    // ✅ 받은 할일 중 완료 여부로 필터링 (receiver_id + completed)
    List<TodoDTO> findByCompleted(String receiverId, boolean completed);

    // ✅ 할일 완료 상태만 빠르게 수정 (AJAX 체크박스 토글용)
    void updateCompleted(long todoId, boolean completed);

    // ✅ 내가 만든 개인 할일만 조회 (receiver_id = 나 && is_personal = true)
    List<TodoDTO> findPersonalTodos(String receiverId);

    // ✅ 판매자가 나에게 준 숙제만 조회 (receiver_id = 나 && is_personal = false)
    List<TodoDTO> findAssignedTodos(String receiverId);

    // ✅ 내가 작성한 모든 할일 조회 (writer_id 기준, 완료 조건 없음)
    List<TodoDTO> findByWriterId(String writerId);

    // ✅ 내가 작성한 할일 중 완료 여부로 필터링 (writer_id + completed)
    List<TodoDTO> findByWriterId(String writerId, Boolean completed);

    // ✅ 특정 거래(trade_id)에 연결된 숙제 목록 조회
    List<TodoDTO> findByTradeId(Long tradeId);
    
    // 받은 숙제 + 완료 필터
    List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed);
    
    // 마감일이 자정을 지난 투두들 초기화
    public int resetTodosByDeadline();
    
    public Map<String, Object> getTodayStats(String receiverId);
    
    Map<String, Object> getTodayStatsAll(String receiverId);       // 전체(내가 받은 모든 것)
    
    Map<String, Object> getTodayStatsAssigned(String receiverId);  // 받은 숙제(= is_personal=false)
    
    Map<String, Object> getTodayStatsCreated(String writerId);     // 내가 생성한 할일(= writer 기준)
    
    int updateTitleContent(long todoId, String title, String content, String ownerId);
    
    // 데드라인 알림용 메서드
    List<TodoDTO> findByDeadline(LocalDate deadline);
    
    // 누락된 백업 확인용 메서드
    int countMissedBackups(LocalDate date);
    
    // 세분화된 통계 메서드들
    Map<String, Object> getTodayStatsByPriority(String receiverId);
    Map<String, Object> getTodayStatsByTimeframe(String receiverId);
    
    // 반복 주기 필터링 메서드들
    List<TodoDTO> findByWriterId(String writerId, Boolean completed, String freq);
    List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed, String freq);
    List<TodoDTO> findAllTodos(String receiverId, Boolean completed, String freq);
    
    /** 새로운 Todo를 저장하고 생성된 PK(todo_id)를 반환 */
    Long insertTodo(TodoCreateRequest req);

    /** PK로 TodoDTO를 Optional로 조회 */
    Optional<TodoDTO> findByIdAsDto(Long todoId);
    
    int updateBasic(Long todoId, String title, String content, Integer priority, LocalDate deadline,
            RecurrenceFreq freq, LocalDate startDate, LocalDate endDate, Boolean allDay);
    public List<TodoDTO> findOccurrencesByReceiverId(String receiverId);
}
