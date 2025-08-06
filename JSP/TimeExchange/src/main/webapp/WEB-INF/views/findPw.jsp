<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>비밀번호 찾기</title>
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

  <style>
    :root {
      --accent: #FF6B35;
      --accent-light: #FFEDE4;
      --surface: #ffffff;
      --surface-alt: #f9f9f9;
      --text-main: #1F2C40;
      --text-sub: #6B7280;
      --danger: #ef4444;
    }

    body {
      margin: 0;
      font-family: 'Pretendard', sans-serif;
      background-color: var(--surface-alt);
    }

    section {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: calc(100vh - 80px); /* nav 제외 */
      padding-top: 40px;
    }

    h2 {
      font-size: 26px;
      font-weight: 800;
      color: var(--text-main);
      margin-bottom: 24px;
    }

    form {
      background: var(--surface);
      padding: 30px;
      border-radius: 14px;
      box-shadow: 0 8px 20px rgba(0, 0, 0, 0.06);
      width: 100%;
      max-width: 400px;
      display: flex;
      flex-direction: column;
      gap: 18px;
    }

    input[type="text"],
    input[type="email"] {
      padding: 12px;
      border: 1px solid #ddd;
      border-radius: 8px;
      font-size: 16px;
      color: var(--text-main);
    }

    button {
      padding: 14px;
      background-color: var(--accent);
      color: white;
      font-size: 16px;
      font-weight: bold;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    button:hover {
      background-color: #e85c2c;
    }

    .error {
      color: var(--danger);
      font-size: 15px;
      margin-top: 16px;
      text-align: center;
    }
  </style>
</head>
<body>

  <!-- ✅ 네비게이션 상단 고정 -->
  <jsp:include page="/WEB-INF/views/nav.jsp" />

  <!-- ✅ 중앙 정렬된 컨텐츠 -->
  <section>
    <h2>비밀번호 찾기</h2>

    <form method="post" action="${pageContext.request.contextPath}/findPw">
      <input type="text" name="name" placeholder="이름" required />
      <input type="text" name="member_id" placeholder="아이디" required />
      <input type="email" name="email" placeholder="이메일" required />
      <button type="submit">비밀번호 찾기</button>
    </form>

    <!-- ✅ 에러 메시지 출력 -->
    <c:if test="${not empty error}">
      <p class="error">${error}</p>
    </c:if>
  </section>

</body>
</html>
