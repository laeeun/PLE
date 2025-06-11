<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
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

        <%@ include file="dbconn.jsp" %>

        <div class="row align-items-md-stretch text-center">
        <%
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM book";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
        %>
            <div class="col-md-4 mb-4">
                <div class="h-100 p-2 border rounded">
                    <img src="./resources/images/<%= rs.getString("b_filename") %>" class="img-fluid mb-2" style="height: 350px; object-fit: cover;" />
                    <h5><b><%= rs.getString("b_name") %></b></h5>
                    <p><%= rs.getString("b_author") %></p>
                    <p><%= rs.getString("b_publisher") %> | <%= rs.getString("b_unitPrice") %>원</p>
                    <p><%= rs.getString("b_description").substring(0,60) %>...</p>
                    <p><a href="./book.jsp?id=<%= rs.getString("b_id") %>" class="btn btn-secondary">상세 정보 &raquo;</a></p>
                </div>
            </div>
        <%
            }

            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        %>
        </div> <!-- row 끝 -->

        <%@ include file="footer.jsp" %>
    </div>
</body>
</html>