package chapter02;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//어노테이션이라고 하고 특정 객체를 자동으로 생성 NoCodeBase
@WebServlet("/declaration01")
public class declaration01 extends HttpServlet
{

	//get 방식 입장
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("declaration01 연결되었음.");
		RequestDispatcher ds = req.getRequestDispatcher("chapter02/declaration01.jsp");
		ds.forward(req, resp);
	}

	//post 방식 입장
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{

	}
	
}
