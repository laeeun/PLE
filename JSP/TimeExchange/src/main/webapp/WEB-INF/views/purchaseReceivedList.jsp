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

    <style>
      :root {
        --tf-primary: #7c6cff;
        --tf-primary-2: #a88bff;
        --tf-bg-1: #f7f5ff;
        --tf-text: #231f2b;
        --tf-border: rgba(124,108,255,.28);
        --tf-glass: rgba(255,255,255,.66);
        --tf-shadow: 0 14px 36px rgba(124,108,255,.16);
        --tf-white: #ffffff;
        --tf-success: #10b981;
        --tf-danger: #ef4444;
        --tf-info: #3b82f6;
      }
      @media (prefers-color-scheme: dark){
        :root {
          --tf-bg-1: #0f0e13;
          --tf-text: #f5f4f8;
          --tf-glass: rgba(22,20,29,.62);
          --tf-white: #171523;
          --tf-border: rgba(168,139,255,.24);
          --tf-shadow: 0 16px 40px rgba(168,139,255,.14);
        }
      }

      html, body { height: 100%; }
      body {
        font-family: 'Pretendard', sans-serif;
        color: var(--tf-text);
        background:
          radial-gradient(1200px 600px at 10% -10%, #efeaff 0%, transparent 60%),
          radial-gradient(1000px 500px at 110% 0%, #f5f0ff 0%, transparent 55%),
          linear-gradient(180deg, var(--tf-bg-1), #ffffff);
        background-attachment: fixed;
      }

      .container.mt-5 {
        max-width: 960px;
        margin-top: 64px !important;
      }

      .container.mt-5 h2.mb-4 {
        font-weight: 800;
        letter-spacing: -0.2px;
        margin-bottom: 22px !important;
        padding-bottom: 12px;
        background: linear-gradient(90deg, var(--tf-primary), var(--tf-primary-2));
        -webkit-background-clip: text;
        background-clip: text;
        color: transparent;
        position: relative;
        display: inline-flex;
        align-items: center;
        gap: 8px;
      }

      .container.mt-5 h2.mb-4::after {
        content: "";
        position: absolute;
        left: 0;
        bottom: 0;
        height: 3px;
        width: 100%;
        background: linear-gradient(90deg, rgba(124,108,255,.35), rgba(168,139,255,0) 60%);
        border-radius: 999px;
      }

      /* ================= Alert 스타일 (아이콘 + 텍스트 분리) ================= */
      .container.mt-5 .alert {
        display: grid !important;
        grid-template-columns: 22px 1fr;
        align-items: start;
        column-gap: 10px;
        padding: 12px 16px !important;
        padding-left: 16px !important;
        line-height: 1.35;
        border-radius: 18px;
        border: 1px solid rgba(0,0,0,.06);
        background: linear-gradient(180deg, #eef3ff, #ffffff);
        box-shadow: var(--tf-shadow);
        color: #1e3a8a;
        z-index: 1;
        position: relative;
      }

      .container.mt-5 .alert::before {
        content: "i";
        position: static !important;
        grid-column: 1;
        width: 22px;
        height: 22px;
        border-radius: 50%;
        display: inline-flex;
        align-items: center;
        justify-content: center;
        background: #fff;
        box-shadow: 0 4px 10px rgba(0,0,0,.07);
        font-weight: 900;
        color: var(--tf-info);
        flex: none;
      }

      .container.mt-5 .alert-success::before { content: "✓"; color: var(--tf-success); }
      .container.mt-5 .alert-danger::before  { content: "!"; color: var(--tf-danger); }

      .table {
        overflow: hidden;
        border-radius: 18px;
        background:
          linear-gradient(180deg, var(--tf-glass), var(--tf-glass)) padding-box,
          linear-gradient(135deg, rgba(124,108,255,.38), rgba(168,139,255,.26)) border-box;
        border: 1px solid transparent;
        backdrop-filter: blur(10px);
        -webkit-backdrop-filter: blur(10px);
        box-shadow: var(--tf-shadow);
      }

      .table thead.table-light th {
        border-bottom: 0;
        background: linear-gradient(180deg, #faf9ff, #f2f0ff) !important;
        color: #4b3f8f;
        font-weight: 800;
      }

      .table tbody tr {
        transition: background .15s ease;
      }

      .table tbody tr:hover {
        background: rgba(168,139,255,.06);
      }

      .table td, .table th {
        vertical-align: middle;
      }

      .status-text {
        font-weight: 700;
        letter-spacing: .2px;
      }

      .btn {
        border-radius: 12px !important;
        font-weight: 700;
        padding: 8px 12px;
        transition: transform .12s ease, box-shadow .12s ease;
      }

      .btn:hover, .btn:focus {
        transform: translateY(-1px);
      }

      .btn-success {
        background-image: linear-gradient(135deg, #16c08d, #10b981);
        border: 0;
        box-shadow: 0 10px 24px rgba(16,185,129,.22);
      }

      .btn-danger {
        background-image: linear-gradient(135deg, #ff6b6b, #ef4444);
        border: 0;
        box-shadow: 0 10px 24px rgba(239,68,68,.22);
      }

      .btn-primary {
        background-image: linear-gradient(135deg, var(--tf-primary), var(--tf-primary-2));
        border: 0;
        box-shadow: 0 10px 24px rgba(124,108,255,.24);
      }

      .btn-secondary {
        background: #fff !important;
        color: var(--tf-primary) !important;
        border: 1px solid var(--tf-border) !important;
        box-shadow: 0 10px 22px rgba(0,0,0,.06);
      }

      form.d-inline button[type="submit"] {
        margin-left: 6px;
      }

      ::selection {
        background: var(--tf-primary);
        color: #fff;
      }

      :focus-visible {
        outline: 3px solid rgba(124,108,255,.32);
        outline-offset: 3px;
        border-radius: 10px;
      }

      @media (max-width: 768px) {
        .container.mt-5 {
          padding: 0 14px;
        }
        .table {
          font-size: .95rem;
        }
      }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

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
                            <c:if test="${req.status == 'APPROVED'}">
                                <button class="btn btn-primary btn-sm assign-btn"
                                        data-trade="${req.request_id}"
                                        data-buyer="${req.buyer_id}">
                                    숙제 보내기
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <a href="<c:url value='/mypage' />" class="btn btn-secondary mt-3">🔙 마이페이지로 돌아가기</a>
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
    window.open(url, "assignTodo", "width=520,height=560,scrollbars=yes,resizable=yes");
});
</script>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
