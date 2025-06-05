<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link href = "./resources/css/bootstrap.min.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
<%
   member mb = (member)request.getAttribute("member");
%>
    <form action="update" method="post">
       <p>아이디: <input type="text" name="id" value="<%=mb.getId()%>" readonly>
       <p>패스워드: <input type="text" name="pw" value="<%=mb.getPw()%>">
       <p> <input type="submit" value="전송">
    </form>
</body>
</html> 