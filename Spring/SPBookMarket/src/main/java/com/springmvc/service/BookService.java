package com.springmvc.service;  // 이 인터페이스가 속한 패키지 경로

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.springmvc.domain.Book;  // 도서 정보를 담는 Book 클래스 import
import org.springframework.stereotype.Service;  // 이 인터페이스가 서비스 역할임을 명시하는 어노테이션
import com.springmvc.repository.BookRepository;  // 연결될 BookRepository 인터페이스 import

@Service  // 이 인터페이스가 "서비스 계층"임을 나타내는 스프링 어노테이션 (사실 구현 클래스에 붙이는 게 일반적이긴 함)
public interface BookService {
	
	List<Book> getAllBookList();
	// 전체 도서 목록을 가져오는 메서드
	// 반환형은 Book 객체 리스트

	List<Book> getBookListByCategory(String category);
	// 카테고리 기준으로 도서를 검색하는 메서드
	// 예: "IT전문서"만 조회

	Set<Book> getBookListByFilter(Map<String, List<String>> filter);
	// 출판사나 카테고리 필터 조건으로 도서를 검색하는 메서드
	// filter: {"publisher": [...], "category": [...]} 형태

	Book getBookById(String bookId);
	// 특정 도서 ID를 이용해 도서 상세정보를 조회하는 메서드

	void setNewBook(Book book);
	// 새로운 도서를 등록하는 메서드 (INSERT)

	void setUpdateBook(Book book);
	// 기존 도서 정보를 수정하는 메서드 (UPDATE)

	void setDelete(String bookID);
	// 도서 ID로 도서를 삭제하는 메서드 (DELETE)
}
