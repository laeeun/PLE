<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>시간 충전소</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>⏱ 시간 충전소</h2>

    <!-- ✅ 현재 잔액 -->
    <div class="alert alert-info mt-3" id="account-info">
        현재 잔액: <strong id="currentAccount">${sessionScope.loggedInUser.account}</strong> 분
    </div>

    <!-- ✅ 메시지 영역 -->
    <div id="messageArea"></div>

    <!-- ✅ 충전 폼 -->
    <form id="chargeForm" class="mt-4">
        <div class="mb-3">
            <label class="form-label">충전할 시간 선택 (30분 단위)</label><br>

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

<script>
  $("#chargeForm").submit(function (e) {
    e.preventDefault(); // 폼 기본 제출 막기

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
        // 메시지 출력
        if (response.success) {
          $("#messageArea").html(`<div class="alert alert-success">${response.message}</div>`);
          $("#currentAccount").text(response.newAccount);
        } else {
          $("#messageArea").html(`<div class="alert alert-danger">${response.message}</div>`);
        }
      },
      error: function () {
        $("#messageArea").html(`<div class="alert alert-danger">충전 중 오류가 발생했습니다.</div>`);
      }
    });
  });
</script>
</body>
</html>
