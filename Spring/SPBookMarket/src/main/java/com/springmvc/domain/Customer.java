package com.springmvc.domain;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable { // 고객 정보 객체 (세션 저장 등을 위해 직렬화)
	private static final long seriaVersionUID = 3636831123198280235L;

	private String customerId; // 🆔 고객 ID
	private String name;       // 👤 고객 이름
	private Address address;   // 🏠 고객 주소 (Address 객체로 관리)
	private String phone;      // 📞 고객 전화번호

	public Customer() {
		this.address = new Address(); // 기본 생성자에서 주소 객체도 생성해둠
	}

	public Customer(String customerId, String name) { // 고객 ID와 이름으로 생성하는 생성자
		this(); // 기본 생성자 호출해서 address 초기화
		this.customerId = customerId;
		this.name = name;
	}

	// Getter / Setter 들
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	// equals() & hashCode()는 customerId로 비교하기 위해 오버라이드 함
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode()); // ID 기준 해시코드
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) // 자기 자신이면 true
			return true;
		if (obj == null) // null은 false
			return false;
		if (getClass() != obj.getClass()) // 타입 다르면 false
			return false;
		Customer other = (Customer) obj;
		if(customerId == null) {
			if(other.customerId != null)
				return false;
		}else if(!customerId.equals(other.customerId)) // ID가 다르면 false
			return false;
		return true;
	}
}
