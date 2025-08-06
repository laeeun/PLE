package com.springmvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springmvc.enums.RecurrenceFreq;

//✅ 할일(Todo) 정보를 담는 DTO 클래스
public class TodoDTO {

	 // 고유 ID (PK)
	 private long todoId;
	
	 // 연관된 거래 ID (숙제일 경우에만 해당, null이면 일반 할일)
	 private Long tradeId;
	
	 // 작성자 ID
	 // - 내가 만든 개인 할일이면 나 자신
	 // - 판매자가 할당한 숙제면 판매자 ID
	 private String writerId;
	
	 // 받는 사람 ID
	 // - 일반 할일이면 나 자신
	 // - 숙제면 구매자 ID (즉, 숙제를 받은 사람)
	 private String receiverId;
	
	 // 할일 제목
	 private String title;
	
	 // 할일 내용 (설명)
	 private String content;
	
	 // 우선순위 (1: 높음, 2: 보통, 3: 낮음) 기본값은 2
	 private int priority = 2;
	
	 // 완료 여부 (기본값: false)
	 private boolean completed = false;
	
	 // ✅ 이 할일이 내가 직접 만든 개인 할일인지 여부
	 // true: 내가 만든 개인 할일
	 // false: 누군가(판매자)가 나에게 준 숙제
	 private boolean isPersonal = true;
	 
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 private LocalDate deadline;
	 // 생성 시각
	 private LocalDateTime created_at = LocalDateTime.now();
	
	 // 마지막 수정 시각
	 private LocalDateTime updated_at = LocalDateTime.now();
	 public RecurrenceFreq freq;        // DAILY / WEEKLY / MONTHLY
     public LocalDate startDate;        // NOT NULL
     public LocalDate endDate;  
     public Boolean allDay = true;      // default true
     private String startDateStr;
     private String endDateStr;
     private boolean occurrence;
     
     
	public boolean isOccurrence() {
		return occurrence;
	}

	public void setOccurrence(boolean occurrence) {
		this.occurrence = occurrence;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public RecurrenceFreq getFreq() {
		return freq;
	}

	public void setFreq(RecurrenceFreq freq) {
		this.freq = freq;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public LocalDate getDeadline() {
		return deadline;
	}
	
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public long getTodoId() {
		return todoId;
	}

	public void setTodoId(long todoId) {
		this.todoId = todoId;
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
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
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isPersonal() {
		return isPersonal;
	}

	public void setPersonal(boolean isPersonal) {
		this.isPersonal = isPersonal;
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
		return "TodoDTO [todoId=" + todoId + ", tradeId=" + tradeId + ", writerId=" + writerId + ", receiverId="
				+ receiverId + ", title=" + title + ", content=" + content + ", priority=" + priority + ", completed="
				+ completed + ", isPersonal=" + isPersonal + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ "]";
	}


}
