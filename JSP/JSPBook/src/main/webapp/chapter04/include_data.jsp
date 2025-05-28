<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<P>오늘의 날짜 및 시각</P>
	<p><%=(new java.util.Date()).toLocaleString() %></p>
</body>
</html>