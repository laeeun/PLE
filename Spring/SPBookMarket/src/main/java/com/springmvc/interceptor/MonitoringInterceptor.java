package com.springmvc.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// 요청 처리 시간을 측정하고 로그로 남기는 인터셉터 클래스
public class MonitoringInterceptor implements HandlerInterceptor {

	// 각 쓰레드별 StopWatch 인스턴스를 저장하기 위한 ThreadLocal 객체
	ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

	// 로그 출력을 위한 Logger 설정
	public Logger logger = LoggerFactory.getLogger(this.getClass());

	// 생성자: 인터셉터 객체가 생성될 때 로그 출력
	public MonitoringInterceptor() {
		System.out.println("MonitoringInterceptor 생성자 호출됨!!");
	}

	// 컨트롤러 실행 직전에 호출되는 메서드 (전처리)
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		StopWatch stopWatch = new StopWatch(handler.toString()); // 요청 처리 시간 측정용 StopWatch 생성
		stopWatch.start(handler.toString()); // 시간 측정 시작
		stopWatchLocal.set(stopWatch); // 현재 쓰레드에 StopWatch 저장
		logger.info("접근한 URL 경로 : " + getURLPath(request)); // 요청 URL 로그 출력
		logger.info("요청 처리 시작 시각 : " + getCurrentTime()); // 시작 시간 로그 출력
		return true; // 컨트롤러로 계속 요청 전달
	}

	// 컨트롤러가 실행된 후, 뷰가 렌더링되기 전 호출되는 메서드 (중간 처리)
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("요청 처리 종료 시각 : " + getCurrentTime()); // 요청 종료 시간 로그 출력
	}

	// 뷰 렌더링까지 완료된 후에 호출되는 메서드 (후처리)
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		StopWatch stopWatch = stopWatchLocal.get(); // 쓰레드에서 StopWatch 가져오기
		stopWatch.stop(); // 시간 측정 종료
		logger.info("요청 처리 소요 시간 : " + stopWatch.getTotalTimeMillis() + " ms"); // 총 소요 시간 로그 출력
		stopWatchLocal.set(null); // 쓰레드 로컬 변수 초기화
		logger.info("==========================================="); // 구분선 출력
	}

	// 현재 요청의 전체 URL 경로를 문자열로 만들어 반환하는 메서드
	private String getURLPath(HttpServletRequest request) {
		String currentPath = request.getRequestURI(); // URI 경로
		String queryString = request.getQueryString(); // 쿼리 파라미터
		queryString = queryString == null ? "" : "?" + queryString; // null 체크 후 결합
		return currentPath + queryString;
	}

	// 현재 시간을 yyyy/MM/dd HH:mm:ss 포맷의 문자열로 반환하는 메서드
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
