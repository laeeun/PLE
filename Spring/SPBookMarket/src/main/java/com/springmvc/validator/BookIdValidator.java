package com.springmvc.validator;  // 이 클래스가 속한 패키지 경로

import javax.validation.ConstraintValidator;  // 커스텀 제약 조건 Validator를 만들기 위한 인터페이스
import javax.validation.ConstraintValidatorContext;  // 유효성 검사시 필요한 컨텍스트 정보

import org.springframework.beans.factory.annotation.Autowired;  // 의존성 주입을 위한 어노테이션

import com.springmvc.domain.Book;         // Book 객체 (도서 정보)
import com.springmvc.exception.BookIdException;  // 도서 ID가 없을 때 발생시키는 사용자 정의 예외
import com.springmvc.service.BookService; // 도서 정보를 가져오기 위한 서비스

// ConstraintValidator<어노테이션 타입, 검사할 값의 타입>
// → 여기서는 @BookId 어노테이션이 붙은 String 값을 검사함
public class BookIdValidator implements ConstraintValidator<BookId, String> {

	@Autowired
	private BookService bookService;  
	// bookService를 주입 받아서 DB에 해당 bookId가 있는지 확인할 때 사용

	@Override
	public void initialize(BookId constraintAnnotation) {
		// @BookId 어노테이션 초기화 메서드
		// 지금은 따로 초기화할 내용이 없어서 비워둠
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 유효성 검사 로직을 정의하는 메서드
		// value는 검사 대상 값 (여기선 입력된 bookId)

		Book book;
		try {
			book = bookService.getBookById(value);  
			// bookId로 Book 객체를 찾아옴 (존재하면 book 객체 반환)
		} catch (BookIdException e) {
			// bookService.getBookById()에서 해당 ID가 없으면 예외 발생
			// 예외가 발생했다는 건, 해당 bookId는 존재하지 않음 → 유효하다고 판단
			return true;
		}

		if (book != null) {
			// 이미 동일한 bookId의 책이 존재한다면 → 중복 → 유효하지 않음
			return false;
		}

		return true;  // 그 외의 경우는 유효한 값으로 인정
	}
}
