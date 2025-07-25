package com.springmvc.service;

import java.util.List;
import com.springmvc.domain.TodoDTO;

public interface TodoService {

    // ✅ 생성
    void createTODO(TodoDTO todo);
    
    // ✅ 전체 할일 조회 (내가 받은 것 기준, 중요도 순 정렬)
    List<TodoDTO> findByReceiverId(String receiverId);

    // ✅ 한 개 할일 조회
    TodoDTO findById(long todoId);

    // ✅ 수정
    void updateTODO(TodoDTO todo);

    // ✅ 삭제
    void deleteById(long todoId);

    // ✅ 완료 여부 필터링
    List<TodoDTO> findByCompleted(String receiverId, boolean completed);

    // ✅ 완료 여부만 빠르게 수정 (AJAX용)
    void updateCompleted(long todoId, boolean completed);

    // ✨ 내가 만든 개인 할일만 조회
    List<TodoDTO> findPersonalTodos(String receiverId);

    // ✨ 판매자가 나에게 준 숙제만 조회
    List<TodoDTO> findAssignedTodos(String receiverId);

    // ✨ 내가 판매자로서 준 숙제들 조회
    List<TodoDTO> findByWriterId(String writerId);

    // ✨ 특정 거래에 연결된 숙제들 조회
    List<TodoDTO> findByTradeId(Long tradeId);
    
    List<TodoDTO> findByWriterId(String writerId, Boolean completed);

    // 받은 숙제 필터링용
    List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed);

    // 전체 할일 필터링용
    List<TodoDTO> findAllTodos(String receiverId, Boolean completed);


}
