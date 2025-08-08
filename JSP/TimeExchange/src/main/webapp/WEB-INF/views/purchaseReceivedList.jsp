<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>받은 구매 요청</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
       :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }
		a:hover {
			text-decoration:none;
			color: var(--accent);
		}
        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            color: var(--text-main);
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 900px;
            margin: 60px auto;
            background: var(--surface-alt);
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
            border: 1px solid var(--border);
        }

        h2 {
            font-size: 24px;
            font-weight: 700;
            color: var(--primary);
            text-align: center;
            margin-bottom: 30px;
        }

        .table {
            background-color: #fff;
            border-radius: 12px;
            overflow: hidden;
        }

        .table th {
            background: #f5f5f5;
            color: var(--primary);
            font-weight: 600;
        }

        .table td {
            vertical-align: middle;
        }

        .btn {
            border-radius: 10px;
            padding: 6px 12px;
            font-weight: 600;
        }

        .btn-success {
            background-color: var(--accent);
            border: none;
        }

        .btn-success:hover {
            background-color: #e85c26;
        }

        .btn-danger {
            background-color: #dc3545;
            border: none;
        }

        .btn-primary {
            background-color: var(--primary);
            border: none;
        }

        .btn-secondary {
            background-color: #6c757d;
            border: none;
        }

        .alert {
            padding: 12px 16px;
            background: #f1f5f9;
            border: 1px solid var(--border);
            border-radius: 10px;
            color: #374151;
            margin-bottom: 20px;
        }

        .text-muted {
            color: var(--text-sub) !important;
        }

        .status-text {
            font-weight: 600;
        }

        @media (max-width: 768px) {
            .container {
                padding: 20px;
            }
            .table {
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container">
    <h2><i class="fas fa-inbox"></i> 받은 구매 요청 목록</h2>

    <c:if test="${empty receivedRequests}">
       <div class="alert">
		    <i class="fas fa-bell-slash"></i> 아직 받은 구매 요청이 없습니다.
		</div>
    </c:if>

    <c:if test="${not empty receivedRequests}">
        <table class="table table-bordered mt-4">
            <thead>
                <tr>           
                    <th>구매자</th>
                    <th>요청 시간</th>
                    <th>상태</th>
                    <th>처리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="req" items="${receivedRequests}">
                    <tr id="row-${req.request_id}">           
                        <td><a href="<c:url value='/profile/${req.buyer_id}' />">${req.buyer_id}</a></td>
                        <td>${req.formattedRequestedAt}</td>
                        <td class="status-text">
                         <c:choose>
						    <c:when test="${req.status == 'APPROVED'}">
						      <i class="fa-solid fa-circle-check status-icon status-approved"></i> 승인됨
						    </c:when>
						    <c:when test="${req.status == 'PENDING'}">
						      <i class="fa-solid fa-clock status-icon status-pending"></i> 대기중
						    </c:when>
						    <c:when test="${req.status == 'REJECTED'}">
						      <i class="fa-solid fa-circle-xmark status-icon status-rejected"></i> 거절됨
						    </c:when>
						    <c:otherwise>
						      <i class="fa-regular fa-circle-question"></i> 알 수 없음
						    </c:otherwise>
						  </c:choose>
				  		</td>
                        <td>
                            <c:if test="${req.status == 'PENDING'}">
                                <button class="btn btn-success btn-sm approve-btn" data-id="${req.request_id}">수락</button>
                                <form action="<c:url value='/purchase/reject' />" method="post" class="d-inline">
                                    <input type="hidden" name="request_id" value="${req.request_id}" />
                                    <button type="submit" class="btn btn-danger btn-sm">거절</button>
                                </form>
                            </c:if>
                            <c:if test="${req.status == 'APPROVED'}">
                                <button class="btn btn-primary btn-sm assign-btn"
                                        data-trade="${req.request_id}"
                                        data-buyer="${req.buyer_id}">
                                    숙제 보내기
                                </button>
                            </c:if>
                            <c:if test="${req.status != 'PENDING' && req.status != 'APPROVED'}">
                                <span class="text-muted">${req.status}</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="text-center mt-4">
        <a href="<c:url value='/mypage' />" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> 마이페이지로 돌아가기
        </a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

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
                    refreshSessionAndBalance();
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

$(document).on('click', '.assign-btn', function () {
    const tradeId = $(this).data('trade');
    const receiver = $(this).data('buyer');
    const base = "<c:url value='/todo/assign/popup' />";
    const url = base + "?tradeId=" + encodeURIComponent(tradeId)
                      + "&receiverId=" + encodeURIComponent(receiver);
    window.open(url, "assignTodo", "width=640,height=720,scrollbars=yes,resizable=yes");
});
</script>
</body>
</html>
