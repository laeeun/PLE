<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>네비게이션 바</title>

    <!-- Bootstrap + Pretendard -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
        }

        /* 네비 전체 배경 */
        .navbar-custom {
            background: rgba(255, 255, 255, 0.65);
            backdrop-filter: blur(12px);
            box-shadow: 0 8px 16px rgba(168, 85, 247, 0.08);
            border-bottom: 1px solid rgba(255, 255, 255, 0.3);
            margin-bottom: 24px;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        /* 메뉴 링크 */
        .navbar-custom .nav-link {
            font-weight: 600;
            color: #7e22ce;
            transition: color 0.3s ease;
        }

        .navbar-custom .nav-link:hover {
            color: #9333ea;
            text-decoration: underline;
        }

        /* 마이페이지 버튼 */
        .btn-mypage {
            background: linear-gradient(to right, #f3e8ff, #fbcfe8);
            color: #6b21a8;
            border: none;
            padding: 6px 14px;
            border-radius: 10px;
            font-size: 0.9rem;
            font-weight: 600;
            box-shadow: 0 3px 8px rgba(245, 183, 255, 0.3);
            transition: all 0.3s ease;
        }

        .btn-mypage:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(245, 183, 255, 0.5);
        }

        /* 로그아웃 버튼 */
        .btn-logout {
            background: linear-gradient(to right, #fde2e4, #ffc1cc);
            color: #8b1a1a;
            border: none;
            padding: 6px 14px;
            border-radius: 10px;
            font-size: 0.9rem;
            font-weight: 600;
            box-shadow: 0 3px 8px rgba(255, 200, 200, 0.3);
            transition: all 0.3s ease;
        }

        .btn-logout:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(255, 200, 200, 0.5);
        }

        /* 로그인 / 회원가입 */
        .btn-outline-primary.btn-sm,
        .btn-outline-secondary.btn-sm {
            border-radius: 10px;
            font-weight: 600;
            padding: 6px 14px;
            transition: all 0.3s ease;
        }

        .btn-outline-primary.btn-sm:hover {
            background-color: #a78bfa;
            border-color: #a78bfa;
            color: white;
        }

        .btn-outline-secondary.btn-sm:hover {
            background-color: #d8b4fe;
            border-color: #d8b4fe;
            color: white;
        }

        /* 유저 이름 */
        .user-name {
            font-weight: 600;
            color: #9333ea;
            margin-right: 12px;  /* ✅ 버튼과 간격 줌 */
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg sticky-top navbar-custom">
    <div class="container d-flex justify-content-between align-items-center">

        <!-- 왼쪽 메뉴 -->
        <div class="d-flex gap-3">
            <a class="nav-link" href="<c:url value='/' />">홈</a>
            <a class="nav-link" href="<c:url value='/talent' />">시간 거래소</a>            
            <a class="nav-link" href="<c:url value='/ranking' />">랭킹</a>
            <a class="nav-link" href="<c:url value='/charge' />">시간 충전소</a>
        </div>

        <!-- 오른쪽 로그인/회원가입 or 마이페이지/로그아웃 -->
      <div class="d-flex align-items-center">
          <c:choose>
              <c:when test="${not empty sessionScope.loggedInUser}">
                  <span class="user-name" style="margin-right: 16px;">${sessionScope.loggedInUser.userName}님</span>
                  <a href="<c:url value='/mypage' />" class="btn btn-mypage" style="margin-right: 12px;">마이페이지</a>
                  <a href="<c:url value='/logout' />" class="btn btn-logout">로그아웃</a>
              </c:when>
              <c:otherwise>
                  <a href="<c:url value='/login' />" class="btn btn-outline-primary btn-sm" style="margin-right: 12px;">로그인</a>
                  <a href="<c:url value='/signUp' />" class="btn btn-outline-secondary btn-sm">회원가입</a>
              </c:otherwise>
          </c:choose>
      </div>


    </div>
</nav>

</body>
</html>
