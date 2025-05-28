<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		HashMap<String,String> text = (HashMap<String,String>) request.getAttribute("map");
		String image = (String) request.getAttribute("image");
	%>

	<h3>이름: <%= text.get("name") %></h3>
	<h3>제목: <%= text.get("subject") %></h3>
	<img src="resources/images/<%= image %>" width="300" height="auto">
</body>
</html>