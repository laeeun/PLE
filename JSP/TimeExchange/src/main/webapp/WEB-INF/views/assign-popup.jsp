<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>숙제 보내기</title>

  <!-- Pretendard + jQuery -->
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Pretendard', sans-serif;
      background-color: #f9fafb; /* 밝은 흰색 배경 */
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    .card {
      background-color: #ffffff;
      border: 1px solid #e5e7eb;
      border-radius: 20px;
      padding: 40px;
      width: 420px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.05);
      animation: fadeIn 0.5s ease;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    h5 {
      font-weight: 700;
      text-align: center;
      color: #111827;
      margin-bottom: 24px;
    }

    label {
      font-weight: 600;
      font-size: 14px;
      margin-bottom: 6px;
      display: block;
      color: #374151;
    }

    .form-text {
      font-size: 12px;
      color: #6b7280;
      margin-top: 4px;
      margin-bottom: 16px;
    }

    input[type="text"],
    input[type="date"],
    textarea {
      width: 100%;
      padding: 10px 12px;
      border-radius: 10px;
      border: 1px solid #d1d5db;
      outline: none;
      background-color: #fff;
      margin-bottom: 16px;
      font-size: 14px;
    }

    textarea {
      resize: none;
    }

    .btn {
      width: 100%;
      padding: 12px;
      border-radius: 12px;
      font-weight: 600;
      font-size: 15px;
      cursor: pointer;
      border: none;
      transition: background 0.2s ease;
    }

    .btn-primary {
      background-color: #fb923c; /* 주황색 */
      color: white;
      margin-bottom: 12px;
    }

    .btn-primary:hover {
      background-color: #f97316;
    }

    .btn-secondary {
      background-color: #e5e7eb;
      color: #374151;
    }

    .btn-secondary:hover {
      background-color: #d1d5db;
    }

    .info-box {
      margin-bottom: 20px;
    }

    .info-label {
      font-size: 13px;
      color: #6b7280;
      margin-bottom: 4px;
    }

    .info-value {
      font-weight: bold;
      color: #1f2937;
    }
  </style>
</head>
<body>
<div class="card">
  <h1>숙제 보내기</h1>

  <!-- 거래 정보 -->
  <div class="info-box">
    <div class="info-label">거래번호</div>
    <div class="info-value">${tradeId}</div>
  </div>
  <div class="info-box">
    <div class="info-label">받는 사람(구매자)</div>
    <div class="info-value">${receiverId}</div>
  </div>

  <form id="assignForm">
    <!-- hidden 필드 -->
    <input type="hidden" id="tradeId"    value="${tradeId}">
    <input type="hidden" id="receiverId" value="${receiverId}">
    <input type="hidden" id="writerId"   value="${writerId}">

    <!-- 제목 -->
    <label for="title">제목</label>
    <input type="text" id="title" maxlength="100" placeholder="예) 1강 예습 문제 풀기" required>

    <!-- 내용 -->
    <label for="content">내용</label>
    <textarea id="content" rows="4" placeholder="상세 요구사항을 적어주세요."></textarea>

    <!-- 마감일 -->
    <label for="deadline">마감일(선택)</label>
    <input type="date" id="deadline">
    <div class="form-text">비워두면 오늘로 저장됩니다.</div>

    <!-- 전송/닫기 버튼 -->
    <button type="submit" class="btn btn-primary">전송</button>
    <button type="button" class="btn btn-secondary" onclick="window.close()">닫기</button>
  </form>
</div>

<script>
  $('#assignForm').on('submit', function (e) {
    e.preventDefault();

    const payload = {
      tradeId:    $('#tradeId').val(),
      writerId:   $('#writerId').val(),
      receiverId: $('#receiverId').val(),
      title:      $('#title').val().trim(),
      content:    $('#content').val().trim(),
      deadline:   $('#deadline').val() || null,
      isPersonal: false,
      completed: false
    };

    if (!payload.title) {
      alert('제목을 입력하세요.');
      return;
    }

    $.ajax({
      url: '<c:url value="/todo/assign/submit"/>',
      method: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(payload),
      success: function (data) {
        if (data && data.success) {
          alert('숙제를 전송했습니다.');
          if (window.opener && !window.opener.closed) {
            try { window.opener.location.reload(); } catch (e) {}
          }
          window.close();
        } else {
          alert('전송 실패: ' + (data && data.message ? data.message : '원인 미상'));
        }
      },
      error: function () {
        alert('서버 통신 오류가 발생했습니다.');
      }
    });
  });
</script>
</body>
</html>
