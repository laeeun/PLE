<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>내 구매 요청 목록</title>

  <!-- Bootstrap (프로젝트에 이미 있으면 기존 경로 사용 가능) -->
  <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
  <!-- Pretendard 폰트 -->
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

  <style>
    /* ===== TimeFair Theme Tokens ===== */
    :root{
      --tf-primary:#7c6cff;
      --tf-primary-2:#a88bff;
      --tf-bg-1:#f7f5ff;
      --tf-text:#231f2b;
      --tf-border:rgba(124,108,255,.28);
      --tf-glass:rgba(255,255,255,.66);
      --tf-shadow:0 14px 36px rgba(124,108,255,.16);
      --tf-white:#ffffff;
      --tf-success:#10b981;
      --tf-danger:#ef4444;
      --tf-info:#3b82f6;
    }
    @media (prefers-color-scheme: dark){
      :root{
        --tf-bg-1:#0f0e13;
        --tf-text:#f5f4f8;
        --tf-glass:rgba(22,20,29,.62);
        --tf-white:#171523;
        --tf-border:rgba(168,139,255,.24);
        --tf-shadow:0 16px 40px rgba(168,139,255,.14);
      }
    }

    html,body{height:100%;}
    body{
      font-family:"Pretendard",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Apple SD Gothic Neo","Noto Sans KR","Malgun Gothic",sans-serif;
      color:var(--tf-text);
      background:
        radial-gradient(1200px 600px at 10% -10%, #efeaff 0%, transparent 60%),
        radial-gradient(1000px 500px at 110% 0%, #f5f0ff 0%, transparent 55%),
        linear-gradient(180deg, var(--tf-bg-1), #ffffff);
      background-attachment:fixed;
    }

    .container.mt-5{max-width:960px; margin-top:64px !important;}

    /* Title with inner underline */
    .container.mt-5 h2{
      font-weight:800; letter-spacing:-.2px;
      margin-bottom:22px; padding-bottom:12px;
      background:linear-gradient(90deg,var(--tf-primary),var(--tf-primary-2));
      -webkit-background-clip:text; background-clip:text; color:transparent;
      position:relative; display:inline-flex; align-items:center; gap:8px;
    }
    .container.mt-5 h2::after{
      content:""; position:absolute; left:0; bottom:0; height:3px; width:100%;
      background:linear-gradient(90deg,rgba(124,108,255,.35),rgba(168,139,255,0) 60%);
      border-radius:999px;
    }

    /* ===== Empty state as alert (icon + text, grid layout) ===== */
    .container.mt-5 .alert{
      display:grid !important;
      grid-template-columns:22px 1fr;
      align-items:start;
      column-gap:10px;
      padding:12px 16px !important;
      border-radius:18px;
      border:1px solid rgba(0,0,0,.06);
      background:linear-gradient(180deg,#eef3ff,#ffffff);
      box-shadow:var(--tf-shadow);
      color:#1e3a8a;
      line-height:1.35;
    }
    .container.mt-5 .alert::before{
      content:"i";
      grid-column:1; width:22px; height:22px; border-radius:50%;
      display:inline-flex; align-items:center; justify-content:center;
      background:#fff; box-shadow:0 4px 10px rgba(0,0,0,.07);
      font-weight:900; color:var(--tf-info);
    }

    /* ===== Table (glass card) ===== */
    table{
      width:100%;
      border-collapse:separate;
      border-spacing:0;
      overflow:hidden; border-radius:18px;
      background:
        linear-gradient(180deg, var(--tf-glass), var(--tf-glass)) padding-box,
        linear-gradient(135deg, rgba(124,108,255,.38), rgba(168,139,255,.26)) border-box;
      border:1px solid transparent;
      backdrop-filter:blur(10px); -webkit-backdrop-filter:blur(10px);
      box-shadow:var(--tf-shadow);
      font-size:0.98rem;
    }
    table thead th{
      background:linear-gradient(180deg,#faf9ff,#f2f0ff);
      color:#4b3f8f; font-weight:800; border-bottom:0;
      padding:12px 14px;
    }
    @media (prefers-color-scheme: dark){
      table thead th{ background:linear-gradient(180deg,#1f1b2f,#1a1826); color:#cfc9ff; }
    }
    table th, table td{
      padding:12px 14px; vertical-align:middle;
      border-bottom:1px solid rgba(0,0,0,.06);
    }
    table tr:hover{ background:rgba(168,139,255,.06); }

    /* Remove the legacy border="1" look by overriding */
    table[border]{
      border:0 !important;
    }

    /* Selection & focus */
    ::selection{background:var(--tf-primary); color:#fff;}
    :focus-visible{outline:3px solid rgba(124,108,255,.32); outline-offset:3px; border-radius:10px;}

    /* Responsive */
    @media (max-width: 768px){
      .container.mt-5{padding:0 14px;}
      table{font-size:.95rem;}
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
  <h2>내가 구매 요청한 재능 목록</h2>

  <c:choose>
    <c:when test="${not empty sentRequests}">
      <!-- 기존 table 구조 유지 (thead/tbody 없이도 스타일 적용) -->
      <table border="1">
        <tr>
          <th>재능 ID</th>
          <th>판매자</th>
          <th>요청 상태</th>
          <th>요청 시간</th>
        </tr>
        <c:forEach var="req" items="${sentRequests}">
          <tr>
            <td>${req.talent_id}</td>
            <td>${req.seller_id}</td>
            <td>${req.status}</td>
            <td>${req.requested_at}</td>
          </tr>
        </c:forEach>
      </table>
    </c:when>
    <c:otherwise>
      <!-- p 그대로 두되, CSS에서 alert처럼 보이도록 만들 수도 있지만
           같은 톤을 쓰기 위해 클래스만 살짝 추가 -->
      <p class="alert alert-info mb-0">요청한 재능이 없습니다.</p>
    </c:otherwise>
  </c:choose>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
