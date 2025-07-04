package com.springmvc.service;  // 이 클래스가 속한 패키지 경로

import org.springframework.beans.factory.annotation.Autowired;  // 의존성 주입 어노테이션
import org.springframework.stereotype.Service;  // 이 클래스가 서비스 역할임을 명시

import com.springmvc.domain.Book;    // 도서 도메인 클래스
import com.springmvc.domain.Order;   // 주문 도메인 클래스
import com.springmvc.repository.BookRepository;     // 도서 관련 DB 작업 처리
import com.springmvc.repository.OrderRepository;    // 주문 관련 DB 작업 처리

@Service  // 이 클래스가 서비스 컴포넌트임을 스프링에게 알림 (빈으로 등록됨)
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BookRepository bookRepository;  // 도서 정보 조회 및 재고 업데이트용

	@Autowired
	private OrderRepository orderRepository;  // 주문 저장용

	@Autowired
	private CartService cartService;  // 주문 후 장바구니 삭제를 위한 서비스

	@Override
	public void confirmOrder(String bookId, long quantity) {
		// 주문 확정 시 재고 확인 및 차감 처리

		Book bookById = bookRepository.getBookById(bookId);  
		// 도서 ID로 도서 정보 조회

		if(bookById.getUnitsInStock() < quantity) {
			// 재고가 부족할 경우 예외 발생
			throw new IllegalArgumentException("품절입니다. 사용가능한 재고수 :" + bookById.getUnitsInStock());
		}

		// 재고 수량에서 주문 수량만큼 차감
		bookById.setUnitsInStock(bookById.getUnitsInStock() - quantity);
		// ★ 이 부분은 메모리에만 반영됨. DB에 저장하려면 별도로 update 호출 필요함.
	}

	@Override
	public Long saveOrder(Order order) {
		// 주문 정보를 저장하고, 관련 장바구니를 삭제하는 메서드

		Long orderId = orderRepository.saveOrder(order);  
		// 주문 저장 후 생성된 주문 ID 반환

		cartService.delete(order.getCart().getCartid());  
		// 주문 완료 후 장바구니 비우기 (삭제)

		return orderId;  // 최종 주문 ID 반환
	}
}
