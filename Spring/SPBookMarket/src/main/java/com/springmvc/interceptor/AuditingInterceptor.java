package com.springmvc.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 요청 전후에 공통 작업을 수행하기 위한 인터셉터 클래스
public class AuditingInterceptor extends HandlerInterceptorAdapter {
	public Logger logger = LoggerFactory.getLogger(this.getClass()); // 로그 출력 도구

	private String user; // 요청 사용자
	private String bookId; // 요청된 도서 ID

	// 컨트롤러 실행 전에 호출되는 메서드 (전처리)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle 함수 입장");

		// "/books/add" 경로로 POST 요청이 들어온 경우에만 처리
		if (request.getRequestURI().endsWith("books/add") && request.getMethod().equals("POST")) {
			user = request.getRemoteUser(); // 요청한 사용자 정보
			bookId = request.getParameterValues("bookId")[0]; // 도서 ID 파라미터 값
		}
		return true; // 계속 진행
	}

	// 요청 처리 완료 후 실행되는 메서드 (후처리)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("after 함수 입장");

		// "/books/add" 요청이 끝난 뒤 로그 남김
		if (request.getRequestURI().endsWith("books/add")) {
			logger.warn(String.format("신규등록 도서 ID : %s, 접근자 : %s, 접근시각 : %s", bookId, user, getCurrentTime()));
		}
	}

	// 현재 시간을 yyyy/MM/dd HH:mm:ss 포맷으로 반환하는 메서드
	private String getCurrentTime() {
		System.out.println("getCurrentTime 함수 입장");
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
