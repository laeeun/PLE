package filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

public class LogFileFilter implements Filter{
	
	PrintWriter writer;
	
	@Override
	public void destroy() {
		writer.close();
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		writer.println("접속한 클라이언트 IP : " +arg0.getRemoteAddr());
		long start=System.currentTimeMillis();
		writer.println("접근한 URL 경로 :" + getCurrentTime());
		
		arg2.doFilter(arg0, arg1);
		
		long end=System.currentTimeMillis();
		writer.println(" 요청 처리 종료 시각 :" + getCurrentTime());
		writer.println(" 요청 처리 소요 시간 :" + (end - start) + "ms ");
		writer.println("==================================================");
		
	}

	@Override
	public void init(FilterConfig Config) throws ServletException {
		String filename=Config.getInitParameter("filename");
		
		if(filename==null) {
			throw new ServletException("로그 파일의 이름을 찾을 수 없습니다.");
		}
		
		try {
			writer=new PrintWriter(new FileWriter(filename, true), true);
		}catch(IOException e){
			throw new ServletException("로그 파일을 열 수 없습니다.");
		}
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
