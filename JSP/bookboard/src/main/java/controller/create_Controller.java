package controller;

import java.io.IOException;
import java.time.LocalDate;

import dao.BoardRepository;
import dto.BoardDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/create")
public class create_Controller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("create get");
		
		RequestDispatcher ds = req.getRequestDispatcher("form.jsp");
		ds.forward(req, resp);	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("create form");
		// id="admin" name="kim"
		String id = "admin";
		String name = req.getParameter("name");
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String regist_day = LocalDate.now().toString();
		int hit = 0;
		String ip = req.getRemoteAddr();
		
		BoardDTO dto = new BoardDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setRegist_day(regist_day);
		dto.setHit(hit);
		dto.setIp(ip);
		
		BoardRepository repository = BoardRepository.getInstance();
        repository.create(dto);

        resp.sendRedirect("readall?pageNum=1");
		
	}

}
