<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
  <style>
    footer {
      margin-top: 60px;
      padding: 24px 16px;
      text-align: center;
      font-family: 'Pretendard', sans-serif;
      font-size: 14px;
      color: #6b7280;
      background-color: #f9f9f9;
      border-top: 1px solid #e5e7eb;
    }
    .footer-links {
      margin-bottom: 8px;
    }
    .footer-links a {
      margin: 0 10px;
      color: #6b7280;
      text-decoration: none;
    }
    .footer-links a:hover {
      text-decoration: underline;
    }
    .footer-icon {
      font-size: 18px;
      color: #FF6B35;
      vertical-align: middle;
      margin-right: 6px;
    }
  </style>
</head>
<body>
  <footer>
    <div class="footer-links">
      <a href="<c:url value='/' />">홈</a> |
      <a href="#">이용약관</a> |
      <a href="#">개인정보처리방침</a>
    </div>
    <div>
      <span class="footer-icon">⏳</span> 
      &copy; 2025 시간 거래소 프로젝트. All rights reserved.
    </div>
  </footer>
</body>
</html>
