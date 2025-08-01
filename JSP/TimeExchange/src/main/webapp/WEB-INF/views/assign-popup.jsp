<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>숙제 보내기</title>
  <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body { padding: 16px; }
    .form-text { font-size: 12px; color: #6b7280; }
  </style>
</head>
<body>
  <h5 class="mb-3">숙제 보내기</h5>

  <!-- 서버에서 전달받은 값 표시 -->
  <div class="mb-3">
    <div class="form-text">거래번호</div>
    <div><strong>${tradeId}</strong></div>
  </div>
  <div class="mb-3">
    <div class="form-text">받는 사람(구매자)</div>
    <div><strong>${receiverId}</strong></div>
  </div>

  <form id="assignForm">
    <!-- hidden: 서버에서 모델로 내려받음 -->
    <input type="hidden" id="tradeId"    value="${tradeId}">
    <input type="hidden" id="receiverId" value="${receiverId}">
    <input type="hidden" id="writerId"   value="${writerId}"><!-- 판매자(현재 로그인 사용자) -->

    <div class="mb-3">
      <label class="form-label">제목</label>
      <input type="text" class="form-control" id="title" maxlength="100" placeholder="예) 1강 예습 문제 풀기" required>
    </div>

    <div class="mb-3">
      <label class="form-label">내용</label>
      <textarea class="form-control" id="content" rows="5" placeholder="상세 요구사항을 적어주세요."></textarea>
    </div>

    <!-- 마감일은 일 단위로 관리. 값 비우면 서버에서 오늘로 저장(COALESCE) -->
    <div class="mb-3">
      <label class="form-label">마감일(선택)</label>
      <input type="date" class="form-control" id="deadline">
      <div class="form-text">비워두면 오늘로 저장됩니다.</div>
    </div>

    <div class="d-flex gap-2">
      <button type="submit" class="btn btn-primary">전송</button>
      <button type="button" class="btn btn-secondary" onclick="window.close()">닫기</button>
    </div>
  </form>

  <script>
    // 기본값: 오늘(원하면 주석 해제)
    // document.getElementById('deadline').value = new Date().toISOString().slice(0,10);

    $('#assignForm').on('submit', function (e) {
      e.preventDefault();

      const payload = {
        tradeId:   $('#tradeId').val(),
        writerId:  $('#writerId').val(),
        receiverId:$('#receiverId').val(),
        title:     $('#title').val().trim(),
        content:   $('#content').val().trim(),
        deadline:  $('#deadline').val() || null,  // null -> 서버에서 CURDATE()
        isPersonal:false,                          // 숙제
        completed: false
      };

      if (!payload.title) {
        alert('제목을 입력하세요.');
        return;
      }

      $.ajax({
        url: "<c:url value='/todo/assign/submit'/>",
        method: "POST",
        contentType: "application/json",
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
  
  <jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
  
</body>
</html>
