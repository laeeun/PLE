package com.springmvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoHistoryDTO {
    private Long historyId;
    private Long todoId;
    private String writerId;
    private String receiverId;

    private String title;
    private boolean completed;
    private LocalDate deadline;

    private LocalDateTime recordedAt;

    // ✅ Getter & Setter
    public Long getHistoryId() {
        return historyId;
    }
    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
    public Long getTodoId() {
        return todoId;
    }
    public void setTodoId(Long todoId) {
        this.todoId = todoId;
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
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
