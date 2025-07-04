package com.springmvc.repository;  // 이 클래스가 속한 패키지 경로

// JDBC의 쿼리 결과를 다루기 위한 ResultSet과 SQLException 관련 클래스 import
import java.sql.ResultSet;
import java.sql.SQLException;

// Spring JDBC에서 쿼리 결과를 객체로 매핑해주는 인터페이스 RowMapper를 사용하기 위한 import
import org.springframework.jdbc.core.RowMapper;

import com.springmvc.domain.Book;  // 매핑 대상이 되는 Book 클래스 import

// BookRowMapper 클래스는 RowMapper<Book> 인터페이스를 구현함
// => 쿼리 결과(ResultSet)의 각 행(row)을 Book 객체로 변환하는 역할
public class BookRowMapper implements RowMapper<Book> {

	// mapRow 메서드는 JDBC에서 한 행(row)을 읽어와서 Book 객체에 담아주는 메서드
	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		Book book = new Book();  // 새로운 Book 객체 생성

		// 아래의 각 줄은 ResultSet(결과 집합)의 컬럼 값을 가져와 Book 객체의 속성에 하나씩 넣는 작업
		book.setBookId(rs.getString(1));        // 첫 번째 컬럼: 도서 ID
		book.setName(rs.getString(2));          // 두 번째 컬럼: 도서명
		book.setUnitPrice(rs.getInt(3));        // 세 번째 컬럼: 가격
		book.setAuthor(rs.getString(4));        // 네 번째 컬럼: 저자
		book.setDescription(rs.getString(5));   // 다섯 번째 컬럼: 설명
		book.setPublisher(rs.getString(6));     // 여섯 번째 컬럼: 출판사
		book.setCategory(rs.getString(7));      // 일곱 번째 컬럼: 카테고리
		book.setUnitsInStock(rs.getLong(8));    // 여덟 번째 컬럼: 재고 수량
		book.setReleaseDate(rs.getString(9));   // 아홉 번째 컬럼: 출간일
		book.setCondition(rs.getString(10));    // 열 번째 컬럼: 상태 (신상품, 중고 등)
		book.setFileName(rs.getString(11));     // 열한 번째 컬럼: 파일명 (이미지 파일 등)

		return book;  // 매핑이 끝난 Book 객체 반환
	}
}
