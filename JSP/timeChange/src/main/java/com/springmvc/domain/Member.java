package com.springmvc.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;


import org.springframework.format.annotation.DateTimeFormat;

public class Member {
	

	
    private String member_id;
    
    private String userName;
    
    private String pw;
    
    private String name;
    
 
    private String emailId;      
    private String emailDomain; 

    
    private String email;    
    
    private String phone;
    
    private String phone1;
    
    private String phone2;
    
    private String phone3;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 폼 요청일 경우
    private LocalDate birthDate;
    
    private String gender;        // 성별 ("M", "F")
    
    private String addr;

    private boolean expert; // 기본값: 일반 사용자
    
    private int account = 0;
    
    private LocalDateTime createdAt; // DB 자동 생성, 필요 시 조회용 현재시간으로 초기화해서 바꾸기    
    
    private boolean emailVerified = false;
    private String emailToken;
    private LocalDateTime tokenCreatedAt;

    


	public Member() {
	    this.createdAt = LocalDateTime.now(); // 현재 시간으로 초기화
	}
    

	

	public String getMember_id() {
		return member_id;
	}




	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}




	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getPw() {
		return pw;
	}




	public void setPw(String pw) {
		this.pw = pw;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getEmailId() {
		return emailId;
	}




	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




	public String getEmailDomain() {
		return emailDomain;
	}




	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
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




	public String getPhone1() {
		return phone1;
	}




	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}




	public String getPhone2() {
		return phone2;
	}




	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}




	public String getPhone3() {
		return phone3;
	}




	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}




	public LocalDate getBirthDate() {
		return birthDate;
	}




	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public String getAddr() {
		return addr;
	}




	public void setAddr(String addr) {
		this.addr = addr;
	}




	public boolean isExpert() {
		return expert;
	}




	public void setExpert(boolean expert) {
		this.expert = expert;
	}




	public int getAccount() {
		return account;
	}




	public void setAccount(int account) {
		this.account = account;
	}




	public LocalDateTime getCreatedAt() {
		return createdAt;
	}




	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isEmailVerified() {
	    return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
	    this.emailVerified = emailVerified;
	}

	public String getEmailToken() {
	    return emailToken;
	}

	public void setEmailToken(String emailToken) {
	    this.emailToken = emailToken;
	}

	public LocalDateTime getTokenCreatedAt() {
	    return tokenCreatedAt;
	}

	public void setTokenCreatedAt(LocalDateTime tokenCreatedAt) {
	    this.tokenCreatedAt = tokenCreatedAt;
	}



	@Override
	public String toString() {
		return "Member [memberId=" + member_id + ", userId=" + userName + ", pw=" + pw + ", name=" + name + ", email="
				+ email + ", phone=" + phone + ", addr=" + addr + ", isExpert=" + expert + ", createdAt=" + createdAt
				+ "]";
	}


	
    
    
}
