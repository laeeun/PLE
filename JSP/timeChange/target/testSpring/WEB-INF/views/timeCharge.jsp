<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>시간 충전소</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>⏱ 시간 충전소</h2>

    <!-- ✅ 세션의 현재 사용자 잔액 표시 -->
    <div class="alert alert-info mt-3">
        현재 잔액: <strong>${sessionScope.loggedInUser.account}</strong> 분
    </div>

    <!-- ✅ 성공/에러 메시지 출력 -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- ✅ 충전 폼 -->
    <form action="<c:url value='/charge' />" method="post" class="mt-4">
        <div class="mb-3">
            <label for="amount" class="form-label">충전할 시간 (분)</label>
            <input type="number" class="form-control" id="amount" name="amount" min="1" required>
        </div>
        <button type="submit" class="btn btn-primary">충전하기</button>
        <a href="<c:url value='/' />" class="btn btn-secondary">홈으로</a>
    </form>
</div>
</body>
</html>
