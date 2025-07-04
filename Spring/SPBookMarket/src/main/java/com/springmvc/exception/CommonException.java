package com.springmvc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// 애플리케이션 전역에서 발생하는 예외를 처리할 수 있는 클래스임을 선언
@ControllerAdvice
public class CommonException {
	
	// RuntimeException이 발생했을 때 실행되는 예외 처리 메서드
	@ExceptionHandler(RuntimeException.class)
	private ModelAndView handleErrorCommon(Exception e) {
		ModelAndView modelAndView = new ModelAndView();
		
		// 예외 객체를 모델에 저장하여 뷰에 전달
		modelAndView.addObject("exception", e);
		
		// 에러 페이지로 이동할 뷰 이름 설정
		modelAndView.setViewName("errorCommon");
		
		return modelAndView;
	}
}
