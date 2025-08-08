<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>시간 충전소</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }

        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            color: var(--text-main);
        }

        h2 {
            font-weight: 900;
            font-size: 28px;
            margin-bottom: 24px;
            color: var(--primary);
        }

        .glass-card {
            background: var(--surface-alt);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 24px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
        }

        .btn-primary {
            background-color: var(--accent);
            border: none;
            font-weight: 600;
            padding: 10px 24px;
            border-radius: 12px;
            transition: background-color 0.2s, transform 0.2s;
        }

        .btn-primary:hover {
            background-color: #ff5a1f;
            transform: translateY(-1px);
        }

        .btn-secondary {
            background-color: var(--surface-alt);
            color: var(--primary);
            border: 1px solid var(--border);
            font-weight: 600;
            padding: 10px 24px;
            border-radius: 12px;
            transition: background-color 0.2s;
        }

        .btn-secondary:hover {
            background-color: var(--accent-100);
        }

        .form-check-label {
            font-weight: 500;
            color: var(--text-main);
        }

        .alert {
            border-radius: 12px;
            font-weight: 500;
        }

        #account-info strong {
            color: var(--accent);
            font-size: 18px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <h2><i class="fa-solid fa-clock-rotate-left"></i> 시간 충전소</h2>

    <!-- ✅ 현재 잔액 -->
    <div class="alert alert-info mt-3 glass-card" id="account-info">
        현재 잔액: <strong id="currentAccount">${sessionScope.loggedInUser.account}</strong> 분
    </div>

    <!-- ✅ 메시지 영역 -->
    <div id="messageArea" class="mt-3"></div>

    <!-- ✅ 충전 폼 -->
    <form id="chargeForm" class="mt-4 glass-card">
        <div class="mb-3">
            <label class="form-label fw-bold">충전할 시간 선택 (30분 단위)</label><br>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge30" value="30" required>
                <label class="form-check-label" for="charge30">30분</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge60" value="60">
                <label class="form-check-label" for="charge60">1시간</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge90" value="90">
                <label class="form-check-label" for="charge90">1시간 30분</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge120" value="120">
                <label class="form-check-label" for="charge120">2시간</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">충전하기</button>
        <a href="<c:url value='/' />" class="btn btn-secondary">홈으로</a>
    </form>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>

  $("#chargeForm").submit(function (e) {
    e.preventDefault();

    const amount = $("input[name='amount']:checked").val();
    if (!amount) {
      alert("충전할 시간을 선택해주세요.");
      return;
    }

    $.ajax({
      type: "POST",
      url: "<c:url value='/charge/ajax' />",
      data: { amount: amount },
      
      success: function (response) {
        if (response.success) {
          $("#messageArea").html(`<div class="alert alert-success glass-card">\${response.message}</div>`);
          $("#currentAccount").text(response.newAccount);
        } else {
          $("#messageArea").html(`<div class="alert alert-danger glass-card">\${response.message}</div>`);
        }

        document.getElementById('messageArea').scrollIntoView({ behavior: 'smooth', block: 'center' });
      },
      error: function () {
        $("#messageArea").html(`<div class="alert alert-danger glass-card">충전 중 오류가 발생했습니다.</div>`);
      }
    });
  });
</script>

</body>
</html>
