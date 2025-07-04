package com.springmvc.exception;

// 사용자 정의 예외 클래스. 장바구니 관련 오류 발생 시 사용
public class CartException extends RuntimeException {
	private static final long seriaVersionUID = -5192041563033358491L; // 직렬화 버전 ID
	
	private String cartId; // 예외 발생 시 문제된 장바구니 ID를 저장하는 필드
	
	// 장바구니 ID를 받아서 예외 객체 생성
	public CartException(String cartId) {
		this.cartId = cartId;
	}

	// 장바구니 ID를 반환하는 getter 메서드
	public String getCartId() {
		return cartId;
	}
}
