package com.springmvc.repository;  // 이 클래스가 속한 패키지 경로 정의

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;  // 스프링에서 데이터 저장소 역할임을 알려주는 어노테이션

import com.springmvc.domain.Order;  // 주문 정보를 담는 Order 클래스 import

@Repository  // 이 클래스가 Repository(데이터 저장소) 역할임을 스프링에게 알려줌
public class OrderRepositoryImpl implements OrderRepository {

	private Map<Long, Order> listOfOrders;  
	// 주문을 저장하는 Map 자료구조 (key: 주문 ID, value: 주문 객체)
	// 실무 DB 대신 메모리에서 임시로 저장하는 구조

	private long nextOrderId;  
	// 다음 주문에 사용할 고유 ID (매번 1씩 증가)

	public OrderRepositoryImpl() {
		listOfOrders = new HashMap<Long, Order>();  
		// 주문 저장소 초기화

		nextOrderId = 2000;  
		// 주문 번호는 2000부터 시작하도록 설정
	}

	@Override
	public Long saveOrder(Order order) {
		// 주문을 저장하는 메서드

		order.setOrderId(getNextOrderId());  
		// 주문 객체에 고유한 ID 부여

		listOfOrders.put(order.getOrderId(), order);  
		// Map에 주문 저장 (key = 주문 ID, value = 주문 객체)

		return order.getOrderId();  
		// 저장한 주문 ID를 반환 (보통 주문 완료 화면에 보여줌)
	}

	private synchronized long getNextOrderId() {
		// 고유 주문 ID를 생성하는 메서드
		// synchronized: 여러 사용자가 동시에 접근해도 ID가 겹치지 않도록 보장

		return nextOrderId++;  
		// 현재 ID를 반환하고 1 증가시킴
	}
}
