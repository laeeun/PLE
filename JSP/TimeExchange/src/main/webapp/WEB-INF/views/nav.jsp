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

        .navbar-custom {
            background: rgba(255, 255, 255, 0.65);
            backdrop-filter: blur(12px);
            box-shadow: 0 8px 16px rgba(168, 85, 247, 0.08);
            border-bottom: 1px solid rgba(255, 255, 255, 0.3);
            margin-bottom: 24px;
            padding-top: 10px;
            padding-bottom: 10px;
        }

        .navbar-custom .nav-link {
            font-weight: 600;
            color: #7e22ce;
            transition: color 0.3s ease;
        }

        .navbar-custom .nav-link:hover {
            color: #9333ea;
            text-decoration: underline;
        }

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
            position: relative;
        }

        .btn-mypage:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(245, 183, 255, 0.5);
        }

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

        .user-name {
            font-weight: 600;
            color: #9333ea;
            margin-right: 12px;
        }

        #notification-badge {
            font-size: 0.75rem;
            padding: 3px 7px;
            border-radius: 50%;
            vertical-align: top;
            margin-left: 5px;
        }

        .dropdown-menu {
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(6px);
            box-shadow: 0 8px 24px rgba(168, 85, 247, 0.15);
            border: 1px solid #f3e8ff;
        }

        .dropdown-item {
            color: #7e22ce;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        .dropdown-item:hover {
            background-color: #f3e8ff;
            color: #6b21a8;
        }

        .dropdown-toggle {
            font-weight: 600;
            border-radius: 10px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg sticky-top navbar-custom">
    <div class="container d-flex justify-content-between align-items-center">

        <!-- 왼쪽 메뉴 -->
        <div class="d-flex gap-3 align-items-center">
            <a class="nav-link" href="<c:url value='/' />">홈</a>
            <a class="nav-link" href="<c:url value='/talent' />">시간 거래소</a>
            <c:if test="${not empty sessionScope.loggedInUser}">
                <a class="nav-link" href="<c:url value='/charge' />">시간 충전소</a>
                <a class="nav-link" href="<c:url value='/todo' />">TODO 리스트</a>
                <a class="nav-link" href="<c:url value='/favorite' />">나의 찜 목록</a>
                
            </c:if>

            <!-- 🔥 인기 카테고리 드롭다운 -->
            <c:if test="${not empty top5CategoryRanking}">
                <div class="dropdown ms-2">
                    <button class="btn btn-outline-primary dropdown-toggle" type="button"
                            id="rankingDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                        🔥 인기 카테고리
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="rankingDropdown">
                        <c:forEach var="entry" items="${top5CategoryRanking}" varStatus="loop">
                            <li>
                                <a class="dropdown-item" href="<c:url value='/talent/${entry.key}' />">
                                    ${loop.index + 1}위 - ${entry.key} (${entry.value}건)
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </div>

        <!-- 오른쪽 메뉴 -->
        <div class="d-flex align-items-center">
            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
	                
                    <span class="user-name">${sessionScope.loggedInUser.userName}님</span>
                    <a href="<c:url value='/mypage' />" class="btn btn-mypage me-2">
                        마이페이지
                    </a>
                    <button onclick="window.open('<c:url value="/notification/popup" />', '알림 목록', 'width=500,height=600')"
				          class="btn btn-outline-secondary btn-sm position-relative">
				  		  전체 알림 보기
					    <c:if test="${unreadCount > 0}">
					      <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
					        ${unreadCount}		        
					      </span>
					    </c:if>
				  	</button>
                    <a href="<c:url value='/logout' />" class="btn btn-logout">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/login' />" class="btn btn-outline-primary btn-sm me-2">로그인</a>
                    <a href="<c:url value='/signUp' />" class="btn btn-outline-secondary btn-sm">회원가입</a>
                </c:otherwise>
            </c:choose>
            <a href="<c:url value='/report' />" class="btn btn-outline-secondary btn-sm">신고목록</a>
        </div>

    </div>
</nav>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
