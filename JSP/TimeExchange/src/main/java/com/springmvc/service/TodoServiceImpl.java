package com.springmvc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.TodoDTO;
import com.springmvc.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService{
	@Autowired
	TodoRepository todoRepository;

	


	@Override
	public void createTODO(TodoDTO todo) {
		todoRepository.createTODO(todo);
		
	}

	@Override
	public List<TodoDTO> readAllTODO(String memberId) {		
		return todoRepository.readAllTODO(memberId);
	}

	@Override
	public void updateTODO(TodoDTO todo) {
		todoRepository.updateTODO(todo);
		
	}

	@Override
	public void deleteTODO(long TodoId) {
		todoRepository.deleteTODO(TodoId);
		
	}

	@Override
	public TodoDTO readoneTODO(long TodoId) {
		return todoRepository.readoneTODO(TodoId);
	}

	@Override
	public List<TodoDTO> readTODOByCompleted(String memberId, boolean completed) {
		return todoRepository.readTODOByCompleted(memberId, completed);
	}

	@Override
	public void updateCompleted(long todoId, boolean completed) {
		todoRepository.updateCompleted(todoId, completed);
	}
	
	
}
