<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link href = "./resources/css/bootstrap.min.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
	<!--
		 기능: 사용자가 새 데이터를 입력할 수 있도록 폼을 제공
	
		내용: 입력 폼(form 태그), POST 방식으로 서블릿이나 DB로 전송
	
		👉 분류: C (Create) 
	-->
    <form action="create" method="post">
       <p>아이디: <input type="text" name="id">
       <p>패스워드: <input type="text" name="pw">
       <p> <input type="submit" value="전송">
    </form>
</body>
</html>