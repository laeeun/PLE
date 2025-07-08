package com.springmvc.domain;

import java.time.LocalDateTime;

public class History {

    private Long historyId;         
    // 거래 내역 고유 ID (DB의 PK 역할로 사용)

    private String targetName;        
    // 거래 상대방의 닉네임 (상대 사용자)

    private String type;            
    // 거래 유형: "SEND" = 내가 시간을 보낸 경우, "RECEIVE" = 내가 시간을 받은 경우

    private int durationTime;       
    // 거래 시간 (단위: 분) → 30 = 30분, 90 = 1시간 30분
    
    private int balance; // 잔여시간

    private LocalDateTime createdAt;
    // 거래가 이루어진 일시 (날짜 + 시간)

    private String memo;            
    // 메모, 간단한 설명 (예: "프로그래밍 수업", "요리 강습")

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}


	public String getTargetId() {
		return targetName;
	}

	public void setTargetId(String targetId) {
		this.targetName = targetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "History [historyId=" + historyId + ", targetName=" + targetName + ", type=" + type + ", durationTime="
				+ durationTime + ", balance=" + balance + ", createdAt=" + createdAt + ", memo=" + memo + "]";
	}

    
}
