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
	<h1>헤더 정보</h1>
	<%
		HashMap<String,String> data = (HashMap<String,String>) request.getAttribute("mapData");
	
		for(String key : data.keySet())
		{
			String value = data.get(key);
			out.println("<p>"+key+" : "+value+"</p>");
		}
	%>
</body>
</html>