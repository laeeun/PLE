package controller;

import java.io.IOException;
import java.util.ArrayList;
import dao.member_repository;
import dto.member;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/read_all")
public class readAll_controller extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        member_repository repo = member_repository.getInstance();
        ArrayList<member> allmember = repo.readall();

        req.setAttribute("allmember", allmember);
        RequestDispatcher rd = req.getRequestDispatcher("allview.jsp");
        rd.forward(req, resp);
    }
}
