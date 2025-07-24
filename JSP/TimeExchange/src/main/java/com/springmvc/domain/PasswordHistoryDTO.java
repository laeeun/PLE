package com.springmvc.domain;

import java.time.LocalDateTime;

public class PasswordHistoryDTO {

    private int id;
    private String member_id;
    private String password_hash;
    private LocalDateTime changed_at;
    private String encodedPw;
    

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getPassword_hash() {
		return password_hash;
	}
	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	public LocalDateTime getChanged_at() {
		return changed_at;
	}
	public void setChanged_at(LocalDateTime changed_at) {
		this.changed_at = changed_at;
	}
	public String getEncodedPw() {
		return encodedPw;
	}
	public void setEncodedPw(String encodedPw) {
		this.encodedPw = encodedPw;
	}
	@Override
	public String toString() {
		return "PasswordHistoryDTO [id=" + id + ", member_id=" + member_id + ", password_hash=" + password_hash
				+ ", changed_at=" + changed_at + ", encodedPw=" + encodedPw + "]";
	}
	
	

    
}
