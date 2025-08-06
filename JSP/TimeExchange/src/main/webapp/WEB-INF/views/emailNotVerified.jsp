<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>이메일 인증 필요</title>

  <!-- 기본 폰트 및 아이콘 -->
  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />

  <style>
    :root {
      --primary:      #1F2C40;
      --accent:       #FF6B35;
      --accent-100:   #FFEEEA;
      --surface:      #F9F9F9;
      --surface-alt:  #FFFFFF;
      --border:       #E8E8E8;
      --text-main:    #1F2C40;
      --text-sub:     #6A737D;
      --danger:       #ef4444;
      --success:      #10b981;
    }

    body {
      margin: 0;
      font-family: 'Pretendard', sans-serif;
      background: var(--surface);
      display: flex;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      padding: 40px 20px;
    }

    .card {
      background: var(--surface-alt);
      border: 1px solid var(--border);
      border-radius: 18px;
      box-shadow: 0 10px 30px rgba(0, 0, 0, 0.06);
      max-width: 500px;
      width: 100%;
      padding: 40px 35px;
      text-align: center;
    }

    .card h2 {
      font-family: 'Montserrat', sans-serif;
      font-size: 30px;
      font-weight: 900;
      color: var(--primary);
      margin-bottom: 18px;
    }

    .card p {
      font-size: 15px;
      color: var(--text-sub);
      margin-bottom: 30px;
      line-height: 1.6;
    }

    .btn-orange {
      display: block;
      width: 100%;
      padding: 14px;
      font-size: 16px;
      font-weight: bold;
      background: var(--accent);
      color: white;
      border: none;
      border-radius: 12px;
      cursor: pointer;
      margin-bottom: 14px;
      transition: all 0.25s ease;
      box-shadow: 0 8px 22px rgba(255, 107, 53, 0.15);
    }

    .btn-orange:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 30px rgba(255, 107, 53, 0.3);
    }

    .btn-home {
      display: block;
      text-align: center;
      font-size: 15px;
      font-weight: 600;
      color: var(--primary);
      border: 1px solid var(--border);
      padding: 12px;
      border-radius: 10px;
      text-decoration: none;
      background: var(--surface);
      transition: background 0.2s, box-shadow 0.2s;
    }

    .btn-home:hover {
      background: var(--accent-100);
      box-shadow: 0 4px 12px rgba(0,0,0,0.06);
    }

    .icon {
      font-size: 48px;
      color: var(--danger);
      margin-bottom: 15px;
    }

  </style>
</head>
<body>

<div class="card">
  <div class="icon">🚫</div>
  <h2>이메일 인증이 필요해요</h2>
  <p>로그인을 완료하려면<br>이메일 인증을 먼저 진행해 주세요.</p>

  <!-- 인증 메일 재전송 폼 -->
  <form action="<c:url value='/mail/resend' />" method="post">
    <input type="hidden" name="member_id" value="${member.member_id}" />
    <button type="submit" class="btn-orange">인증 메일 다시 보내기</button>
  </form>

  <!-- 홈으로 이동 버튼 -->
  <a href="<c:url value='/' />" class="btn-home">🏠 홈으로 돌아가기</a>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
