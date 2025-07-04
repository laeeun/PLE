package com.springmvc.domain;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable { // 🛒 주문 정보를 담는 클래스 (세션/DB 저장을 위해 직렬화 필요)
	private static final long seriaVersionUID = 2659461092139119863L;

	private Long orderId;        // 주문 고유 ID (ex. 자동 생성 번호)
	private Cart cart;           // 장바구니 객체 (주문에 포함된 상품들)
	private Customer customer;   // 고객 정보 객체 (주문한 사람)
	private Shipping shipping;   // 배송 정보 객체 (어디로 보낼지)

	public Order() {
		this.customer = new Customer();   // 고객 정보 초기화
		this.shipping = new Shipping();   // 배송 정보 초기화
	}

	// Getter / Setter들
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	// equals() & hashCode(): 주문 ID 기준으로 객체 비교
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if(orderId == null) {
			if(other.orderId != null)
				return false;
		}else if(!orderId.equals(other.orderId))
			return false;
		return true;
	}
}
