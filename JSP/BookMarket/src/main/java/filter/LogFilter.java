package filter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

public class LogFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println(" 접속한 클라이언트 IP : " + arg0.getRemoteAddr());
		long start = System.currentTimeMillis();
		System.out.println("접근한 URL 경로 :" + getURLPath(arg0));
		System.out.println("요청 처리 소요 시간 :" + getCurrentTime());
		arg2.doFilter(arg0,arg1);
		
		long end = System.currentTimeMillis();
		System.out.println("요청 처리 종료 시간 :" + getCurrentTime());
		System.out.println("요청 처리 소요 시간 :" +(end-start)+"ms ");
		System.out.println("========================================");
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("BookMarket 초기화");
	}

	private String getURLPath(ServletRequest request) {
		HttpServletRequest req;
		String currentPath="";
		String queryString="";
		if(request instanceof HttpServletRequest) {
			req=(HttpServletRequest)request;
			currentPath = req.getRequestURI();
			queryString=req.getQueryString();
			queryString=queryString==null ? "" : "?" + queryString;
		}
		return currentPath+queryString;
	}

	private String getCurrentTime() {
		DateFormat formatter=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
