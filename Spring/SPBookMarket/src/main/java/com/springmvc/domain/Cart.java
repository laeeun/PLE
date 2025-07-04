package com.springmvc.domain;

import java.io.Serializable; // 직렬화 가능하게 만듦 (세션에 저장하기 위해)
import java.util.HashMap;     // 장바구니 내부 도서 목록을 저장할 Map 사용
import java.util.Map;

public class Cart implements Serializable { // 장바구니 클래스 선언
	private static final long seriaVersionUID = 2155125089108199199L; // 직렬화 버전 UID
	
	private String cartId; // 🛒 장바구니 ID (보통 세션 ID와 동일하게 사용됨)
	private Map<String, CartItem> cartItems; // 📚 장바구니에 담긴 항목들 (도서ID → CartItem)
	private int grandTotal; // 💰 장바구니 전체 금액

	// 생성자: 장바구니 ID만 받아서 새 HashMap으로 초기화
	public Cart(String cartId) {
		this.cartId = cartId;
		this.cartItems = new HashMap<>();
	}

	// getter/setter
	public String getCartid() {
		return cartId;
	}
	public void setCartid(String cartid) {
		this.cartId = cartid;
	}
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public int getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}

	// 🧮 총 금액 업데이트 (CartItem들을 돌면서 합계 계산)
	public void updateGrandTotal() {
		grandTotal = 0;
		for(CartItem item : cartItems.values()) {
			grandTotal = grandTotal + item.getTotalPrice();
		}
	}

	// 🧾 equals(), hashCode() 오버라이딩 – 장바구니 ID 기준으로 객체 비교
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if(cartId == null) {
			if(other.cartId != null)
				return false;
		}else if(!cartId.equals(other.cartId))
			return false;
		return true;
	}

	// ➕ 장바구니에 항목 추가하는 메서드
	public void addCartItem(CartItem item) {
		System.out.println("addCart item함수 입장");

		String bookId = item.getBook().getBookId(); // 추가하려는 도서의 ID 얻기

		if(cartItems.containsKey(bookId)) { // 이미 장바구니에 있으면
			CartItem cartItem = cartItems.get(bookId); // 기존 CartItem 가져오기
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity()); // 수량 추가
			cartItems.put(bookId, cartItem); // 다시 저장
		}else {
			cartItems.put(bookId, item); // 처음 넣는 경우엔 그냥 저장
		}
		updateGrandTotal(); // 총금액 다시 계산
	}

	// ➖ 장바구니에서 항목 삭제하는 메서드
	public void removeCartItem(CartItem item) {
		System.out.println("removeCartItem함수 들어왔다 !!!!!");

		String bookId = item.getBook().getBookId(); // 도서 ID 얻기
		System.out.println(bookId);

		for (String key : cartItems.keySet()) {
			System.out.println("key = " + key + ", value = " + cartItems.get(key));
		}

		cartItems.remove(bookId); // Map에서 해당 항목 제거
		System.out.println("삭제전");

		for (String key : cartItems.keySet()) {
			System.out.println("key = " + key + ", value = " + cartItems.get(key));
		}
		System.out.println("도서 아이디 찾아서 삭제했다");

		updateGrandTotal(); // 총 금액 갱신
		System.out.println("삭제하고 갱신했다 !!");
	}

	// 디버깅용 toString – 장바구니 정보 전체 출력
	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", cartItems=" + cartItems + ", grandTotal=" + grandTotal + "]";
	}
}
