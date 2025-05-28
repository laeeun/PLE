<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
		String name = (String) request.getAttribute("name");
		String subject = (String) request.getAttribute("subject");
		String imageName2 = (String) request.getAttribute("imageName2");
	%>
	
	<h3>이름: <%= name %></h3>
	<h3>제목: <%= subject %></h3>
	<img src="resources/images/<%= imageName2 %>" width="300" height="auto">
</body>
</html>