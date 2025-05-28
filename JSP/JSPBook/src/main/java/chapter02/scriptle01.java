package chapter02;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/scriptle01")
public class scriptle01 extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("scriptle01 연결되었음");
		RequestDispatcher ds = req.getRequestDispatcher("chapter02/scriptle01.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("scriptle01 연결되었음");
		RequestDispatcher ds = req.getRequestDispatcher("chapter02/scriptle01.jsp");
		ds.forward(req, resp);
	}

}
