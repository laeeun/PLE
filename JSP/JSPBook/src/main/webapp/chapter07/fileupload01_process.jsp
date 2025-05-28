<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String name1 =(String) request.getAttribute("name1");
		String subject1 =(String) request.getAttribute("subject1");
		String image1 =(String) request.getAttribute("image1_2");
		String image2 =(String) request.getAttribute("image2_2");
		String image3 =(String) request.getAttribute("image3_2");
		String image4 =(String) request.getAttribute("image4_2");
	%>
	<h3>이름 : <%=name1 %></h3>
	<h3>제목 : <%=subject1 %></h3>
	<!-- 상대 경로는 현재url의 영향을 받는다 -->
	<img src="resources/images/<%=image1 %>">
	<img src="resources/images/<%=image2 %>">
	<img src="resources/images/<%=image3 %>">
	<img src="resources/images/<%=image4 %>">
</body>
</html>