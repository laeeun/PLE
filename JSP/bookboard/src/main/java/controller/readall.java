package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.BoardRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/readall")
public class readall extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		BoardRepository repository = BoardRepository.getInstance();
        ArrayList arr = repository.readall(pageNum);
        int total_record = repository.getListCount();

        req.setAttribute("arr", arr);
        req.setAttribute("total_record", total_record);
        req.setAttribute("pageNum", pageNum);
        RequestDispatcher ds = req.getRequestDispatcher("boardview.jsp");
        ds.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
