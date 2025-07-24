<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>아이디 찾기 결과</title>
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Pretendard', sans-serif;
      background: linear-gradient(to right, #ffe6f0, #fff0f5);
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      flex-direction: column;
    }

    .result-box {
      margin-top: 30px;
      background: white;
      padding: 30px;
      border-radius: 15px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
      text-align: center;
      width: 340px;
    }

    .result-box h2 {
      font-size: 28px;
      color: #ff69b4;
      margin-bottom: 20px;
    }

    .found-id {
      font-size: 22px;
      font-weight: bold;
      color: #333;
    }

    .join-date {
      font-size: 15px;
      color: #888;
      margin-top: 5px;
    }

    .btn-wrap {
      margin-top: 25px;
    }

    .btn-wrap a {
      display: inline-block;
      padding: 10px 20px;
      border-radius: 8px;
      text-decoration: none;
      font-size: 15px;
      margin: 0 5px;
    }

    .btn-login {
      background: #ff99cc; 
      color: white;
    }

    .btn-pw {
      background: #ddd;
      color: #444;
    }
  </style>
</head>
<body>
  <jsp:include page="/WEB-INF/views/nav.jsp" />

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
</body>
</html>
