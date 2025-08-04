<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>TimeFair - 홈</title>

  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />
  <script src="https://unpkg.com/lucide@latest"></script>

  <style>
    :root {
      --primary:      #1D4D4F;
      --accent:       #54D1B9;
      --accent-100:   #E4F8F4;
      --surface:      #FDFDFD;
      --surface-alt:  #FFFFFF;
      --border:       #E8E8E8;
      --text-main:    #1D4D4F;
      --text-sub:     #6D8485;
    }

    * { margin:0; padding:0; box-sizing:border-box; }
    body {
      font-family:'Pretendard', sans-serif;
      color:var(--text-main);
      background:var(--surface);
      min-height:100vh;
      display:flex;
      flex-direction:column;
      position:relative;
      line-height:1.6;
    }
    a { color:inherit; text-decoration:none; }
    section { padding:80px 0; }
    .container { max-width:1440px; margin:0 auto; padding:0 32px; }

    .section-title {
      font-size:36px; font-weight:900;
      font-family:'Montserrat', sans-serif;
      color:var(--primary);
      margin-bottom:20px;
      text-align:center;
    }

    .section-subtitle {
      font-size:18px;
      color:var(--text-sub);
      margin-bottom:50px;
      text-align:center;
    }

    nav {
      position:sticky; top:0; width:100%; z-index:1000;
      background:var(--surface-alt);
      border-bottom:1px solid var(--border);
    }
    .nav-inner {
     max-width: 1440px;
     margin: 0 auto;
     padding: 8px 28px; 
     display: flex;
     justify-content: space-between;
     align-items: center;
   }
    .brand img {
      height:48px;
      object-fit:contain;
    }
    .nav-menu { display:flex; align-items:center; gap:30px; }
    .nav-link {
      position:relative; font-size:16px; font-weight:600; color:var(--text-main);
      transition:color .2s;
    }
    .nav-link::after {
      content:""; position:absolute; left:0; bottom:-6px; width:0; height:2px;
      background:var(--accent); transition:width .2s;
    }
    .nav-link:hover { color:var(--accent); }
    .nav-link:hover::after { width:100%; }

    .dropdown { position:relative; }
    .dropdown-box {
      position:absolute; top:110%; right:0; display:none;
      background:var(--surface); border:1px solid var(--border);
      border-radius:12px; min-width:220px;
      box-shadow:0 8px 22px rgba(0,0,0,.08);
    }
    .dropdown-box a {
      display:block; padding:12px 20px; font-size:14px;
    }
    .dropdown-box a:hover {
      background:var(--accent-100);
    }
    .dropdown:hover .dropdown-box {
      display:block;
    }

    .hero {
      padding:120px 0;
      background: linear-gradient(135deg, var(--surface-alt), var(--accent-100));
      text-align: center;
    }
    .hero-inner {
      max-width:800px; margin:0 auto; padding:0 24px;
      display:flex; flex-direction:column; align-items:center;
    }
    .hero h1 {
      font-family:'Montserrat', sans-serif; font-weight:900;
      font-size:72px; line-height:1.1; color:var(--primary);
      letter-spacing:-3px; margin-bottom:20px;
    }
    .hero-text-and-cta {
      max-width:100%; text-align:center;
    }
    .hero-text-and-cta p {
      font-size:18px; color:var(--text-sub); margin:20px 0 40px;
    }
    .btn-cta {
      padding:18px 46px; font-size:17px; font-weight:700;
      border:none; border-radius:14px; cursor:pointer; color:#fff;
      background:var(--accent);
      box-shadow:0 8px 24px rgba(84,209,185, .2);
      transition:transform .25s, box-shadow .25s, background .25s;
    }
    .btn-cta:hover {
      transform:translateY(-3px);
      box-shadow:0 12px 30px rgba(84,209,185,.3);
      background: #47C4AA;
    }

    .category-grid {
      display:grid;
      grid-template-columns:repeat(auto-fit,minmax(180px,1fr));
      gap:20px;
      text-align:center;
    }
    .category-card {
      display:flex; flex-direction:column; align-items:center;
      padding:30px 20px;
      background:var(--surface-alt); border:1px solid var(--border);
      border-radius:16px; box-shadow:0 4px 12px rgba(0,0,0,.05);
      transition:all .25s;
    }
    .category-card:hover {
      transform:translateY(-5px);
      box-shadow:0 8px 24px rgba(0,0,0,.1);
      border-color:var(--accent);
    }
    .category-icon {
      width:48px; height:48px; color:var(--accent); margin-bottom:15px;
    }
    .category-card h4 {
      font-size:16px; font-weight:700; color:var(--primary); margin:0 0 5px;
    }
    .category-card small {
      font-size:12px; color:var(--text-sub);
    }

    .talent-grid {
      display:grid; grid-template-columns:repeat(auto-fit,minmax(300px,1fr));
      gap:28px;
    }
    .talent-card {
      padding:26px; background:var(--surface-alt); border:1px solid var(--border);
      border-radius:20px; box-shadow:0 6px 20px rgba(0,0,0,.05);
      transition:transform .2s, box-shadow .2s;
      display:flex; flex-direction:column;
    }
    .talent-card:hover {
      transform:translateY(-5px);
      box-shadow:0 10px 30px rgba(0,0,0,.1);
    }
    .talent-header {
      display:flex; align-items:center; margin-bottom:15px;
    }
    .talent-profile-img {
      width:40px; height:40px; border-radius:50%; margin-right:12px;
      border:2px solid var(--border); object-fit:cover;
    }
    .talent-header-info h3 {
      font-size:18px; font-weight:700; color:var(--primary); margin:0;
    }
    .talent-header-info p {
      font-size:13px; color:var(--text-sub); margin:0;
    }
    .talent-card-body p {
      font-size:14px; color:var(--text-sub); margin-bottom:12px; line-height:1.6;
    }
    .talent-card-footer {
      margin-top:auto; padding-top:15px; border-top:1px dashed var(--border);
      display:flex; justify-content:space-between; align-items:center;
    }
    .talent-category-tag {
      font-size:12px; font-weight:600; padding:5px 10px;
      background:var(--accent-100); color:var(--accent); border-radius:12px;
    }
    .talent-card-link {
      font-size:13px; font-weight:700; color:var(--primary); transition:color .2s;
    }
    .talent-card-link:hover { color:var(--accent); }

    @media(max-width:768px){
      .nav-menu{gap:20px;}
      .hero h1{font-size:40px;}
      .btn-cta{padding:16px 36px; font-size:16px;}
      .section-title{font-size:28px;}
      .section-subtitle{font-size:16px;}
      .category-grid, .talent-grid { grid-template-columns:1fr; }
    }
    
    .category-container {
     display: flex;
     flex-wrap: wrap;
     justify-content: center;
     gap: 16px;
     margin-top: 20px;
   }
   
   .category-link {
     display: inline-block;
     padding: 12px 24px;
     font-size: 15px;
     font-weight: 600;
     color: var(--primary);
     background: var(--accent-100);
     border-radius: 999px;
     border: 1px solid var(--accent);
     transition: all 0.2s;
     text-align: center;
   }
   
   .category-link:hover {
     background: var(--accent);
     color: white;
     transform: translateY(-2px);
   }
    
  </style>
