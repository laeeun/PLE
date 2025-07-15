package com.springmvc.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class History {
	
	public History() {
		this.created_at = Timestamp.valueOf(LocalDateTime.now());  // 현재 시간으로 초기화
	}
	
	
    private Long history_id;        // 기본키

    private String buyer_id;        // 구매자 ID
    private String seller_id;       // 판매자 ID

    private Long talent_id;         // 어떤 재능 거래인지
    private String category;        // 카테고리

    private int account;            // 거래 시간
    private int balance_change;     // 시간 지갑 증감 (예: -30, +30)

    private String type;            // PURCHASE, TRANSFER 등
    private Timestamp created_at;   // 거래 일시
    
    
    
	public Long getHistory_id() {
		return history_id;
	}
	
	public void setHistory_id(Long history_id) {
		this.history_id = history_id;
	}
	
	public String getBuyer_id() {
		return buyer_id;
	}
	
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	
	public String getSeller_id() {
		return seller_id;
	}
	
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	
	public Long getTalent_id() {
		return talent_id;
	}
	
	public void setTalent_id(Long talent_id) {
		this.talent_id = talent_id;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getAccount() {
		return account;
	}
	
	public void setAccount(int account) {
		this.account = account;
	}
	
	public int getBalance_change() {
		return balance_change;
	}
	
	public void setBalance_change(int balance_change) {
		this.balance_change = balance_change;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Timestamp getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
	@Override
	public String toString() {
		return "History [history_id=" + history_id + ", buyer_id=" + buyer_id + ", seller_id=" + seller_id
				+ ", talent_id=" + talent_id + ", category=" + category + ", account=" + account + ", balance_change="
				+ balance_change + ", type=" + type + ", created_at=" + created_at + "]";
	}

    
}
