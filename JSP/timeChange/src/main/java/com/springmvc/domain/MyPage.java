package com.springmvc.domain;

import java.util.List;

public class MyPage {
	
    private String memberId;
    
    private String name;
    
    private String password;
    
    private String email;
    
    private String phone;
    
    private String address;
    
    private boolean isExpert = false;
    
    private int balance; // 잔여시간
    
    private List<Review> myReviews; // 내가 쓴 리뷰 목록
    
    private List<History> myHistory; // 내 거래 내역

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<Review> getMyReviews() {
		return myReviews;
	}

	public void setMyReviews(List<Review> myReviews) {
		this.myReviews = myReviews;
	}

	public List<History> getMyHistory() {
		return myHistory;
	}

	public void setMyHistory(List<History> myHistory) {
		this.myHistory = myHistory;
	}

	@Override
	public String toString() {
		return "MyPage [memberId=" + memberId + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", isExpert=" + isExpert + ", balance=" + balance
				+ ", myReviews=" + myReviews + ", myHistory=" + myHistory + "]";
	}

	
    
    
}
