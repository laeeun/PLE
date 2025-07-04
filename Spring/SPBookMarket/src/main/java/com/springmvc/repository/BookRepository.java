package com.springmvc.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.springmvc.domain.Book;

// Book 관련 데이터 접근을 정의하는 인터페이스 (DAO 역할)
public interface BookRepository {
	
	// 모든 도서 목록을 반환하는 메서드
	List<Book> getAllBookList();
	
	// 특정 카테고리에 해당하는 도서 목록을 반환하는 메서드
	List<Book> getBookListByCategory(String category);
	
	// 필터 조건에 따라 도서를 조회하여 Set으로 반환하는 메서드 (중복 제거 목적)
	Set<Book> getBookListByFilter(Map<String, List<String>> filter);
	
	// 도서 ID로 해당 도서 정보를 반환하는 메서드
	Book getBookById(String bookId);
	
	// 새 도서를 저장(등록)하는 메서드
	void setNewBook(Book book);
	
	// 기존 도서 정보를 수정(갱신)하는 메서드
	void setUpdateBook(Book book);
	
	// 도서 ID로 도서를 삭제하는 메서드
	void setDeleteBook(String bookID);
}
