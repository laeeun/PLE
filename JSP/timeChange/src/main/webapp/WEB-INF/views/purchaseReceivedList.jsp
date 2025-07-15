<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>받은 구매 요청</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4">📥 받은 구매 요청 목록</h2>

    <c:if test="${empty receivedRequests}">
        <div class="alert alert-info">아직 받은 구매 요청이 없습니다.</div>
    </c:if>

    <c:if test="${not empty receivedRequests}">
        <table class="table table-hover">
            <thead class="table-light">
                <tr>
                    <th>요청 ID</th>
                    <th>재능 ID</th>
                    <th>구매자 ID</th>
                    <th>요청 시간</th>
                    <th>상태</th>
                    <th>처리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="req" items="${receivedRequests}">
                    <tr>
                        <td>${req.request_id}</td>
                        <td>${req.talent_id}</td>
                        <td>${req.buyer_id}</td>
                        <td>${req.requested_at}</td>
                        <td>${req.status}</td>
                        <td>
                            <c:if test="${req.status == 'PENDING'}">
                                <form action="<c:url value='/purchase/approve' />" method="post" class="d-inline">
                                    <input type="hidden" name="request_id" value="${req.request_id}" />
                                    <button type="submit" class="btn btn-success btn-sm">수락</button>
                                </form>
                                <form action="<c:url value='/purchase/reject' />" method="post" class="d-inline">
                                    <input type="hidden" name="request_id" value="${req.request_id}" />
                                    <button type="submit" class="btn btn-danger btn-sm">거절</button>
                                </form>
                            </c:if>
                            <c:if test="${req.status != 'PENDING'}">
                                <span class="text-muted">${req.status}</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <a href="<c:url value='/mypage' />" class="btn btn-secondary mt-3">🔙 마이페이지로 돌아가기</a>
</div>
</body>
</html>
