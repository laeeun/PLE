<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>AS-Mate 홈</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <style>
    .category-card:hover { transform: translateY(-5px); box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
    .hero { background: url('https://via.placeholder.com/1200x400') center/cover no-repeat; color: white; height: 400px; position: relative; }
    .hero .overlay { position: absolute; inset: 0; background: rgba(0,0,0,0.4); }
  </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand fw-bold" href="#">AS‑Mate</a>
    <div class="collapse navbar-collapse justify-content-end">
      <ul class="navbar-nav">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/login">로그인</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/signup">회원가입</a></li>
      </ul>
    </div>
  </div>
</nav>

<section class="hero d-flex align-items-center">
  <div class="overlay"></div>
  <div class="container text-center position-relative">
    <h1 class="display-4 fw-bold">AS‑Mate와 함께라면 AS도 스마트하게</h1>
    <p class="lead mt-3">고장 접수, 진행 확인, 상담 요청까지 한 곳에서!</p>
    <a href="${pageContext.request.contextPath}/login" class="btn btn-lg btn-primary mt-4">서비스 시작하기</a>
  </div>
</section>

<section class="container py-5">
  <h2 class="mb-4 text-center">주요 서비스</h2>
  <div class="row g-4">
    <div class="col-md-3">
      <div class="card category-card h-100 text-center p-4">
        <i class="bi bi-gear-fill display-4 mb-3"></i>
        <h5>AS 접수하기</h5>
        <p>사진과 설명만으로 손쉽게 접수</p>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card category-card h-100 text-center p-4">
        <i class="bi bi-clock-history display-4 mb-3"></i>
        <h5>접수 내역보기</h5>
        <p>내 요청의 처리 상태를 실시간 확인</p>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card category-card h-100 text-center p-4">
        <i class="bi bi-question-circle-fill display-4 mb-3"></i>
        <h5>자주 묻는 질문</h5>
        <p>질문과 팁으로 궁금증 해결</p>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card category-card h-100 text-center p-4">
        <i class="bi bi-chat-dots-fill display-4 mb-3"></i>
        <h5>고객 상담</h5>
        <p>전문 상담 서비스 연결</p>
      </div>
    </div>
  </div>
</section>

<section class="bg-light py-5">
  <div class="container text-center">
    <h3>접수번호로 진행 상태 확인</h3>
    <div class="input-group mt-3 w-50 mx-auto">
      <input type="text" class="form-control" placeholder="접수번호 입력">
      <button class="btn btn-primary">조회하기</button>
    </div>
  </div>
</section>

<section class="container py-5">
  <h4 class="mb-4">공지사항</h4>
  <div class="list-group">
    <a href="#" class="list-group-item list-group-item-action">[2025-06-20] 시스템 점검 안내</a>
    <a href="#" class="list-group-item list-group-item-action">[2025-05-30] 여름 서비스 이벤트 공지</a>
    <a href="#" class="list-group-item list-group-item-action">[2025-04-10] 신규 기능 추가 안내</a>
  </div>
</section>

<footer class="bg-dark text-white py-4">
  <div class="container d-flex justify-content-between">
    <div>© 2025 AS‑Mate. All rights reserved.</div>
    <div>
      <a href="#" class="text-white me-3">이용약관</a>
      <a href="#" class="text-white me-3">개인정보처리방침</a>
      <a href="#" class="text-white">문의하기</a>
    </div>
  </div>
</footer>

<!-- 📌 부트스트랩 아이콘 CDN -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</body>
</html>
