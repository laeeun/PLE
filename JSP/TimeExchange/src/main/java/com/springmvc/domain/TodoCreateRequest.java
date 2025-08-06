package com.springmvc.domain;

import java.time.LocalDate;

import com.springmvc.enums.RecurrenceFreq;

public class TodoCreateRequest {

    private Long tradeId;
    private String writerId;
    private String receiverId;
    private String title;
    private String content;
    private Integer priority;
    private Boolean isPersonal;
    private LocalDate deadline;

    // 반복 설정
    private RecurrenceFreq freq;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean allDay = true;

    // === Getter/Setter ===
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
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

    @Override
    public String toString() {
        return "TodoCreateRequest{" +
                "tradeId=" + tradeId +
                ", writerId='" + writerId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", priority=" + priority +
                ", isPersonal=" + isPersonal +
                ", deadline=" + deadline +
                ", freq=" + freq +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", allDay=" + allDay +
                '}';
    }
}
