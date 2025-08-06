<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 변경</title>

  <!-- 감성 폰트 -->
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Pretendard', sans-serif;
      background-color: #f9fafb;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .card {
      background-color: #ffffff;
      border: 1px solid #e5e7eb;
      border-radius: 20px;
      padding: 40px;
      width: 420px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.05);
      animation: fadeIn 0.5s ease;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    h2 {
      font-weight: 700;
      text-align: center;
      color: #111827;
      margin-bottom: 24px;
    }

    label {
      font-weight: 600;
      font-size: 14px;
      margin-bottom: 6px;
      display: block;
      color: #374151;
    }

    input[type="password"] {
      width: 100%;
      padding: 10px 12px;
      border-radius: 10px;
      border: 1px solid #d1d5db;
      background-color: #fff;
      margin-bottom: 6px;
      font-size: 14px;
      box-sizing: border-box;
    }

    .form-text {
      font-size: 12px;
      color: #6b7280;
      margin-bottom: 14px;
      height: 14px;
    }

    .form-text.error {
      color: #ef4444;
    }

    .btn {
      width: 100%;
      padding: 12px;
      border-radius: 12px;
      font-weight: 600;
      font-size: 15px;
      cursor: pointer;
      border: none;
      transition: background 0.2s ease;
    }

    .btn-primary {
      background-color: #fb923c;
      color: white;
      margin-top: 8px;
    }

    .btn-primary:hover {
      background-color: #f97316;
    }

    .alert {
      text-align: center;
      font-size: 14px;
      margin-bottom: 15px;
    }

    .alert.error {
      color: #ef4444;
    }

    .alert.success {
      color: #10b981;
    }
  </style>
</head>
<body>

<div class="card">
  <h2>비밀번호 변경</h2>

  <!-- 에러 또는 성공 메시지 -->
  <c:if test="${not empty error}">
    <div class="alert error">${error}</div>
  </c:if>
  <c:if test="${not empty success}">
    <div class="alert success">${success}</div>
  </c:if>

  <form id="pwForm" action="${pageContext.request.contextPath}/mypage/changePw" method="post">
    <label for="currentPw">현재 비밀번호</label>
    <input type="password" name="currentPw" id="currentPw" required>

    <label for="newPw">새 비밀번호</label>
    <input type="password" name="newPw" id="newPw" required>
    <span id="pwCheckMsg" class="form-text"></span>

    <label for="pwConfirm">비밀번호 확인</label>
    <input type="password" name="pwConfirm" id="pwConfirm" required>
    <span id="pwMatchMsg" class="form-text"></span>

    <button type="submit" class="btn btn-primary">변경하기</button>
  </form>
</div>

<!-- ✅ 메시지 alert -->
<script>
  window.addEventListener("DOMContentLoaded", () => {
    <c:if test="${not empty error}">
      alert("${fn:escapeXml(error)}");
    </c:if>
    <c:if test="${not empty success}">
      alert("${fn:escapeXml(success)}");
    </c:if>
  });
</script>

<!-- ✅ 유효성 검사 JS -->
<script>
  const newPw = document.getElementById('newPw');
  const pwConfirm = document.getElementById('pwConfirm');
  const pwCheckMsg = document.getElementById('pwCheckMsg');
  const pwMatchMsg = document.getElementById('pwMatchMsg');
  const form = document.getElementById('pwForm');

  function validatePassword(pw) {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+]{8,20}$/;
    return regex.test(pw);
  }

  // 실시간 새 비밀번호 유효성 검사
  newPw.addEventListener('input', () => {
    if (!validatePassword(newPw.value)) {
      pwCheckMsg.textContent = '영문, 숫자 포함 8~20자';
      pwCheckMsg.classList.add('error');
    } else {
      pwCheckMsg.textContent = '';
      pwCheckMsg.classList.remove('error');
    }
  });

  // 실시간 비밀번호 확인 일치 검사
  pwConfirm.addEventListener('input', () => {
    if (newPw.value !== pwConfirm.value) {
      pwMatchMsg.textContent = '비밀번호가 일치하지 않아요';
      pwMatchMsg.classList.add('error');
    } else {
      pwMatchMsg.textContent = '';
      pwMatchMsg.classList.remove('error');
    }
  });

  // 제출 전 최종 유효성 검사
  form.addEventListener('submit', (e) => {
    if (!validatePassword(newPw.value)) {
      alert("새 비밀번호 형식이 올바르지 않습니다.");
      newPw.focus();
      e.preventDefault();
    } else if (newPw.value !== pwConfirm.value) {
      alert("비밀번호가 일치하지 않습니다.");
      pwConfirm.focus();
      e.preventDefault();
    }
  });
</script>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
</body>
</html>
