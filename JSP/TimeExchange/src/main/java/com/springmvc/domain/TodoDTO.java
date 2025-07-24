package com.springmvc.domain;

import java.time.LocalDateTime;

public class TodoDTO {
	private long todoId;
	private String memberId;
	private String title;
	private String content;
	private int Priority = 2;
	private boolean completed = false;
	LocalDateTime created_at  =LocalDateTime.now();
	LocalDateTime updated_at  =LocalDateTime.now();
	
	public TodoDTO() {}
	public TodoDTO(long todoId, String memberId, String title, String content, int Priority, boolean completed,
			LocalDateTime created_at, LocalDateTime updated_at) {
		super();
		this.todoId = todoId;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.Priority = Priority;
		this.completed = completed;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	
	public long getTodoId() {
		return todoId;
	}
	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
	
	public int getPriority() {
		return Priority;
	}
	public void setPriority(int priority) {
		Priority = priority;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	@Override
	public String toString() {
		return "TodoDTO [todoId=" + todoId + ", memberId=" + memberId + ", title=" + title + ", content=" + content
				+ ", priorty=" + Priority + ", completed=" + completed + ", created_at=" + created_at + ", updated_at="
				+ updated_at + "]";
	}
	
	
	
}
