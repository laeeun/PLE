<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<title>배송정보 입력</title>
</head>
<body>
	<div class="container py-4">
		<%@ include file="menu.jsp" %>

		<div class="p-5 mb-4 bg-body-tertiary rounded-3">
			<div class="container-fluid py-5">
				<h1 class="display-5 fw-bold">배송정보</h1>
				<p class="col-md-8 fs-4">Shipping Info</p>
			</div>
		</div>

		<div class="row align-items-md-stretch">
			<form action="./processShippingInfo.jsp" method="post">
				<input type="hidden" name="cartId" value="<%= request.getParameter("cartId") %>">

				<div class="mb-3 row">
					<label for="name" class="col-sm-2 col-form-label">성명</label>
					<div class="col-sm-3">
						<input type="text" id="name" name="name" class="form-control" required>
					</div>
				</div>

                <div class="mb-3 row">
					<label for="shippingDate" class="col-sm-2 col-form-label">배송일</label>
					<div class="col-sm-3">
						<input type="text" id="shippingDate" name="shippingDate" class="form-control" placeholder="yyyy/mm/dd" required>
					</div>
				</div>

				<div class="mb-3 row">
					<label for="country" class="col-sm-2 col-form-label">국가명</label>
					<div class="col-sm-3">
						<input type="text" id="country" name="country" class="form-control" required>
					</div>
				</div>

				<div class="mb-3 row">
					<label for="zipCode" class="col-sm-2 col-form-label">우편번호</label>
					<div class="col-sm-3">
						<input type="text" id="zipCode" name="zipCode" class="form-control" required>
					</div>
				</div>

				<div class="mb-3 row">
					<label for="addressName" class="col-sm-2 col-form-label">주소</label>
					<div class="col-sm-5">
						<input type="text" id="addressName" name="addressName" class="form-control" required>
					</div>
				</div>

				<div class="mb-3 row">
					<div class="offset-sm-2 col-sm-10">
						<a href="./cart.jsp?cartId=<%= request.getParameter("cartId") %>" class="btn btn-secondary me-2">이전</a>
						<input type="submit" class="btn btn-primary me-2" value="등록" />
						<a href="./checkOutCancelled.jsp" class="btn btn-secondary">취소</a>
					</div>
				</div>
			</form>
		</div>

		<jsp:include page="footer.jsp" />
	</div>
</body>
</html>
