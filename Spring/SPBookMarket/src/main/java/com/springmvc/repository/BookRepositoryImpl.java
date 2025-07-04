package com.springmvc.repository;  // 이 파일이 속한 패키지 이름 정의

// 자바에서 제공하는 컬렉션, 유틸리티 클래스들을 import
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;  // DB와 연결할 때 사용하는 데이터 소스

// 로그를 남기기 위해 SLF4J 로그 라이브러리 사용
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 스프링에서 의존성 주입을 위한 어노테이션
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;  // JDBC를 쉽게 다룰 수 있게 해주는 클래스
import org.springframework.stereotype.Repository;  // 이 클래스가 DAO 역할임을 명시

// 도메인 객체(Book)와 사용자 정의 예외(BookIdException) import
import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;

@Repository  // 이 클래스는 데이터베이스 작업을 처리하는 Repository임을 나타냄
public class BookRepositoryImpl implements BookRepository {

	private JdbcTemplate template;  // DB 작업을 쉽게 처리해주는 Spring의 JdbcTemplate 객체

	@Autowired  // 스프링이 자동으로 DataSource를 주입해줌
	public void setJdbctemplate(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);  // 주입받은 DataSource를 이용해 JdbcTemplate 생성
	}

	public static Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);  // 로그 기록용 Logger 객체 생성

	private List<Book> listOfBooks = new ArrayList<Book>();  // 테스트용 도서 리스트 (메모리에 저장)

	// 생성자: 3권의 도서 정보를 리스트에 추가 (테스트용 초기 데이터)
	public BookRepositoryImpl() {
		Book book1 = new Book("ISBN1234", "C# 교과서", 30000);
		book1.setAuthor("박용준");
		book1.setDescription("C# 초보자를 위한 입문서. 프로그래밍 기초를 배우기 좋음.");
		book1.setPublisher("길벗");
		book1.setCategory("IT전문서");
		book1.setUnitsInStock(1000);  // 재고 수량
		book1.setReleaseDate("2020/05/29");

		Book book2 = new Book("ISBN1235", "Node.js 교과서", 36000);
		book2.setAuthor("조현영");
		book2.setDescription("Node.js 기반의 웹서버 개발을 위한 실전 가이드.");
		book2.setPublisher("길벗");
		book2.setCategory("IT전문서");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2020/07/25");

		Book book3 = new Book("ISBN1236", "어도비 XD CC 2020", 25000);
		book3.setAuthor("김두한");
		book3.setDescription("디자인 툴 XD 사용법과 UI/UX 기초 설명.");
		book3.setPublisher("길벗");
		book3.setCategory("IT활용서");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate("2019/05/29");

		listOfBooks.add(book1);  // 리스트에 책 추가
		listOfBooks.add(book2);
		listOfBooks.add(book3);
	}

	@Override
	public List<Book> getAllBookList() {
		String SQL = "SELECT * FROM book";  // 모든 책을 가져오는 SQL문
		List<Book> listOfBooks = template.query(SQL, new BookRowMapper());  // 결과를 Book 객체로 매핑
		return listOfBooks;  // 리스트 반환
	}

	@Override
	public List<Book> getBookListByCategory(String category) {
		logger.info("getBookListByCategory : " + category);  // 로그 찍기
		List<Book> booksByCategory = new ArrayList<Book>();
		String SQL = "SELECT * FROM book where b_category LIKE '%" + category + "%'";  // 카테고리 포함하는 책 찾기
		booksByCategory = template.query(SQL, new BookRowMapper());  // 쿼리 실행 후 Book 객체로 매핑
		return booksByCategory;
	}

	@Override
	public Set<Book> getBookListByFilter(Map<String, List<String>> filter) {
		logger.info("getBookListByFilter : " + filter);  // 필터값 로그로 출력

		Set<Book> booksByPublisher = new HashSet<Book>();  // 출판사 기준 필터 결과 저장
		Set<Book> booksByCategory = new HashSet<Book>();  // 카테고리 기준 필터 결과 저장
		Set<String> criterias = filter.keySet();  // 필터 키값들 가져오기

		// 출판사 필터가 포함되어 있으면 해당 조건에 맞는 책 검색
		if (criterias.contains("publisher")) {
			for (int j = 0; j < filter.get("publisher").size(); j++) {
				String publisherName = filter.get("publisher").get(j);
				String SQL = "SELECT * FROM book where b_publisher LIKE '%" + publisherName + "%'";
				booksByPublisher.addAll(template.query(SQL, new BookRowMapper()));
			}
		}

		// 카테고리 필터가 포함되어 있으면 해당 조건에 맞는 책 검색
		if (criterias.contains("category")) {
			for (int i = 0; i < filter.get("category").size(); i++) {
				String category = filter.get("category").get(i);
				String SQL = "SELECT * FROM book where b_category LIKE '%" + category + "%'";
				booksByCategory.addAll(template.query(SQL, new BookRowMapper()));
			}
		}

		booksByCategory.retainAll(booksByPublisher);  // 출판사와 카테고리 모두 일치하는 책만 남김 (교집합)
		logger.info("👉 booksByCategory값:" + booksByCategory);  // 결과 로그 출력
		return booksByCategory;
	}

	@Override
	public Book getBookById(String bookId) {
		logger.info("📥 [getBookById] 도서 ID: {}", bookId);  // 요청한 도서 ID 로그로 출력
		Book bookInfo = null;
		String SQL = "SELECT count(*) FROM book where b_bookId=?";  // 해당 ID의 책이 존재하는지 확인
		int rowCount = template.queryForObject(SQL, Integer.class, bookId);  // 존재하는 책 개수 조회

		if (rowCount != 0) {
			SQL = "SELECT * FROM book where b_bookId=?";  // 책이 있으면 상세 정보 가져옴
			bookInfo = template.queryForObject(SQL, new Object[] { bookId }, new BookRowMapper());
		}

		if (bookInfo == null) {
			throw new BookIdException(bookId);  // 책이 없으면 예외 발생
		}

		return bookInfo;  // 책 정보 반환
	}

	@Override
	public void setNewBook(Book book) {
		logger.info("📤 [setNewBook] 신규 도서 등록: {}", book);  // 로그 출력
		String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName)" +
		             "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  // 책 등록용 SQL

		template.update(SQL, 
			book.getBookId(), book.getName(), book.getUnitPrice(), 
			book.getAuthor(), book.getDescription(), book.getPublisher(), 
			book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), 
			book.getCondition(), book.getFileName());  // DB에 값 넣기
	}

	@Override
	public void setUpdateBook(Book book) {
		if(book.getFileName() != null) {
			// 파일명이 있을 경우 책 정보 업데이트
			String SQL = "UPDATE Book SET b_name = ?, b_unitPrice = ?, b_author = ?, b_description = ?, b_publisher = ?, b_category = ?, b_unitsInStock = ?, b_releaseDate = ?, b_condition = ?, b_fileName = ? where b_bookId = ? ";
			template.update(SQL, book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getCondition(), book.getFileName(), book.getBookId());
		}else if (book.getFileName() == null) {
			// 파일명이 없으면 INSERT 수행 (※ 이 부분은 UPDATE가 아닌 INSERT라 로직이 이상할 수 있음. 구조 재검토 필요)
			String SQL = "INSERT INTO book (b_bookId, b_name, b_unitPrice, b_author, b_description, b_publisher, b_category, b_unitsInStock, b_releaseDate, b_condition, b_fileName)" +
			             "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			template.update(SQL, book.getBookId(), book.getName(), book.getUnitPrice(), book.getAuthor(), book.getDescription(), book.getPublisher(), book.getCategory(), book.getUnitsInStock(), book.getReleaseDate(), book.getCondition(), book.getFileName());
		}
	}

	@Override
	public void setDeleteBook(String bookID) {
		String SQL = "DELETE from Book where b_bookId = ? ";  // 해당 도서를 삭제하는 SQL
		this.template.update(SQL, bookID);  // 실행
	}
}
