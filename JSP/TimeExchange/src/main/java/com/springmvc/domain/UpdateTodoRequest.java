package com.springmvc.domain;

public class UpdateTodoRequest {
	private Long todoId;
	private String title;
	private String content;
	public Long getTodoId() {
		return todoId;
	}
	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
