package com.springmvc.service;  // 이 인터페이스가 속한 패키지 경로

import com.springmvc.domain.Order;  // 주문 정보를 담는 Order 클래스 import

// OrderService는 주문 관련 서비스 기능을 정의한 인터페이스
// 실제 구현은 OrderServiceImpl에서 이루어짐
public interface OrderService {

	void confirmOrder(String bookId, long quantity);
	// 도서를 주문 확정하는 기능
	// bookId: 주문할 책의 ID
	// quantity: 주문 수량
	// 이 메서드는 주로 주문 전 재고 확인이나 검증 로직에 쓰임

	Long saveOrder(Order order);
	// 실제 주문 데이터를 저장하는 기능
	// 파라미터: Order 객체 (배송지, 주문 도서, 수량, 날짜 등 포함)
	// 반환값: 주문 번호 (고유한 Long 타입 ID)
}
