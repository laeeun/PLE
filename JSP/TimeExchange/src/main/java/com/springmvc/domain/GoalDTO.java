package com.springmvc.domain;

import java.time.LocalDate;

public class GoalDTO {
    private Long goalId;
    private String userId;
    private String title;
    private String description;
    private String goalType; // "WEEKLY", "MONTHLY"
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean completed; // ✅ 완료 여부
    private boolean isActive = true;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // 생성자
    public GoalDTO() {}

    public GoalDTO(String userId, String title, String description, String goalType,
                   LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.goalType = goalType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = false;
        this.isActive = true;
        this.createdAt = LocalDate.now();
    }

    // Getter & Setter
    public Long getGoalId() { return goalId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }

    // 목표 달성 여부 (단순 반환)
    public boolean isAchieved() {
        return completed;
    }

	@Override
	public String toString() {
		return "GoalDTO [goalId=" + goalId + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", goalType=" + goalType + ", startDate=" + startDate + ", endDate=" + endDate + ", completed="
				+ completed + ", isActive=" + isActive + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
}
