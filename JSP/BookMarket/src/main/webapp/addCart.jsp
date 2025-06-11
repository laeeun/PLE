<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>

<%
    String id = request.getParameter("id");
    if (id == null || id.trim().equals("")) {
        response.sendRedirect("books.jsp");
        return;
    }

    BookRepository dao = BookRepository.getInstance();
    Book book = dao.getBookById(id);
    if (book == null) {
        response.sendRedirect("exceptionNoBookId.jsp");
        return;
    }

    ArrayList<Book> cartList = (ArrayList<Book>) session.getAttribute("cartList");
    if (cartList == null) {
        cartList = new ArrayList<Book>();
        session.setAttribute("cartList", cartList);
    }

    boolean exists = false;
    for (Book item : cartList) {
        if (item.getBookId().equals(id)) {
            item.setQuantity(item.getQuantity() + 1);
            exists = true;
            break;
        }
    }

    if (!exists) {
        book.setQuantity(1);
        cartList.add(book);
    }

    response.sendRedirect("book.jsp?id=" + id);
%>
