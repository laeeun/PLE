<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%System.out.println("👉 books 입짱~"); %>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<meta charset="UTF-8">
<title>도서목록</title>
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
			<h1 class="display-3">도서 목록</h1>
		</div>
	</div>
	
	<div class="container">
		<div class="row" align="center">
			<c:forEach items="${bookList}" var="book">
				<div class="col-md-4">
					<c:choose>
						<c:when test="${book.getBookImage() == null}">
							<img src="<c:url value="/resources/images/${book.getBookId()}.png"/>" style="width:60%"/>
						</c:when>
						<c:otherwise>
							<img src="<c:url value="/resources/images/${book.getBookImage().getOriginalFilename()}"/>" style="width:60%"/>
						</c:otherwise>
					</c:choose>
					<h3>${book.name}</h3>
					<p>${book.author}</p>
						<br>${book.publisher} | ${book.releaseDate}
					<p align=left>${fn:substring(book.description, 0, 100)}...</p>
					<p>${book.unitPrice}원</p>
					<p><a href="<c:url value="/books/book?id=${book.bookId}"/>" class="btn btn-secondary" role="button">상세정보 &raquo;</a></p>
				</div>
			</c:forEach>
		</div>
		
		<hr>
		
		<footer class="container">
			<p>&copy; WebMarket</p>
		</footer>
	</div>
</body>
</html>