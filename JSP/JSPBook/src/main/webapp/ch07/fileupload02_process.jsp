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
		String name1 = (String) request.getAttribute("name1");
		String name2 = (String) request.getAttribute("name2");
		String name3 = (String) request.getAttribute("name3");
		String subject1 = (String) request.getAttribute("subject1");
		String subject2 = (String) request.getAttribute("subject2");
		String subject3 = (String) request.getAttribute("subject3");
		String imageName1 = (String) request.getAttribute("fileName1");
		String imageName2 = (String) request.getAttribute("fileName2");
		String imageName3 = (String) request.getAttribute("fileName3");
	%>
	
	<h3>이름: <%= name1 %></h3>
	<h3>제목: <%= subject1 %></h3>
	<img src="resources/images/<%= imageName1 %>" width="300" height="auto">
	
	<h3>이름: <%= name2 %></h3>
	<h3>제목: <%= subject2 %></h3>
	<img src="resources/images/<%= imageName2 %>" width="300" height="auto">
	
	<h3>이름: <%= name3 %></h3>
	<h3>제목: <%= subject3 %></h3>
	<img src="resources/images/<%= imageName3 %>" width="300" height="auto">
</body>
</html>