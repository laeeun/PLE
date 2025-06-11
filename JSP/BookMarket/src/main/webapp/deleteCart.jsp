<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>장바구니 초기화</title>
</head>
<body>
<%
  String id = request.getParameter("cartId");
  if (id == null || id.trim().equals("")) {
      response.sendRedirect("cart.jsp");
      return;
  }

  session.invalidate();  // 세션 전체 무효화 (주의: 로그인 정보도 날아감)
  response.sendRedirect("cart.jsp");
%>
</body>
</html>
