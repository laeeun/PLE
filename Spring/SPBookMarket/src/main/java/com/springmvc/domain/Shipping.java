package com.springmvc.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Shipping implements Serializable { // 배송 정보 도메인 객체 (직렬화 대상)
	private static final long seriaVersionUID = 8121814661110003493L;

	private String name; //  받는 사람 이름
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date date;   //  배송 날짜 (yyyy/MM/dd 형식으로 포맷)
	private Address address; // 배송 주소 (Address 객체로 구성)

	public Shipping() {
		this.address = new Address(); // 주소 객체는 생성 시 같이 초기화
	}

	// Getter / Setter 영역
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
