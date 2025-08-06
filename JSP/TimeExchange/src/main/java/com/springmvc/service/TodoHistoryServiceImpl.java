package com.springmvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TodoHistoryDTO;
import com.springmvc.repository.TodoHistoryRepository;

@Service
public class TodoHistoryServiceImpl implements TodoHistoryService {
    
    private final TodoHistoryRepository todoHistoryRepository;

    @Autowired
    public TodoHistoryServiceImpl(TodoHistoryRepository todoHistoryRepository) {
        this.todoHistoryRepository = todoHistoryRepository;
    }

    @Override
    public List<TodoHistoryDTO> findByReceiverId(String receiverId) {
        return todoHistoryRepository.findByReceiverId(receiverId);
    }

    @Override
    public List<Map<String, Object>> findStatsByReceiverId(String receiverId) {
        return todoHistoryRepository.findStatsByReceiverId(receiverId);
    }

    	@Override
	public int backupFromTodoTable() {
		return todoHistoryRepository.backupFromTodoTable();
	}
	
	@Override
	public void backupSingleTodo(Long todoId) {
		todoHistoryRepository.backupSingleTodo(todoId);
	}

	@Override
	public List<Map<String, Object>> findStatsByDateRange(String receiverId, LocalDate start, LocalDate end) {
		return todoHistoryRepository.findStatsByDateRange(receiverId, start, end);
	}
    
}
