package ch05;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/request02")
public class request02 extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		System.out.println("request02 연결");
		
		HashMap<String,String> pack = new HashMap<String,String>(); 
		
		Enumeration en = req.getHeaderNames();
		while(en.hasMoreElements())
		{
			String headerName = (String)en.nextElement();
			String headerValue = req.getHeader(headerName);
			pack.put(headerName, headerValue);
		}
		
		req.setAttribute("mapData", pack);
		RequestDispatcher ds = req.getRequestDispatcher("ch05/request02.jsp");
		ds.forward(req, resp);
	}
	
}
