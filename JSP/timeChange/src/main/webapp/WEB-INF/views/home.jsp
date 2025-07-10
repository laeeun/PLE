<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>TimeFair - 홈</title>
  
  <!-- 감성 폰트 Pretendard -->
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
      overflow: hidden;
    }

    h1 {
      font-size: 62px;
      color: #ff69b4;
      margin-bottom: 30px;
      animation: fadeIn 1.5s ease-out;
    }

    .slide-text {
      font-size: 28px;
      color: #555;
      height: 30px;
      overflow: hidden;
      position: relative;
      min-width: 320px;
      text-align: center;
    }

    .slide-text span {
      opacity: 0;
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      transition: opacity 1s ease;
    }

    .slide-text span.active {
      opacity: 1;
    }

    .btn-wrap {
      margin-top: 50px;
    }

    .btn-login {
      padding: 12px 24px;
      background: #ff99cc;
      color: white;
      border: none;
      border-radius: 10px;
      font-size: 16px;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    .btn-login:hover {
      background: #ff66b2;
      transform: scale(1.05);
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-white border-bottom shadow-sm sticky-top">
    <div class="container d-flex justify-content-around">
        <a class="nav-link fw-bold text-dark" href="<c:url value='/' />">홈</a>
        <a class="nav-link fw-bold text-dark" href="<c:url value='/talent' />">시간 거래소</a>
        <a class="nav-link fw-bold text-dark" href="<c:url value='/expert' />">전문가</a>
        <a class="nav-link fw-bold text-dark" href="<c:url value='/ranking' />">랭킹</a>
 	</div>
 </nav>       		
  <!-- ⏰ 시계 이모티콘 포함 메인 타이틀 -->
  <h1>⏰ 우리의 시간을 교환해요!</h1>

  <!-- 슬라이딩 부제목 텍스트 -->
  <div class="slide-text" id="slide-text">
    <span class="active">타임지갑으로 시간 적립</span>
    <span>전문가와 재능 거래</span>
    <span>나만의 리포트 카드 생성</span>
    <span>가족처럼 시간 나눔도 가능</span>
  </div>

  <!-- 로그인 이동 버튼 -->
  <div class="btn-wrap">
	  <c:choose>
	    <c:when test="${not empty sessionScope.loggedInUser}">
	      <form action="${pageContext.request.contextPath}/talent" method="get">
	        <button class="btn-login">시간 거래소로 이동</button>
	      </form>
	    </c:when>
	    <c:otherwise>
	      <form action="${pageContext.request.contextPath}/login" method="get">
	        <button class="btn-login">로그인하러 가기</button>
	      </form>
	    </c:otherwise>
	  </c:choose>
	</div>
	<hr>
	<jsp:include page="/WEB-INF/views/footer.jsp" />
  <!-- 슬라이드 전환 스크립트 -->
  <script>
    const texts = document.querySelectorAll('#slide-text span');
    let index = 0;

    setInterval(() => {
      texts[index].classList.remove('active');
      index = (index + 1) % texts.length;
      texts[index].classList.add('active');
    }, 3000); // 4초마다 전환
  </script>

</body>
</html>
