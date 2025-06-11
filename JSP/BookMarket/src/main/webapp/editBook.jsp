<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>도서 편집</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script type="text/javascript">
    function deleteConfirm(id) {
      if (confirm("해당 도서를 삭제합니다!!") === true) {
        location.href = "./deleteBook.jsp?id=" + id;
      }
    }
  </script>
</head>
<%
  String edit = request.getParameter("edit");
%>
<body>
  <div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">도서 편집</h1>
        <p class="col-md-8 fs-4">Book Editing</p>
      </div>
    </div>

    <%@ include file="dbconn.jsp" %>

    <div class="row align-items-md-stretch text-center">
      <%
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
          String sql = "SELECT * FROM book";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();

          while (rs.next()) {
      %>
      <div class="col-md-4 mb-4">
        <div class="h-100 p-2 border rounded-3">
          <img src="./resources/images/<%= rs.getString("b_filename") %>" style="width:250px; height:350px;" class="mb-2" />
          <h5><b><%= rs.getString("b_name") %></b></h5>
          <p><%= rs.getString("b_publisher") %> | <%= rs.getString("b_unitPrice") %>원</p>
          <p><%= rs.getString("b_description") %></p>
          <%
            if (edit != null && edit.equals("update")) {
          %>
            <a href="./updateBook.jsp?id=<%= rs.getString("b_id") %>" class="btn btn-success">수정 &raquo;</a>
          <%
            } else if (edit != null && edit.equals("delete")) {
          %>
            <a href="#" onclick="deleteConfirm('<%= rs.getString("b_id") %>')" class="btn btn-danger">삭제 &raquo;</a>
          <%
            }
          %>
        </div>
      </div>
      <%
          } // end while
        } catch (SQLException e) {
          out.println("DB 오류: " + e.getMessage());
        } finally {
          if (rs != null) rs.close();
          if (pstmt != null) pstmt.close();
          if (conn != null) conn.close();
        }
      %>
    </div>

    <%@ include file="footer.jsp" %>
  </div>
</body>
</html>
