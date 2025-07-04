package com.springmvc.domain;

import java.time.LocalDateTime;

public class AdminMember {

    private String memberId;         // 회원 고유 ID
    
    private String username;         // 사용자명
    
    private String name;             // 이름
    
    private String email;            // 이메일
    
    private boolean isExpert;        // 전문가 여부
    
    private int balance;             // 현재 보유 시간 (분 단위)
    
    private boolean isActive;        // 계정 활성화 여부
    
    private String statusMessage;    // 계정 비활성화 사유
    
    private LocalDateTime createdAt; // 가입일

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isExpert() {
		return isExpert;
	}

	public void setExpert(boolean isExpert) {
		this.isExpert = isExpert;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "AdminMember [memberId=" + memberId + ", username=" + username + ", name=" + name + ", email=" + email
				+ ", isExpert=" + isExpert + ", balance=" + balance + ", isActive=" + isActive + ", statusMessage="
				+ statusMessage + ", createdAt=" + createdAt + "]";
	}
    
    
}
