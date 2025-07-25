package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TodoDTO;
import com.springmvc.repository.TodoRepository;

// ✅ 할일 서비스 구현체 - 비즈니스 로직을 처리하고 Repository와 Controller를 연결하는 중간 계층
@Service
public class TodoServiceImpl implements TodoService {
    
    // ✅ TodoRepository 의존성 주입
    @Autowired
    TodoRepository todoRepository;

    // ✅ 할일 생성
    @Override
    public void createTODO(TodoDTO todo) {
        todoRepository.createTODO(todo);
    }

    // ✅ 특정 사용자가 받은 모든 할일 목록 조회 (개인 + 숙제 포함)
    @Override
    public List<TodoDTO> findByReceiverId(String receiverId) {
        return todoRepository.findByReceiverId(receiverId);
    }

    // ✅ 특정 할일 상세 조회 (todo_id 기준)
    @Override
    public TodoDTO findById(long todoId) {
        return todoRepository.findById(todoId);
    }

    // ✅ 할일 수정
    @Override
    public void updateTODO(TodoDTO todo) {
        todoRepository.updateTODO(todo);
    }

    // ✅ 할일 삭제
    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }

    // ✅ 완료 여부로 필터링된 할일 목록 조회 (ex: 완료된 것만, 미완료된 것만)
    @Override
    public List<TodoDTO> findByCompleted(String receiverId, boolean completed) {
        return todoRepository.findByCompleted(receiverId, completed);
    }

    // ✅ 특정 할일의 완료 상태만 빠르게 업데이트 (AJAX용)
    @Override
    public void updateCompleted(long todoId, boolean completed) {
        todoRepository.updateCompleted(todoId, completed);
    }

    // ✅ 내가 만든 개인 할일 목록만 조회
    @Override
    public List<TodoDTO> findPersonalTodos(String receiverId) {
        return todoRepository.findPersonalTodos(receiverId);
    }

    // ✅ 다른 사람이 나에게 준 숙제만 조회 (isPersonal = false)
    @Override
    public List<TodoDTO> findAssignedTodos(String receiverId) {
        return todoRepository.findAssignedTodos(receiverId);
    }

    // ✅ 내가 판매자로서 준 숙제 목록 조회 (내가 writer인 할일들)
    @Override
    public List<TodoDTO> findByWriterId(String writerId) {
        return todoRepository.findByWriterId(writerId);
    }

    // ✅ 특정 거래에 연결된 숙제 목록 조회
    @Override
    public List<TodoDTO> findByTradeId(Long tradeId) {
        return todoRepository.findByTradeId(tradeId);
    }
    
    @Override
    public List<TodoDTO> findByWriterId(String writerId, Boolean completed) {
        return todoRepository.findByWriterId(writerId, completed);
    }

    @Override
    public List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed) {
        if (completed == null) {
            return todoRepository.findAssignedTodos(receiverId);
        }
        // completed 조건도 같이 조회
        return todoRepository.findAssignedTodos(receiverId, completed);
    }

    @Override
    public List<TodoDTO> findAllTodos(String receiverId, Boolean completed) {
        if (completed == null) {
            return todoRepository.findByReceiverId(receiverId);
        }
        return todoRepository.findByCompleted(receiverId, completed);
    }

    
}
