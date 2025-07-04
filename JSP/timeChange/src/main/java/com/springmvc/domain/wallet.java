package com.springmvc.domain;

import java.time.LocalDateTime;

public class wallet {
	
	private long walletId;
	
	private String memberId;
	
	private int balance;
	
	private LocalDateTime updatedAt;

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "wallet [walletId=" + walletId + ", memberId=" + memberId + ", balance=" + balance + ", updatedAt="
				+ updatedAt + "]";
	}

	
	

}
