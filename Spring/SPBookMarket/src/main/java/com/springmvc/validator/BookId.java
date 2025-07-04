package com.springmvc.validator;  // 이 클래스가 속한 패키지 경로

// 자바 어노테이션 관련 기능 import
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;  // 유효성 검사 제약 조건을 정의하기 위한 어노테이션 import

@Constraint(validatedBy = BookIdValidator.class)
// 이 어노테이션이 어떤 검증 로직으로 작동할지 지정함
// BookIdValidator 클래스가 실제 검증 로직을 담당함

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
// 이 어노테이션을 어디에 붙일 수 있는지 정의함
// → 메서드, 필드, 다른 어노테이션에 붙일 수 있음

@Retention(RetentionPolicy.RUNTIME)
// 실행 시에도 이 어노테이션 정보가 유지되도록 함 (실행 중 반영 가능)

@Documented
// JavaDoc 같은 문서에 이 어노테이션이 포함되도록 함

public @interface BookId {
	// 이 부분은 어노테이션의 속성을 정의하는 곳

	String message() default "{BookId.NewBook.bookId}";
	// 유효성 검사 실패 시 보여줄 기본 메시지
	// messages.properties 파일과 연결되어 다국어 메시지 처리 가능

	Class<?>[] groups() default {};
	// 유효성 검사를 그룹별로 나눌 때 사용 (기본은 빈 배열)

	Class<?>[] payload() default {};
	// 추가 정보를 전달할 때 사용하는 확장용 속성 (보통은 사용하지 않음)
}
