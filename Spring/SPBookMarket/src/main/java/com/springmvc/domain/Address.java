package com.springmvc.domain; // 도메인 패키지: 데이터 모델 클래스들이 위치

import java.io.Serializable; // 객체를 직렬화할 수 있도록 해주는 인터페이스

public class Address implements Serializable { // 주소 정보를 담는 클래스, 직렬화 가능해야 세션/파일 저장 등에 쓸 수 있어
	private static final long seriaVersionUID = 613846598817670033L; // 직렬화 시 클래스 버전 일치용 ID (타입오타: seria → serial 권장)

	private String detailName; // 세부주소 (예: 아파트, 동호수 등)
	private String addressName; // 도로명 주소 또는 지번 주소
	private String country; // 국가명 (예: 대한민국)
	private String zipCode; // 우편번호
	
	public String getDetailName() { // 세부주소 반환
		return detailName;
	}
	
	public void setDetailName(String detailName) { // 세부주소 설정
		this.detailName = detailName;
	}
	
	public String getAddressName() { // 주소 반환
		return addressName;
	}
	
	public void setAddressName(String addressName) { // 주소 설정
		this.addressName = addressName;
	}
	
	public String getCountry() { // 국가명 반환
		return country;
	}
	
	public void setCountry(String country) { // 국가명 설정
		this.country = country;
	}
	
	public String getZipCode() { // 우편번호 반환
		return zipCode;
	}
	
	public void setZipCode(String zipCode) { // 우편번호 설정
		this.zipCode = zipCode;
	}

	@Override
	public int hashCode() { // 객체를 Hash 기반 자료구조(Set, Map)에 넣을 때 고유 식별자로 사용됨
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressName == null) ? 0 : addressName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((detailName == null) ? 0 : detailName.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) { // 두 객체가 같은지 비교할 때 사용하는 메서드 (equals 비교 기준 직접 정의)
		if(this == obj) // 주소값이 같으면 true
			return true;
		if(obj == null) // null이면 false
			return false;
		if(getClass() != obj.getClass()) // 클래스 타입 다르면 false
			return false;

		Address other = (Address) obj; // 타입 캐스팅 후 값 비교

		if(addressName == null) {
			if(other.addressName != null)
				return false;
		}else if (!addressName.equals(other.addressName))
			return false;

		if(country == null) {
			if(other.country != null)
				return false;
		}else if(!country.equals(other.country))
			return false;

		if(detailName == null) {
			if(other.detailName != null)
				return false;
		}else if(!detailName.equals(other.detailName))
			return false;

		if(zipCode == null) {
			if(other.zipCode != null)
				return false;
		}else if(!zipCode.equals(other.zipCode))
			return false;

		return true; // 모든 조건이 같으면 true
	}
}
