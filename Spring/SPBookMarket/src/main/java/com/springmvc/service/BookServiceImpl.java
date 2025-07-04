package com.springmvc.service;  // 이 클래스가 속한 패키지 경로

import com.springmvc.domain.Book;  // 도서 정보 객체 Book import
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;  // 의존성 주입을 위한 어노테이션
import org.springframework.stereotype.Service;  // 이 클래스가 서비스임을 명시하는 어노테이션
import com.springmvc.repository.BookRepository;  // 실제 DB 작업을 수행하는 Repository 인터페이스 import

@Service  // 이 클래스가 "서비스 계층"임을 스프링에 알림
public class BookServiceImpl implements BookService {

	@Autowired  // 스프링이 BookRepository 객체를 자동으로 주입해줌 (DI)
	private BookRepository bookRepository;  // 도서 정보 저장소 역할

	@Override
	public List<Book> getAllBookList() {
		// 전체 도서 목록을 가져오는 메서드 (Repository에 위임)
		return bookRepository.getAllBookList();
	}

	@Override
	public List<Book> getBookListByCategory(String category) {
		// 특정 카테고리의 도서 목록을 가져오는 메서드
		System.out.println("📡 [Service] getAllBookList 호출됨!");  // 로그 출력
		List<Book> booksByCategory = bookRepository.getBookListByCategory(category);  // Repository 호출
		return booksByCategory;  // 결과 반환
	}

	@Override
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		// 출판사/카테고리 필터로 도서 검색
		Set<Book> booksByFilter = bookRepository.getBookListByFilter(filter);  // Repository 호출
		return booksByFilter;
	}

	@Override
	public Book getBookById(String bookId) {
		// 도서 ID로 특정 도서 정보 가져오기
		Book bookById = bookRepository.getBookById(bookId);  // Repository 호출
		return bookById;
	}

	@Override
	public void setNewBook(Book book) {
		// 새로운 도서 추가
		bookRepository.setNewBook(book);  // Repository 호출
	}

	@Override
	public void setUpdateBook(Book book) {
		// 기존 도서 정보 수정
		bookRepository.setUpdateBook(book);  // Repository 호출
	}

	@Override
	public void setDelete(String bookID) {
		// 도서 삭제
		bookRepository.setDeleteBook(bookID);  // Repository 호출
	}
}
