<!-- testDB_req.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="testDB.jsp">
		<p> 아이디 : <input type="text" name="id">
		<p> 비밀번호 : <input type="text" name="passwd">
		<p> 이 름 : <input type="text" name="name">
		<p> <input type="submit" value="send">
	</form>
</body>
</html>