package controller;

import java.io.IOException;
import dao.member_repository;
import dto.member;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/update")
public class update_controller extends HttpServlet {
    member_repository repo = member_repository.getInstance();

    // 수정폼 보여주기 (GET 방식)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        member mb = repo.readone(id); // 해당 회원 정보 불러옴
        req.setAttribute("member", mb);

        RequestDispatcher rd = req.getRequestDispatcher("updateForm.jsp");
        rd.forward(req, resp);
    }

    // 수정 처리 (POST 방식)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        member mb = new member();
        mb.setId(id);
        mb.setPw(pw);

        repo.update(mb);
        resp.sendRedirect("read_all");
    }
}
