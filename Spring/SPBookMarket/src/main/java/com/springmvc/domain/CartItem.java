package com.springmvc.domain;

import java.io.Serializable; // 직렬화를 위한 import (세션 저장 등)

public class CartItem implements Serializable { // 장바구니 항목 클래스
	private static final long seriaVersionUID = 3636831123198290235L;

	private Book book;            // 📖 장바구니에 담긴 도서 객체
	private int quantity;         // 🔢 담긴 수량
	private int totalPrice;       // 💰 해당 도서의 총 가격 (unitPrice * quantity)

	// 기본 생성자
	public CartItem() {
	}

	// 도서를 전달받아 수량 1, 총 금액은 도서 가격으로 설정하는 생성자
	public CartItem(Book book) {
		super();
		this.book = book;
		this.quantity = 1;
		this.totalPrice = book.getUnitPrice();
	}

	// 도서 객체 getter/setter
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}

	// 수량 getter/setter
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// 총 가격 getter/setter
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	// 📌 수량과 단가에 따라 총 가격을 다시 계산해주는 메서드
	public void updateTotalPrice() {
		totalPrice = this.book.getUnitPrice() * this.quantity;
	}

	// equals() & hashCode() – 도서(Book) 기준으로 객체 비교
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { // 자기 자신과 비교면 같음
			return true;
		}
		if (obj == null) { // null이면 다름
			return false;
		}
		if (getClass() != obj.getClass()) { // 타입 다르면 다름
			return false;
		}

	    CartItem other = (CartItem) obj;

		// 도서가 같으면 같은 CartItem으로 판단
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;

		return true;
	}
}
