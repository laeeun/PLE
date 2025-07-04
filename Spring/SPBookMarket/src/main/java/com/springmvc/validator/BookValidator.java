package com.springmvc.validator;  // 이 클래스가 속한 패키지 경로

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;  // 제약 조건 위반 시 사용하는 객체

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;    // 검증 중 발생한 에러 정보를 담는 객체
import org.springframework.validation.Validator;  // 스프링의 유효성 검사 인터페이스

import com.springmvc.domain.Book;  // 도서 객체

// BookValidator 클래스는 스프링 Validator 인터페이스를 구현함
// => Book 객체에 대해 유효성 검사를 직접 정의함
public class BookValidator implements Validator {

	@Autowired
	private javax.validation.Validator beanValidator;  
	// JSR-380(Bean Validation) 기반의 표준 Validator를 주입받음 (예: @NotNull, @Size 등)

	private Set<Validator> springValidators;  
	// 다른 스프링 Validator들을 담는 집합 (커스텀 유효성 검사기들을 여기에 추가 가능)

	public BookValidator() {
		springValidators = new HashSet<Validator>();  
		// 초기화: 비어있는 Validator 집합 생성
	}

	public Set<Validator> getSpringValidators() {
		// 외부에서 사용할 수 있도록 getter 제공
		return springValidators;
	}

	public void setSpringValidators(Set<Validator> springValidators) {
		// 외부에서 커스텀 Validator들을 추가할 수 있도록 setter 제공
		this.springValidators = springValidators;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// 이 Validator가 어떤 클래스 타입을 검사할 수 있는지 정의
		// 여기서는 Book 클래스 또는 그 자식 클래스에 대해 검사 가능함
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// 실제 유효성 검사를 수행하는 메서드
		System.out.println("BookValidator validate 함수 입장");  // 디버깅용 로그 출력

		// 1단계: Bean Validation(@BookId, @NotNull 등) 검증 수행
		Set<ConstraintViolation<Object>> violations = beanValidator.validate(target);

		for (ConstraintViolation<Object> violation : violations) {
			// 검증 위반 항목들을 하나씩 꺼내서

			String propertyPath = violation.getPropertyPath().toString();  // 어떤 필드에서 에러났는지
			String message = violation.getMessage();  // 그 필드의 에러 메시지

			errors.rejectValue(propertyPath, "", message);  
			// 스프링의 Errors 객체에 오류 정보 등록 (화면에서 에러 메시지 출력 가능)
		}

		// 2단계: 추가적인 스프링 Validator들도 순차적으로 호출
		for (Validator validator : springValidators) {
			validator.validate(target, errors);  
			// 각각의 커스텀 Validator가 추가적인 검사 수행
		}
	}
}
