package com.springmvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 직렬화 관련 경고 무시
@SuppressWarnings("serial")
// 예외 발생 시 HTTP 404 상태와 함께 지정된 메시지를 클라이언트에 반환
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="요청한 도서 분야를 찾을 수 없습니다.")
public class CategoryException extends RuntimeException {
	// 별도 로직 없이 런타임 예외만 상속받는 빈 클래스
}
