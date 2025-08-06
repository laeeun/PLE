package com.springmvc.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
    
    @Autowired
    TodoHistoryService todoHistoryService;
    // ✅ 할일 생성
    @Override
    public void createTODO(TodoDTO todo) {
        todoRepository.createTODO(todo);
    }

    // ✅ 특정 사용자가 받은 모든 할일 목록 조회 (개인 + 숙제 포함)
    @Override
    public List<TodoDTO> findByReceiverId(String receiverId) {
        List<TodoDTO> todos = todoRepository.findByReceiverId(receiverId);         // 비반복 TODO
        List<TodoDTO> recurring = todoRepository.findOccurrencesByReceiverId(receiverId); // 반복 TODO

        // ✅ 날짜 포맷터 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // ✅ 포맷된 문자열 추가
        todos.forEach(todo -> {
        	System.out.println(todo.getStartDate());
        	System.out.println(todo.getEndDate());
            if (todo.getStartDate() != null)
                todo.setStartDateStr(todo.getStartDate().format(formatter));
            if (todo.getEndDate() != null)
                todo.setEndDateStr(todo.getEndDate().format(formatter));
        });

        recurring.forEach(todo -> {
        	System.out.println("recurring"+todo.getStartDate());
        	System.out.println("recurring"+todo.getEndDate());
            if (todo.getStartDate() != null)
                todo.setStartDateStr(todo.getStartDate().format(formatter));
            if (todo.getEndDate() != null)
                todo.setEndDateStr(todo.getEndDate().format(formatter));
        });

        todos.addAll(recurring);
        return todos;
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
        
        // ✅ 실시간 백업: 완료 상태 변경 시 히스토리에 기록
        if (completed) {
            TodoDTO todo = todoRepository.findById(todoId);
            if (todo != null && todo.getDeadline() != null && 
                todo.getDeadline().isBefore(LocalDate.now())) {
                // 데드라인이 지난 할일이 완료되면 히스토리에 백업
                todoHistoryService.backupSingleTodo(todoId);
            }
        }
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

	@Override
	public Map<String, Object> getTodayStats(String receiverId) {
		return todoRepository.getTodayStats(receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsAll(String receiverId) {
		return todoRepository.getTodayStatsAll(receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsAssigned(String receiverId) {
		return todoRepository.getTodayStatsAssigned(receiverId);
	}

	@Override
	public Map<String, Object> getTodayStatsCreated(String writerId) {
		return todoRepository.getTodayStatsCreated(writerId);
	}
	
	    @Override
    public int updateTitleContent(long todoId, String title, String content, String ownerId) {
        return todoRepository.updateTitleContent(todoId, title, content, ownerId);
    }
    
    // 반복 주기 필터링 메서드들
    @Override
    public List<TodoDTO> findByWriterId(String writerId, Boolean completed, String freq) {
        return todoRepository.findByWriterId(writerId, completed, freq);
    }

    @Override
    public List<TodoDTO> findAssignedTodos(String receiverId, Boolean completed, String freq) {
        return todoRepository.findAssignedTodos(receiverId, completed, freq);
    }

    @Override
    public List<TodoDTO> findAllTodos(String receiverId, Boolean completed, String freq) {
        return todoRepository.findAllTodos(receiverId, completed, freq);
    }
	
}
