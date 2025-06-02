<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		System.out.println("session01.jsp 도착");
	%>
	<form action="session01" method="post">
		<p>아 이 디: <input type="text" name="id"></p>
		<p>비밀번호: <input type="password" name="pw"></p>
		<p><input type="submit" value="전송"></p>
	</form>
</body>
</html>