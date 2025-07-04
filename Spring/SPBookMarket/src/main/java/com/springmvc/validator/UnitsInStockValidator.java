package com.springmvc.validator;  // 이 클래스가 속한 패키지 경로

import org.springframework.stereotype.Component;  // 스프링 컴포넌트로 등록하기 위한 어노테이션
import org.springframework.validation.Errors;     // 검증 중 에러 정보를 담는 객체
import org.springframework.validation.Validator;  // 스프링 Validator 인터페이스

import com.springmvc.domain.Book;  // 검증 대상이 되는 Book 객체

@Component  // 이 클래스를 스프링 빈으로 등록함 (자동 감지됨)
public class UnitsInStockValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// 이 Validator가 Book 객체에 대해 유효성 검사를 지원하는지 여부 반환
		// isAssignableFrom: 해당 클래스 또는 그 자식 클래스까지 포함함
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// 유효성 검사 수행 메서드
		// target: 검증 대상 객체, errors: 오류 정보를 담는 객체

		Book book = (Book) target;  // 검증 대상 객체를 Book 타입으로 캐스팅

		// 만약 책의 가격이 10,000원 이상이고, 재고 수량이 99개 초과면 오류 발생
		if (book.getUnitPrice() >= 10000 && book.getUnitsInStock() > 99) {
			// 'unitsInStock' 필드에 오류 메시지를 등록
			// 메시지 키: UnitsInStockValidator.message (→ messages.properties에서 가져옴)
			errors.rejectValue("unitsInStock", "UnitsInStockValidator.message");
		}
	}
}
