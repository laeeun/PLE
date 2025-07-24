package com.springmvc.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvc.domain.TodoDTO;

@Repository
public class TodoRepositoryImpl implements TodoRepository{
	
	JdbcTemplate template;
	
	@Autowired
	TodoRepositoryImpl(JdbcTemplate template){
		this.template = template;
	}
	
	@Override
	public void createTODO(TodoDTO todo) {
		String sql = "INSERT INTO todo (member_id, title, content, priority, completed, created_at, updated_at) "
	               + "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";

	    template.update(sql,
	        todo.getMemberId(),
	        todo.getTitle(),
	        todo.getContent(),
	        todo.getPriority(),
	        todo.isCompleted()
	    );
	}

	@Override
	public List<TodoDTO> readAllTODO(String memberId) {
	    String sql = "SELECT * FROM todo WHERE member_id = ? ORDER BY priority ASC, created_at DESC";
	    return template.query(sql, new TodoRowMapper(), memberId);
	}

	@Override
	public void updateTODO(TodoDTO todo) {
	    String sql = "UPDATE todo SET title = ?, content = ?, priority = ?, completed = ?, updated_at = NOW() "
	               + "WHERE todo_id = ? AND member_id = ?";
	    
	    template.update(sql,
	        todo.getTitle(),
	        todo.getContent(),
	        todo.getPriority(),
	        todo.isCompleted(),
	        todo.getTodoId(),
	        todo.getMemberId()
	    );
	}

	@Override
	public void deleteTODO(long TodoId) {
		String sql = "DELETE FROM todo WHERE todo_id = ?";
	    template.update(sql, TodoId);
	}

	@Override
	public TodoDTO readoneTODO(long TodoId) {
		String sql = "SELECT * FROM todo WHERE todo_id = ?";
	    try {
	        return template.queryForObject(sql, new TodoRowMapper(), TodoId);
	    } catch (EmptyResultDataAccessException e) {
	        return null; // 또는 Optional.empty() 등으로 처리
	    }
	}

	@Override
	public List<TodoDTO> readTODOByCompleted(String memberId, boolean completed) {
		String sql = "SELECT * FROM todo WHERE member_id = ? AND completed = ? "
	               + "ORDER BY priority ASC, created_at DESC";
	    return template.query(sql, new TodoRowMapper(), memberId, completed);
	}

	@Override
	public void updateCompleted(long todoId, boolean completed) {
		String sql = "UPDATE todo SET completed = ?, updated_at = NOW() WHERE todo_id = ?";
	    template.update(sql, completed, todoId);
	}
	
}
