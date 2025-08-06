	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
	<html lang="ko">
	<head>
	    <meta charset="UTF-8">
	    <title>TimeFair - 네비게이션 바</title>
	
	    <!-- Pretendard + Montserrat -->
	    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
	    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet">
	    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />
	
	    <style>
	        /**** TimeFair 공통 테마 ****/
	        :root {
	            --primary:      #1F2C40;   /* 미드나잇 블루 */
	            --accent:       #FF6B35;   /* 밝은 오렌지 */
	            --accent-100:   #FFEEEA;
	            --surface:      #F9F9F9;
	            --surface-alt:  #FFFFFF;
	            --border:       #E8E8E8;
	            --text-main:    #1F2C40;
	            --text-sub:     #6A737D;
	        }
	
	        * { margin: 0; padding: 0; box-sizing: border-box; }
	        body {
	            font-family: 'Pretendard', sans-serif;
	            background: var(--surface);
	            color: var(--text-main);
	        }
	        a { text-decoration: none; color: inherit; }
	
	        /* === Navigation === */
	        nav {
	            position: sticky;
	            top: 0;
	            width: 100%;
	            z-index: 1000;
	            background: var(--surface-alt);
	            border-bottom: 1px solid var(--border);
	        }
	        .nav-inner {
	            max-width: 1180px;
	            margin: 0 auto;
	            padding: 18px 28px;
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	        }
	        .brand {
	            font-family: 'Montserrat', sans-serif;
	            font-weight: 900;
	            font-size: 24px;
	            color: var(--primary);
	            letter-spacing: -1px;
	        }
	        .nav-menu {
	            display: flex;
	            align-items: center;
	            gap: 30px;
	        }
	        .nav-link {
	            position: relative;
	            font-size: 16px;
	            font-weight: 600;
	            color: var(--text-main);
	            transition: color .2s;
	            cursor: pointer;
	        }
	        .nav-link::after {
	            content: "";
	            position: absolute;
	            left: 0;
	            bottom: -6px;
	            width: 0;
	            height: 2px;
	            background: var(--accent);
	            transition: width .2s;
	        }
	        .nav-link:hover { color: var(--accent); }
	        .nav-link:hover::after { width: 100%; }
	
	        /* 드롭다운 */
	        .dropdown { position: relative; }
	        .dropdown-box {
	            position: absolute;
	            top: 110%;
	            left: 0;
	            display: none;
	            background: var(--surface);
	            border: 1px solid var(--border);
	            border-radius: 12px;
	            min-width: 220px;
	            box-shadow: 0 8px 22px rgba(0,0,0,.08);
	        }
	        .dropdown-box a {
	            display: block;
	            padding: 12px 20px;
	            font-size: 14px;
	            transition: background-color 0.2s;
	        }
	        .dropdown-box a:hover { background: var(--accent-100); }
	        .dropdown:hover .dropdown-box { display: block; }
			.dropdown .nav-link {
			  display: inline-block; /* ✅ block 요소 피하기 위해 추가 */
			  padding: 6px 0;         /* ✅ 높이 정렬 맞춤 */
			}
			
	        /* 버튼 스타일 (마이페이지, 로그아웃 등) */
	        .btn {
	            padding: 6px 14px;
	            border: none;
	            border-radius: 12px;
	            font-size: 0.9rem;
	            font-weight: 600;
	            cursor: pointer;
	            transition: all 0.3s ease;
	        }
	        .btn-mypage {
	            background: var(--accent);
	            color: #fff;
	        }
	        .btn-mypage:hover { opacity: 0.9; }
	        .btn-logout {
	            background: #FFE2E2;
	            color: #8b1a1a;
	        }
	        .btn-logout:hover { background: #FF6B6B; color: #fff; }
	        .user-name { font-weight: 600; color: var(--accent); margin-right: 12px; }
	
	        @media(max-width: 768px){
	            .nav-menu { gap: 20px; }
	        }
	    </style>
	</head>
	<body>
	
	<nav>
		  <div class="nav-inner">
		    <!-- 브랜드 -->
		    <span class="brand">TimeFair</span>
		
		    <!-- 왼쪽 메뉴 그룹 -->
		    <div class="nav-menu">
		      <a class="nav-link" href="<c:url value='/' />">홈</a>
		      <a class="nav-link" href="<c:url value='/talent' />">시간 거래소</a>
		      <c:if test="${not empty sessionScope.loggedInUser}">
		        <a class="nav-link" href="<c:url value='/charge' />">시간 충전소</a>
		        <a class="nav-link" href="<c:url value='/favorite' />">나의 찜 목록</a>
		      </c:if>
		
		      <!-- 🔥 인기 카테고리 드롭다운: 왼쪽 메뉴 안에 위치 -->
		      <c:if test="${not empty top5CategoryRanking}">
				  <div class="dropdown">
				    <span class="nav-link">인기 카테고리 ▾</span>
				    <div class="dropdown-box">
				      <c:forEach var="entry" items="${top5CategoryRanking}" varStatus="loop">
				        <a href="<c:url value='/talent?category=${entry.key}' />">
				          ${loop.index + 1}위 - ${entry.key} (${entry.value}건)
				        </a>
				      </c:forEach>
				    </div>
				  </div>
				</c:if>
		    </div>
		
		    <!-- 오른쪽 사용자 메뉴 그룹 -->
		    <div class="nav-right-menu" style="display:flex;align-items:center;gap:14px;">
		      <c:choose>
		        <c:when test="${not empty sessionScope.loggedInUser}">
		          <span class="user-name">${sessionScope.loggedInUser.userName}님</span>
		          <button onclick="window.open('<c:url value="/notification/popup" />','알림 목록','width=500,height=600')"
		                  class="btn" style="background:#EEE; color:var(--primary);">
		            전체 알림
		            <c:if test="${unreadCount > 0}">
		              <span style="margin-left:6px; color:#FF6B35; font-weight:bold;">(${unreadCount})</span>
		            </c:if>
		          </button>
		          <a href="<c:url value='/logout' />" class="btn btn-logout">로그아웃</a>
		        </c:when>
		        <c:otherwise>
		          <a href="<c:url value='/login' />" class="btn" style="background:#EEE; color:var(--primary);">로그인</a>
		          <a href="<c:url value='/signUp' />" class="btn" style="background:#EEE; color:var(--primary);">회원가입</a>
		        </c:otherwise>
		      </c:choose>
		      <a href="<c:url value='/report' />" class="btn" style="background:#EEE; color:var(--primary);">신고목록</a>
		    </div>
		  </div>
		</nav>
	
	</body>
	</html>
