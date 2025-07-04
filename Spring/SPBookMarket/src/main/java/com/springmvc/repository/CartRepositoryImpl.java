package com.springmvc.repository;  // 이 클래스가 속한 패키지 경로

// 장바구니 저장을 위한 Map 사용과 관련된 클래스들 import
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;  // 스프링이 관리하는 Repository 빈임을 표시

import com.springmvc.domain.Cart;  // 장바구니 정보를 담는 Cart 클래스 import

@Repository  // 이 클래스가 Repository 역할(데이터 저장소)임을 스프링에 알려주는 어노테이션
public class CartRepositoryImpl implements CartRepository {

	private Map<String, Cart> listOfCarts;  
	// 장바구니를 메모리에 저장할 Map 선언
	// key: 장바구니 ID (문자열), value: Cart 객체

	public CartRepositoryImpl() {
		listOfCarts = new HashMap<String, Cart>();  
		// 생성자: 장바구니를 저장할 Map을 빈 상태로 초기화
	}

	public Cart create(Cart cart) {
		// 장바구니 생성 메서드

		if (listOfCarts.keySet().contains(cart.getCartid())) {
			// 이미 같은 ID의 장바구니가 존재하면 예외 발생
			throw new IllegalArgumentException(String.format("장바구니를  생성할 수 없습니다. 장바구니 Id(%)가 존재합니다", cart.getCartid()));
		}

		listOfCarts.put(cart.getCartid(), cart);  
		// Map에 새로운 장바구니 추가

		return cart;  // 생성된 장바구니 반환
	}

	public Cart read(String cartId) {
		// 장바구니 조회 메서드
		return listOfCarts.get(cartId);  
		// 주어진 ID로 저장된 장바구니 반환 (없으면 null)
	}

	public void update(String cartId, Cart cart) {
		// 장바구니 정보 수정 메서드

		if (!listOfCarts.keySet().contains(cartId)) {
			// 수정하려는 ID가 존재하지 않으면 예외 발생
			throw new IllegalArgumentException(String.format("장바구니 목록을 갱신할 수 없습니다. 장바구니에 ID(%)가 존재하지 않습니다.", cartId));
		}

		listOfCarts.put(cartId, cart);  
		// Map에 새로운 장바구니 객체로 덮어쓰기
	}

	@Override
	public void delete(String cartId) {
		// 장바구니 삭제 메서드

		if (!listOfCarts.keySet().contains(cartId)) {
			// 삭제하려는 장바구니 ID가 없으면 예외 발생
			throw new IllegalArgumentException(String.format("장바구니 목록을 삭제할 수 없습니다. 장바구니 ID(%)가 존재하지 않습니다.", cartId));
		}

		listOfCarts.remove(cartId);  // 장바구니 Map에서 해당 ID 제거
	}
}
