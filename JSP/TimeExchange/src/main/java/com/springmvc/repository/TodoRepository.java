package com.springmvc.repository;

import java.util.List;
import com.springmvc.domain.TodoDTO;

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


}
