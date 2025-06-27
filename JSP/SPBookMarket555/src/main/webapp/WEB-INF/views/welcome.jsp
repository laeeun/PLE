<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<link href="./resources/css/bootstrap.min.css" rel="stylesheet">
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="<c:url value="/home"/>">Home</a>
				<a class="navbar-brand" href="<c:url value="/books/all"/>">books</a>
			</div>
		</div>
	</nav>
	
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3 text-center">${greeting}</h1>		
		</div>
	</div>
	
	<div class="container">
		<div class="text-center">
			<h3>${strapline}</h3>		
		</div>
	</div>
	
	<footer class="container">
		<hr>
		<p>&copy; WebMarket</p>
	</footer>
</body>
</html>