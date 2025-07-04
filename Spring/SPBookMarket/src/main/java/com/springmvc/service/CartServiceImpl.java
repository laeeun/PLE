package com.springmvc.service;  // 이 클래스가 속한 패키지 경로

import org.springframework.beans.factory.annotation.Autowired;  // 스프링이 의존성 자동 주입할 때 사용하는 어노테이션
import org.springframework.stereotype.Service;  // 이 클래스가 서비스 계층임을 알려주는 어노테이션

import com.springmvc.domain.Cart;  // 장바구니 객체
import com.springmvc.exception.CartException;  // 장바구니 관련 예외 처리 클래스
import com.springmvc.repository.CartRepository;  // 실제 DB 또는 메모리에서 장바구니를 다루는 저장소

@Service  // 이 클래스가 서비스 역할임을 스프링에게 알림 (빈으로 등록됨)
public class CartServiceImpl implements CartService {

	@Autowired  // CartRepository를 자동으로 주입받음 (스프링이 객체 생성해서 넣어줌)
	private CartRepository cartRepository;

	@Override
	public Cart create(Cart cart) {
		// 새로운 장바구니 생성
		return cartRepository.create(cart);  // Repository를 호출해서 실제 저장 작업 수행
	}

	@Override
	public Cart read(String cartId) {
		// 장바구니 ID로 장바구니 조회
		return cartRepository.read(cartId);  // Repository에서 조회
	}

	@Override
	public void update(String cartId, Cart cart) {
		// 기존 장바구니 수정
		cartRepository.update(cartId, cart);  // Repository에 위임
	}

	@Override
	public void delete(String cartId) {
		// 장바구니 삭제
		cartRepository.delete(cartId);  // Repository에 위임
	}

	@Override
	public Cart validateCart(String cartId) {
	    // 장바구니가 유효한지 검사 (존재하지 않거나 비어있으면 예외 발생)

	    System.out.println("🛒 cartId: " + cartId);  // 로그 출력 (요청 들어온 cartId 확인용)

	    Cart cart = cartRepository.read(cartId);  // cartId로 장바구니 조회

	    // 로그 출력: 장바구니가 존재할 경우 아이템 수 출력, 없으면 "null"
	    System.out.println("🧺 장바구니 아이템 수: " + (cart != null ? cart.getCartItems().size() : "null"));

	    if (cart == null || cart.getCartItems().size() == 0) {
	        // 장바구니가 존재하지 않거나, 아이템이 0개일 경우 예외 발생
	        throw new CartException(cartId);
	    }

	    return cart;  // 유효한 장바구니인 경우 반환
	}
}
