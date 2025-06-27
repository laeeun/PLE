package com.springmvc.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Shipping implements Serializable{
	private static final long seriaVersionUID = 8121814661110003493L;
	
	private String name; //배송고객 이름
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date date; //배송일
	private Address address; //배송주소 객체
	
	public Shipping() {
		this.address = new Address();
	}

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
