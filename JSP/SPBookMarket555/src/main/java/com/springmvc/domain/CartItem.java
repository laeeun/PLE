package com.springmvc.domain;

public class CartItem {
	private Book book;
	private int quantity;
	private int totalPrice;
	
	public CartItem() {
		
	}
	public CartItem(Book book) {
		super();
		this.book = book;
		this.quantity = 1;
		this.totalPrice = book.getUnitPrice();
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void updateTotalPrice() {
		totalPrice = this.book.getUnitPrice() * this.quantity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
				return false;
		}
		
	    CartItem other = (CartItem) obj;

			if(book == null) {
				if (book == null) {
					if(other.book != null)
						return false;
				}else if(!book.equals(other.book))
					return false;
			}
		return true;
	}
	
	
	
}
