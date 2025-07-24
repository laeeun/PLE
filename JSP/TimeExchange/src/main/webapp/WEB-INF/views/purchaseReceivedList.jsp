<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>받은 구매 요청</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                    <tr id="row-${req.request_id}">
                        <td>${req.request_id}</td>
                        <td>${req.talent_id}</td>
                        <td>${req.buyer_id}</td>
                        <td>${req.requested_at}</td>
                        <td class="status-text">${req.status}</td>
                        <td>
                            <c:if test="${req.status == 'PENDING'}">
                                <button class="btn btn-success btn-sm approve-btn"
                                        data-id="${req.request_id}">수락</button>
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

<script>
$(document).ready(function () {
    $(".approve-btn").click(function () {
        const requestId = $(this).data("id");
        const row = $("#row-" + requestId);
        const statusText = row.find(".status-text");
        const approveBtn = $(this);
        const rejectBtn = row.find("form button[type='submit']");

        $.ajax({
            url: "<c:url value='/purchase/approve/ajax'/>",
            method: "POST",
            data: { request_id: requestId },
            success: function (data) {
                if (data.success) {
                    statusText.text("APPROVED");
                    approveBtn.prop("disabled", true);
                    rejectBtn.prop("disabled", true);
                    refreshSessionAndBalance();  // 세션 동기화
                    alert(data.message);
                } else {
                    alert("❌ 오류: " + data.message);
                }
            },
            error: function () {
                alert("서버와의 통신 중 오류가 발생했습니다.");
            }
        });
    });
});

// 세션 갱신 및 보유 시간 갱신
function refreshSessionAndBalance() {
    $.ajax({
        url: "<c:url value='/session/refresh' />",
        method: "POST",
        success: function (res) {
            if (res.success && res.updatedAccount !== undefined) {
                const el = document.querySelector("#accountBalance");
                if (el) el.textContent = res.updatedAccount + " 시간";
            }
        }
    });
}
</script>
</body>
</html>
