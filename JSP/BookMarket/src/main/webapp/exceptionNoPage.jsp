<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<title>Insert title here</title>
</head>
<body>
	<div class="container py-4">
		<jsp:include page="menu.jsp" />
		<div class="p-5 mb-4 bg-body-tertiay rounded-3">
			<div class="container-fluid py-5">
				<h1 class="alert alert-danger">요청하신 페이지를 찾을 수 없습니다.</h1>
			</div>
		</div>
		<div class="row align-items-stretch">
			<div class="col-md-12">
				<div class="h-100 p-5">
					<p><%=request.getRequestURL() %></p>
					<p><a href="books.jsp" class="btn btn-secondary">도서 목록&raquo;</a>
				</div>
			</div>
		</div>
		<%@include file="footer.jsp" %>
	</div>
</body>
</html>