package controller;

import java.io.IOException;
import dao.member_repository;
import dto.member;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/create")
public class create_controller extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        member mb = new member();
        mb.setId(id);
        mb.setPw(pw);

        member_repository repo = member_repository.getInstance();
        repo.create(mb);

        resp.sendRedirect("read_all");
    }
}
