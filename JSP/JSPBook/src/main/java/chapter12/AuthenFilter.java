package chapter12;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AuthenFilter implements Filter{

	@Override
	public void destroy() {
		System.out.println("Filter01 해제...");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("Filter01.jsp 수행...");
		String name=arg0.getParameter("name");
		
		if(name==null || name.equals("")) {
			arg1.setCharacterEncoding("UTF-8");
			arg1.setContentType("text/html; charset=UTF-8");
			PrintWriter writer=arg1.getWriter();
			String message="입력된 name 값은 null입니다.";
			writer.println(message);
			return;
		}
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Filter01 초기화...");
	}

}
