package com.springmvc.exception;

// BookId 관련 예외 상황에서 사용하는 사용자 정의 예외 클래스
@SuppressWarnings("serial") // 직렬화 경고 무시
public class BookIdException extends RuntimeException { // RuntimeException을 상속받은 예외 클래스

	private String bookId; // 문제가 발생한 도서 ID를 저장하는 필드

	public BookIdException(String bookId) { // 도서 ID를 받아서 예외 객체 생성하는 생성자
		this.bookId = bookId;
	}

	public String getBookId() { // 저장된 도서 ID 값을 꺼내는 getter 메서드
		return bookId;
	}
}
