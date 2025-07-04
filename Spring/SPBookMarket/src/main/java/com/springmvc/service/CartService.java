package com.springmvc.service;  // 이 인터페이스가 속한 패키지 경로

import com.springmvc.domain.Cart;  // 장바구니 정보를 담는 Cart 클래스 import

// CartService는 장바구니 관련 서비스 기능들을 정의한 인터페이스 (설계서 역할)
public interface CartService {
	
	Cart create(Cart cart);
	// 장바구니를 새로 생성하는 메서드
	// 파라미터: Cart 객체 (장바구니 전체 정보)
	// 반환값: 생성된 장바구니 (Cart)

	Cart read(String cartId);
	// 장바구니 ID로 기존 장바구니를 조회하는 메서드
	// 반환값: 해당 ID의 Cart 객체

	Cart validateCart(String cartId);
	// 장바구니가 유효한지(존재하는지) 검증하는 메서드
	// 존재하면 Cart 반환, 없으면 예외 던지거나 null 등 처리

	void update(String cartId, Cart cart);
	// 장바구니 ID로 기존 장바구니 내용을 새로운 Cart 객체로 수정

	void delete(String cartId);
	// 장바구니 ID를 통해 해당 장바구니를 삭제
}
