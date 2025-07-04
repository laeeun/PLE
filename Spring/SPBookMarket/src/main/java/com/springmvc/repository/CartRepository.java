package com.springmvc.repository;  // 이 인터페이스가 속한 패키지 위치 정의

import com.springmvc.domain.Cart;  // Cart 클래스(import) - 장바구니 정보를 담고 있는 객체

// CartRepository 인터페이스는 장바구니 관련 작업을 정의해 놓은 "기능 설계도"라고 보면 됨
public interface CartRepository {

	Cart create(Cart cart);  
	// 장바구니 생성 메서드
	// 파라미터로 Cart 객체를 받아서 새 장바구니를 저장하고, 저장된 Cart를 반환

	Cart read(String cartId);  
	// 장바구니 조회 메서드
	// cartId(장바구니 고유 ID)를 받아 해당하는 장바구니 정보를 찾아서 반환

	void update(String cartId, Cart cart);  
	// 장바구니 수정 메서드
	// cartId로 기존 장바구니를 찾아서, 새로 받은 Cart 객체로 내용을 업데이트함

	void delete(String cartId);  
	// 장바구니 삭제 메서드
	// cartId를 기반으로 해당 장바구니를 삭제함
}
