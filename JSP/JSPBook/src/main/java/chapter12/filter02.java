package chapter12;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/filter02")
public class filter02 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("filter02 컨트롤 연결");
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		
		
		req.setAttribute("id", id);
		req.setAttribute("pw", pw);
		RequestDispatcher ds = req.getRequestDispatcher("chapter12/filter02_process.jsp");
		ds.forward(req, resp);
	}

}