</head>
<body>
  <nav>
    <div class="nav-inner">
      <a href="<c:url value='/' />" class="brand">
        <img src="<c:url value='/resources/images/Logo.png' />" alt="TimeFair 로고" style="height: 48px;">
      </a>
      <div class="nav-menu">
        <a class="nav-link" href="<c:url value='/' />">홈</a>
        <a class="nav-link" href="<c:url value='/talent' />">시간 거래소</a>
        <c:if test="${not empty sessionScope.loggedInUser}">
          <a class="nav-link" href="<c:url value='/charge' />">시간 충전소</a>
        </c:if>
        <div class="dropdown">
          <p class="nav-link" style="cursor:pointer;">랭킹 ▾</p>
          <div class="dropdown-box">
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

  <section class="hero">
    <div class="container hero-inner">
      <h1>우리의 시간을<br />가치있게 Change해요.</h1>
      <div class="hero-text-and-cta">
        <p>"시간은 흘러가지만, 나누면 남는다."<br />전문가와 재능을 거래하고, 나만의 리포트 카드를 생성해 보세요.</p>
        <c:choose>
          <c:when test="${not empty sessionScope.loggedInUser}">
            <form action="${pageContext.request.contextPath}/talent" method="get">
              <button class="btn-cta">시간 거래소로 이동</button>
            </form>
          </c:when>
          <c:otherwise>
            <form action="${pageContext.request.contextPath}/login" method="get">
              <button class="btn-cta">로그인하러 가기</button>
            </form>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </section>

  <section style="background-color: var(--surface);">
     <div class="container">
       <h2 class="section-title" style="text-align:center;">전체 카테고리</h2>
       <div class="category-container">
         <a href="<c:url value='/talent?category=프로그래밍' />" class="category-link">프로그래밍</a>
         <a href="<c:url value='/talent?category=디자인' />" class="category-link">디자인</a>
         <a href="<c:url value='/talent?category=번역' />" class="category-link">번역</a>
         <a href="<c:url value='/talent?category=음악' />" class="category-link">음악</a>
         <a href="<c:url value='/talent?category=영상편집' />" class="category-link">영상편집</a>
         <a href="<c:url value='/talent?category=글쓰기' />" class="category-link">글쓰기</a>
         <a href="<c:url value='/talent?category=과외' />" class="category-link">과외</a>
         <a href="<c:url value='/talent?category=생활서비스' />" class="category-link">생활서비스</a>
         <a href="<c:url value='/talent?category=기획창작' />" class="category-link">기획창작</a>
       </div>
     </div>
  </section>


  <section style="background-color: var(--surface-alt);">
    <div class="container">
      <h2 class="section-title">우리 동네에 이런 재능이 있어요!</h2>
      <p class="section-subtitle">같은 지역 사용자들이 등록한 따뜻한 재능들을 만나보세요.</p>
      <c:if test="${not empty localTalents}">
        <div class="talent-grid">
          <c:forEach var="talent" items="${localTalents}">
            <div class="talent-card">
              <div class="talent-header">
                <img src="https://via.placeholder.com/40" alt="프로필 이미지" class="talent-profile-img" />
                <div class="talent-header-info">
                  <h3>${talent.title}</h3>
                  <p>작성자: ${talent.username}</p>
                </div>
              </div>
              <div class="talent-card-body">
                <p>
                  <c:choose>
                    <c:when test="${fn:length(talent.description) > 80}">
                      ${fn:substring(talent.description,0,80)}...
                    </c:when>
                    <c:otherwise>${talent.description}</c:otherwise>
                  </c:choose>
                </p>
              </div>
              <div class="talent-card-footer">
                <span class="talent-category-tag">${talent.category}</span>
                <a href="<c:url value='/talent/view?id=${talent.talent_id}' />" class="talent-card-link">자세히 보기 →</a>
              </div>
            </div>
          </c:forEach>
        </div>
      </c:if>
    </div>
  </section>

  <jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
  <jsp:include page="/WEB-INF/views/footer.jsp" />

  <script>
    lucide.createIcons();
  </script>
</body>
</html>
