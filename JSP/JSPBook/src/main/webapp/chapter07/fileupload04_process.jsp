<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%
	HashMap<String,String> text=(HashMap<String,String>)request.getAttribute("map");
	String image=(String)request.getAttribute("image");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>이름</h1>
	<h3><%=text.get("name") %></h3>
	<h1>subject</h1>
	<h3><%=text.get("subject") %></h3>
	<img src="resources/images/<%=image %>">
</body>
</html>