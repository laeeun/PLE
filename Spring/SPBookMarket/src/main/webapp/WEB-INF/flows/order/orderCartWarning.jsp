<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<title>Thank you</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="./home">Home</a>
			</div>
		</div>
	</nav>
	
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">주문 취소</h1>
		</div>
	</div>
	
	<div class="container">
		<h2 class="alert alert-danger">주문을 취소하였습니다.</h2>
	</div>
	<div class="container">
		<p><a href="<c:url value="/books"/>" class="btn btn-primary">&raquo; 도서목록</a></p>
	</div>
</body>
</html>