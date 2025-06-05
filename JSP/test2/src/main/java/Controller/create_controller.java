package Controller;

import java.io.IOException;

import dao.member_repository;
import dto.member;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create")
public class create_controller extends HttpServlet {
	
	member_repository repository = member_repository.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher ds = req.getRequestDispatcher("createForm.jsp");
		ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//전처리 : form --> 데이터 전달받음 
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		System.out.println(id+","+pw);
		//전처리 : 전달된 데이터를 묶음
		member mb = new member();
		mb.setId(id);
		mb.setPw(pw);
		
		//DB에 저장하기 위해서 repository클래스에게 전달.
		repository.save(mb);
		
		resp.sendRedirect("read_all");
	}
	
}
