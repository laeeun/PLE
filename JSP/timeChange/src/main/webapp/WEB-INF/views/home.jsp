<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <p>홈입니다.
   
	<form action="${pageContext.request.contextPath}/login" method="get">
	  <button type="submit">로그인</button>
	</form>
  
</body>
</html>