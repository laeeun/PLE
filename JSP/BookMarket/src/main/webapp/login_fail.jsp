<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>리다이렉트 중...</title>
</head>
<body>
<%
  response.sendRedirect("login.jsp?error=1");
%>
</body>
</html>
