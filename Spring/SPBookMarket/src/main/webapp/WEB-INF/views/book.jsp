<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%System.out.println("👉 book 입짱~"); %>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
<meta charset="UTF-8">
<title>도서 상세 정보</title>
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
			<h1 class="display-3">도서 정보</h1>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<c:choose>
					<c:when test="${book.getBookImage() == null}">
						<img src="<c:url value="/resources/images/${book.getBookId()}.png"/>" style="width:100%"/>
					</c:when>
					<c:otherwise>
						<img src="<c:url value="/resources/images/${book.getBookImage().getOriginalFilename()}"/>" style="width:100%"/>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col-md-8">
				<h3>${book.name}</h3>
				<p>${book.description}</p>
				<br>
				<p><b>도서코드 : </b><span class="badge badge-info">${book.bookId}</span></p>
				<p><b>저자 : </b>${book.author}</p>
				<p><b>출판사 : </b>${book.publisher}</p>
				<p><b>출판일 : </b>${book.releaseDate}</p>
				<p><b>분류 : </b>${book.category}</p>
				<p><b>재고수 : </b>${book.unitsInStock}</p>
				<h4>${book.unitPrice}원</h4>
				<br>
				<p><a href="#" class="btn btn-primary">도서주문 &raquo;</a>
				<a href="<c:url value="/books"/>" class="btn btn-secondary">도서목록 &raquo;</a>
			</div>
		</div>
		<hr>
		<footer class="container">
			<p>&copy; WebMarket</p>
		</footer>
	</div>
	
</body>
</html>