package chapter13;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/session01")
public class session01 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("session01 doget 연결");
		RequestDispatcher ds = req.getRequestDispatcher("chapter13/session01.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			System.out.println("session01 dopost 연결");
			String id=req.getParameter("id");
			String pw=req.getParameter("pw");
			
			if("admin".equals(id) && pw.equals("1234")) {
				//로그인성공로직
				//세션생성 //싱글턴 방식
				//true : 없으면 새로 만들고 있으면 그냥 그거줘! 비워놓으면 기본값 true
				//false: 없으면 null을 주고 있으면 그냥 그거줘!
				HttpSession session=req.getSession(true);
				session.setAttribute("id", id);
				session.setAttribute("pw", pw);
			}
			
			RequestDispatcher ds = req.getRequestDispatcher("chapter13/session01_success.jsp");
			ds.forward(req, resp);
	}

}
