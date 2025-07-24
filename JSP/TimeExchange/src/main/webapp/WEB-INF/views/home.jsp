<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>TimeFair - 홈</title>
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
  <script src="https://unpkg.com/lucide@latest"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Pretendard', sans-serif;
    }

    body {
      height: 100vh;
      background: linear-gradient(to right, #f7f7fa, #f0f4ff);
      background-attachment: fixed;
      background-size: cover;
      overflow-x: hidden;
      display: flex;
      flex-direction: column;
      align-items: center;
      position: relative;
    }

    body::before {
      content: "";
      position: absolute;
      top: 10%;
      left: 5%;
      width: 100px;
      height: 100px;
      background: url('https://cdn-icons-png.flaticon.com/512/2088/2088617.png') no-repeat center/contain;
      opacity: 0.07;
      animation: float 6s ease-in-out infinite;
    }

    body::after {
      content: "";
      position: absolute;
      bottom: 10%;
      right: 5%;
      width: 120px;
      height: 120px;
      background: url('https://cdn-icons-png.flaticon.com/512/4395/4395696.png') no-repeat center/contain;
      opacity: 0.07;
      animation: floatReverse 7s ease-in-out infinite;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0); }
      50% { transform: translateY(-20px); }
    }

    @keyframes floatReverse {
      0%, 100% { transform: translateY(0); }
      50% { transform: translateY(20px); }
    }

    nav.navbar {
      width: 100%;
      background-color: rgba(255, 255, 255, 0.7);
      backdrop-filter: blur(6px);
      border-bottom: 1px solid rgba(0,0,0,0.05);
      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.04);
      position: sticky;
      top: 0;
      z-index: 1000;
    }

    .nav-inner {
      max-width: 1080px;
      margin: 0 auto;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 14px 20px;
    }

    .nav-link {
      font-size: 17px;
      color: #334155;
      font-weight: 500;
      text-decoration: none;
      margin: 0 12px;
    }

    .nav-link:hover {
      color: #000;
    }

    .dropdown {
      position: relative;
      display: inline-block;
    }

    .dropdown-content {
      display: none;
      position: absolute;
      background-color: white;
      min-width: 180px;
      box-shadow: 0 4px 10px rgba(0,0,0,0.1);
      z-index: 1;
      padding: 8px 0;
      border-radius: 8px;
    }

    .dropdown:hover .dropdown-content {
      display: block;
    }

    .dropdown-content a {
      display: block;
      padding: 10px 16px;
      text-decoration: none;
      color: #334155;
      font-size: 15px;
    }

    .dropdown-content a:hover {
      background-color: #f3f4f6;
    }

    .content-wrapper {
      background-color: rgba(255, 255, 255, 0.6);
      backdrop-filter: blur(16px);
      border-radius: 16px;
      margin-top: 80px;
      padding: 60px 30px;
      text-align: center;
      max-width: 720px;
      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
      animation: fadeIn 1.2s ease-out;
      position: relative;
      z-index: 10;
    }

    h1 {
      font-size: 42px;
      color: #1e293b;
      margin-bottom: 24px;
      animation: glowFade 2.5s ease-in-out infinite alternate;
    }

    @keyframes glowFade {
      0% { text-shadow: 0 0 0px rgba(167, 139, 250, 0); }
      100% { text-shadow: 0 0 12px rgba(167, 139, 250, 0.6); }
    }

    .slide-text {
      font-size: 20px;
      min-height: 30px;
      margin-top: 12px;
      height: 30px;
      position: relative;
      color: #475569;
    }

    .slide-text span {
      opacity: 0;
      position: absolute;
      left: 0;
      right: 0;
      transition: opacity 1s ease;
    }

    .slide-text span.active {
      opacity: 1;
    }

    .btn-wrap {
      margin-top: 40px;
    }

    .btn-login {
      padding: 14px 28px;
      background: linear-gradient(to right, #a78bfa, #c084fc);
      border: none;
      border-radius: 12px;
      font-size: 17px;
      font-weight: bold;
      color: #fff;
      cursor: pointer;
      box-shadow: 0 0 12px rgba(167, 139, 250, 0.6);
      transition: all 0.3s ease;
    }

    .btn-login:hover {
      transform: scale(1.05);
      box-shadow: 0 0 16px rgba(167, 139, 250, 0.7);
    }

    .quote {
      margin-top: 40px;
      font-size: 15px;
      color: #64748b;
      font-style: italic;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    hr {
      margin: 60px 0 20px;
      border: none;
      height: 1px;
      background-color: rgba(0,0,0,0.08);
      width: 80%;
    }
  </style>
</head>
<body>
  <nav class="navbar">
    <div class="nav-inner">
      <span style="font-size: 20px; font-weight: bold; color: #334155;">TimeFair</span>
      <div>
        <a class="nav-link" href="<c:url value='/' />">홈</a>
        <a class="nav-link" href="<c:url value='/talent' />">시간 거래소</a>
        <c:if test="${not empty sessionScope.loggedInUser}">
		        <a class="nav-link" href="<c:url value='/charge' />">시간 충전소</a>
		    </c:if>
        <div class="dropdown">
  			<p class="nav-link">랭킹 ▾</p>
  			<div class="dropdown-content">
    			<c:forEach var="entry" items="${top5CategoryRanking}" varStatus="status">
	      			<a href="<c:url value='/talent/${entry.key}' />">
	       			 ${status.index + 1}위 - ${entry.key} (${entry.value}건)
	      			</a>
    			</c:forEach>
		  </div>
		</div>
    </div>
    </div>
  </nav>

  <div class="content-wrapper">
    <h1>우리의 Time을 Change해요</h1>
    <div class="slide-text" id="slide-text">
      <span class="active">타임지갑으로 시간 적립</span>
      <span>전문가와 재능 거래</span>
      <span>나만의 리포트 카드 생성</span>
      <span>가족처럼 시간 나눔도 가능</span>
    </div>
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
    <div class="quote">
      "시간은 흘러가지만, 나누면 남는다."
    </div>
  </div>

  <hr>

	<div class="container" style="max-width: 1080px; padding: 40px 0;">
	  <h2 style="text-align:center; margin-bottom: 30px; color: #5b21b6;">전체 카테고리 보기</h2>
	  <div class="row" style="display: flex; flex-wrap: wrap; justify-content: center;">
	    <c:forEach var="entry" items="${categoryRankingMap}">
	      <div class="col-md-4 mb-4" style="padding: 12px;">
	        <div class="card text-center h-100" style="
	            border-radius: 16px;
	            background: rgba(216, 180, 254, 0.9); /* 진한 연보라 + 투명도 */
	            box-shadow: 0 8px 24px rgba(128, 90, 213, 0.2);
	            padding: 40px 20px;
	            backdrop-filter: blur(10px);
	            transition: all 0.3s ease;
	            animation: fadeIn 0.8s ease;
	        ">
	          <!-- 아이콘 -->
	          <div style="margin-bottom: 24px;">
	            <c:choose>
	              <c:when test="${entry.key == '디자인'}">
	                <i class="fas fa-paint-brush fa-3x" style="color: #ffffff;"></i>
	              </c:when>
	              <c:when test="${entry.key == '번역'}">
	                <i class="fas fa-language fa-3x" style="color: #ffffff;"></i>
	              </c:when>
	              <c:when test="${entry.key == '음악'}">
	                <i class="fas fa-music fa-3x" style="color: #ffffff;"></i>
	              </c:when>
	              <c:when test="${entry.key == '프로그래밍'}">
	                <i class="fas fa-code fa-3x" style="color: #ffffff;"></i>
	              </c:when>
	              <c:when test="${entry.key == '영상 편집'}">
	                <i class="fas fa-video fa-3x" style="color: #ffffff;"></i>
	              </c:when>
	              <c:otherwise>
	                <i class="fas fa-star fa-3x" style="color: #ffffff;"></i>
	              </c:otherwise>
	            </c:choose>
	          </div>
	
	          <!-- 카테고리명 -->
	          <h5 style="font-size: 20px; color: #ffffff; margin-bottom: 12px;">${entry.key}</h5>
	
	          <!-- 버튼 -->
	          <a href="<c:url value='/talent/${entry.key}' />" style="
	              font-size: 14px;
	              padding: 10px 22px;
	              border-radius: 8px;
	              background: rgba(255, 255, 255, 0.25);
	              color: #ffffff;
	              font-weight: 600;
	              text-decoration: none;
	              backdrop-filter: blur(4px);
	              box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
	              transition: all 0.3s ease;
	              display: inline-block;
	          " onmouseover="this.style.background='rgba(255,255,255,0.45)';"
	             onmouseout="this.style.background='rgba(255,255,255,0.25)';">
	            보러 가기 →
	          </a>
	        </div>
	      </div>
	    </c:forEach>
	  </div>
	</div>
	<h2 style="text-align: center; color: #5b21b6; margin-bottom: 10px;">
 	 우리 동네에 이런 재능이 있어요!
	</h2>
	<p style="text-align: center; color: #64748b; font-size: 15px; margin-bottom: 30px;">
	  같은 지역 사용자들이 등록한 따뜻한 재능들을 만나보세요.
	</p>
	<c:if test="${not empty localTalents}">
	  <div style="max-width: 1080px; margin: 80px auto 40px; padding: 0 20px;">
	    <h2 style="text-align: center; color: #5b21b6; margin-bottom: 30px;">내 동네 재능 모아보기</h2>
	    <div style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">
	      <c:forEach var="talent" items="${localTalents}">
	        <div style="
	          width: 280px;
	          padding: 20px;
	          background: rgba(255, 255, 255, 0.7);
	          backdrop-filter: blur(10px);
	          border-radius: 16px;
	          box-shadow: 0 6px 18px rgba(0, 0, 0, 0.06);
	          transition: all 0.3s ease;
	        ">
	          <h3 style="font-size: 18px; color: #4b5563; margin-bottom: 8px;">${talent.title}</h3>
	          <p style="font-size: 14px; color: #6b7280; margin-bottom: 12px;">
	            <c:choose>
	              <c:when test="${fn:length(talent.description) > 60}">
	                ${fn:substring(talent.description, 0, 60)}...
	              </c:when>
	              <c:otherwise>
	                ${talent.description}
	              </c:otherwise>
	            </c:choose>
	          </p>
	          <p style="font-size: 13px; color: #94a3b8;">작성자: ${talent.writerNickname}</p>
	          <a href="<c:url value='/talent/view?talentId=${talent.id}' />" style="
	            display: inline-block;
	            margin-top: 12px;
	            font-size: 13px;
	            color: #7c3aed;
	            font-weight: bold;
	            text-decoration: none;
	          ">자세히 보기 →</a>
	        </div>
	      </c:forEach>
	    </div>
	  </div>
	</c:if>

  <jsp:include page="/WEB-INF/views/footer.jsp" />

  <script>
    const texts = document.querySelectorAll('#slide-text span');
    let index = 0;
    setInterval(() => {
      texts[index].classList.remove('active');
      index = (index + 1) % texts.length;
      texts[index].classList.add('active');
    }, 3000);
    lucide.createIcons();
  </script>
</body>
</html>
