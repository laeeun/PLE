package com.springmvc.repository;  // 이 인터페이스가 속한 패키지 경로

import com.springmvc.domain.Order;  // 주문 정보를 담는 Order 클래스 import

// OrderRepository는 "주문" 데이터를 처리하기 위한 기능 틀(설계도) 역할을 하는 인터페이스
public interface OrderRepository {

	Long saveOrder(Order order);  
	// 주문 정보를 저장하는 메서드
	// 파라미터: Order 객체 (주문 정보 전체를 담고 있음)
	// 반환값: Long 타입 → 저장된 주문의 고유 ID 또는 주문 번호 등을 의미함
}
