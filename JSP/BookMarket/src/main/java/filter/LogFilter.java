package filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

public class LogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("BookMarket 초기화");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {

		System.out.println("접속한 클라이언트 IP: " + arg0.getRemoteAddr());

		long start = System.currentTimeMillis();
		System.out.println("접근한 URL 경로: " + getURLPath(arg0));
		System.out.println("요청 처리 시작 시각: " + getCurrentTime());

		arg2.doFilter(arg0, arg1);

		long end = System.currentTimeMillis();
		System.out.println("요청 처리 종료 시각: " + getCurrentTime());
		System.out.println("요청 처리 소요 시간: " + (end - start) + "ms");
		System.out.println("========================================");
	}

	@Override
	public void destroy() {
		// 자원 정리 필요 시 구현
	}

	// 요청 URL 경로 + 쿼리스트링 반환
	private String getURLPath(ServletRequest request) {
		String currentPath = "";
		String queryString = "";

		if (request instanceof HttpServletRequest req) {
			currentPath = req.getRequestURI();
			queryString = req.getQueryString();
			queryString = (queryString == null) ? "" : "?" + queryString;
		}
		return currentPath + queryString;
	}

	// 현재 시각 반환
	private String getCurrentTime() {
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
