package chapter05;

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
public class request02 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("request02 연결");
		//전처리
		HashMap<String,String> pack =new HashMap<String,String>();
		Enumeration en = req.getHeaderNames();
		while(en.hasMoreElements()) {
			String headerName =(String) en.nextElement(); //키값을 꺼내서 변수에 대입함
			String headerValue =req.getHeader(headerName); //키값을 이용해서 value값을 변수에 대입함
			pack.put(headerName, headerValue); //키값과 value값을 하나의 쌍으로 map에 넣음
		}
		//뷰이동
		req.setAttribute("mapdata", pack);
		RequestDispatcher ds = req.getRequestDispatcher("chapter05/requset02.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
