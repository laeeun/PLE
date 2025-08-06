<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>아이디 찾기 결과</title>
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

  <style>
    :root {
      --accent: #FF6B35;
      --accent-light: #FFEDE4;
      --surface: #ffffff;
      --surface-alt: #f9f9f9;
      --text-main: #1F2C40;
      --text-sub: #6B7280;
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
      min-height: calc(100vh - 80px); /* nav 높이 고려 */
      padding-top: 40px;
    }

    .result-box {
      background: var(--surface);
      padding: 32px;
      border-radius: 14px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.06);
      text-align: center;
      width: 100%;
      max-width: 400px;
    }

    .result-box h2 {
      font-size: 26px;
      font-weight: 800;
      color: var(--text-main);
      margin-bottom: 12px;
    }

    .result-box p {
      margin: 10px 0;
      font-size: 16px;
      color: var(--text-sub);
    }

    .found-id {
      font-size: 22px;
      font-weight: bold;
      color: var(--text-main);
      margin-top: 12px;
    }

    .join-date {
      font-size: 14px;
      color: #999;
    }

    .btn-wrap {
      margin-top: 30px;
      display: flex;
      justify-content: center;
      gap: 12px;
    }

    .btn-wrap a {
      padding: 12px 20px;
      border-radius: 8px;
      text-decoration: none;
      font-size: 15px;
      font-weight: bold;
    }

    .btn-login {
      background: var(--accent);
      color: white;
    }

    .btn-pw {
      background: #ddd;
      color: #444;
    }
  </style>
</head>
<body>

  <!-- ✅ 네비게이션 최상단 고정 -->
  <jsp:include page="/WEB-INF/views/nav.jsp" />

  <!-- ✅ 중앙 컨텐츠 -->
  <section>
    <div class="result-box">
      <h2>아이디 찾기 결과</h2>
      <p>입력하신 정보와 일치하는 계정이 확인되었습니다.</p>
      <p class="found-id">${foundId}</p>
      <p class="join-date">가입일: ${joinDate}</p>

      <div class="btn-wrap">
        <a href="${pageContext.request.contextPath}/login" class="btn-login">로그인하러 가기</a>
        <a href="${pageContext.request.contextPath}/findPw" class="btn-pw">비밀번호 찾기</a>
      </div>
    </div>
  </section>

</body>
</html>
