package chapter05;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/exam5_1")
public class request01 extends HttpServlet{
	// ?id=a&pw=1

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("exam5_1 연결");
		//컨트롤러의 역할
		//전처리(데이터 확보단계)
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		System.out.println(id);
		System.out.println(pw);
		//모델이동(데이터베이스로가는 단계):지금은 생략중
		//뷰이동(jsp로 이동)
		req.setAttribute("userid", id);
		req.setAttribute("userpw", pw);
		
		RequestDispatcher ds = req.getRequestDispatcher("chapter05/request01_process.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
