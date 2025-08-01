<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>주문 취소</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">주문 취소</h1>
        <p class="col-md-8 fs-4">Order Cancellation</p>
      </div>
    </div>

    <div class="row align-items-md-stretch">
      <h2 class="alert alert-danger">주문이 취소되었습니다</h2>
    </div>

    <div class="container">
      <p>
        <a href="./books.jsp" class="btn btn-secondary">&raquo; 도서목록</a>
      </p>
    </div>

    <%@ include file="footer.jsp" %>
  </div>
</body>
</html>
