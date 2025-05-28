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
		HashMap<String,String> data =(HashMap<String,String>) request.getAttribute("mapdata");
		//foreach로 하나씩 키를 꺼내서 가져옴
		for(String key : data.keySet()){
			String value = data.get(key);
			out.println("<P>"+key+":"+value+"</p>");
		}
	%>
</body>
</html>