package com.springmvc.domain;


import java.util.HashMap;
import java.util.Map;

public class Cart {
	
	private String cartId; //사용자 세션 ID (장바구니 식별자)
	private Map<String, CartItem> cartItems; // 장바구니에 담긴 도서 목록
	private int grandTotal; // 장바구니 전체 금액
	
	
	public Cart(String cartId) {
		this.cartId = cartId;
		this.cartItems = new HashMap<>();
	}

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
	
	public void updateGrandTotal() {
		grandTotal = 0;
		for(CartItem item : cartItems.values()) {
			grandTotal = grandTotal + item.getTotalPrice();
		}
	}
	

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
	
	public void addCartItem(CartItem item) {
		System.out.println("addCart item함수 입장");
		String bookId = item.getBook().getBookId(); // 현재 등록하기 위한 도서  ID 가져오기
		
		//도서 ID가 cartItems 객체에 등록되어 있는지 여부 확인
		if(cartItems.containsKey(bookId)) {
			CartItem cartItem = cartItems.get(bookId); //등록된 도서 ID에 대한 정보 가져오기
			//등록된 도서 ID의 개수 추가 저장
			cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
			cartItems.put(bookId, cartItem); //등록된 도서 ID에 대한 변경 정보(cartItem) 저장
		}else {
			cartItems.put(bookId, item); //도서 ID에 데한 도서정보(ietem) 정보저장
		}
		updateGrandTotal();
	}
	
	public void removeCartItem(CartItem item) {
		System.out.println("removeCartItem함수 들어왔다 !!!!!");
		String bookId = item.getBook().getBookId();	
		System.out.println(bookId);
		
		for (String key : cartItems.keySet()) {
	          System.out.println("key = " + key + ", value = " + cartItems.get(key));
	      }

		
		cartItems.remove(bookId); //bookId 도서 삭제
		System.out.println("삭제전");
		
		for (String key : cartItems.keySet()) {
	          System.out.println("key = " + key + ", value = " + cartItems.get(key));
	      }
		System.out.println("도서 아이디 찾아서 삭제했다");
		updateGrandTotal(); //총액 갱신
		System.out.println("삭제하고 갱신했다 !!");
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", cartItems=" + cartItems + ", grandTotal=" + grandTotal + "]";
	}
	
	
}
