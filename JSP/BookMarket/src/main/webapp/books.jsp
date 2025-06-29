<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>도서 목록</title>
</head>
<body>
    <div class="container py-4">
    
        <%@ include file="menu.jsp" %>

        <div class="p-5 mb-4 bg-body-tertiary rounded-3">
            <div class="container-fluid py-5">
                <h1 class="display-5 fw-bold">도서목록</h1>
                <p class="col-md-8 fs-4">BookList</p>
            </div>
        </div>

        <div class="row align-items-md-stretch text-center">
        <%
            // DB에서 도서 정보 가져오기
            BookRepository bookDAO = BookRepository.getInstance();
            ArrayList<Book> bookList = bookDAO.getAllBooks();

            for (Book book : bookList) {
        %>
        
            <div class="col-md-4 mb-4">
                <div class="h-100 p-2 border rounded">
                    <img src="<%= request.getContextPath() %>/resources/images/<%= book.getFileName() %>" style="width:250px; height:350px" alt="<%= book.getName() %> 이미지" />
                    <h5><b><%= book.getName() %></b></h5>
                    <p><%= book.getAuthor() %></p>
                    <p><%= book.getPublisher() %> | <%= book.getUnitPrice() %>원</p>
                    <p><%= book.getDescription() %>&hellip;</p>
                    <p>
                        <a href="book.jsp?id=<%= book.getBookId() %>" class="btn btn-secondary" role="button">
                            상세 정보 &raquo;
                        </a>
                    </p>
                </div>
            </div>
        <%
            }
        %>
        
        </div>

        <%@ include file="footer.jsp" %>
    </div>
</body>
</html>
