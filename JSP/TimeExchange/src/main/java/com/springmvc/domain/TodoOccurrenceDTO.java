package com.springmvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoOccurrenceDTO {
    private Long occurrenceId;
    private Long todoId;              // = todo.todo_id (과거 스키마는 recurrence_id)
    private LocalDate occurDate;
    private Boolean completed;
    private Boolean hidden;
    private LocalDateTime createdAt;
    private String title;
    private String writerId;
    private String receiverId;
    private java.time.LocalDate deadline;
    private com.springmvc.enums.RecurrenceFreq freq;
    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
    private Boolean allDay;
	public Long getOccurrenceId() {
		return occurrenceId;
	}
	public void setOccurrenceId(Long occurrenceId) {
		this.occurrenceId = occurrenceId;
	}
	public Long getTodoId() {
		return todoId;
	}
	public void setTodoId(Long todoId) {
		this.todoId = todoId;
	}
	public LocalDate getOccurDate() {
		return occurDate;
	}
	public void setOccurDate(LocalDate occurDate) {
		this.occurDate = occurDate;
	}
	
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriterId() {
		return writerId;
	}
	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public java.time.LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(java.time.LocalDate deadline) {
		this.deadline = deadline;
	}
	public com.springmvc.enums.RecurrenceFreq getFreq() {
		return freq;
	}
	public void setFreq(com.springmvc.enums.RecurrenceFreq freq) {
		this.freq = freq;
	}
	public java.time.LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(java.time.LocalDate startDate) {
		this.startDate = startDate;
	}
	public java.time.LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(java.time.LocalDate endDate) {
		this.endDate = endDate;
	}
	public Boolean getAllDay() {
		return allDay;
	}
	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}
    
    
    
}
