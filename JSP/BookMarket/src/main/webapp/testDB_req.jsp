<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 입력</title>
</head>
<body>
	<h2>회원 정보 입력 폼</h2>
	<form method="post" action="testDB.jsp">
		<p>아이디 : <input type="text" name="id" /></p>
		<p>비밀번호 : <input type="password" name="passwd" /></p>
		<p>이름 : <input type="text" name="name" /></p>
		<p><input type="submit" value="전송" /></p>
	</form>
</body>
</html>
