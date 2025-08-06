<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>전문가 정보 입력</title>

  <!-- 폰트 & 스타일 -->
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet">
  <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">

  <style>
    :root {
      --primary: #1F2C40;
      --accent: #FF6B35;
      --accent-100: #FFEEEA;
      --surface: #F9F9F9;
      --surface-alt: #FFFFFF;
      --border: #E8E8E8;
      --text-main: #1F2C40;
      --text-sub: #6A737D;
    }

    * { box-sizing: border-box; }

    body {
      font-family: 'Pretendard', sans-serif;
      background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
      background-size: 400% 400%;
      animation: gradientBG 15s ease infinite;
      color: var(--text-main);
      padding: 80px 20px;
      min-height: 100vh;
    }

    @keyframes gradientBG {
      0% {background-position: 0% 50%;}
      50% {background-position: 100% 50%;}
      100% {background-position: 0% 50%;}
    }

    .form-container {
      background: rgba(255, 255, 255, 0.95);
      backdrop-filter: blur(12px);
      border-radius: 20px;
      max-width: 720px;
      margin: 0 auto;
      padding: 50px 40px;
      box-shadow: 0 12px 28px rgba(0, 0, 0, 0.08);
      border: 1px solid var(--border);
    }

    .form-container h3 {
      font-family: 'Montserrat', sans-serif;
      font-size: 30px;
      font-weight: 900;
      margin-bottom: 30px;
      text-align: center;
      color: var(--primary);
    }

    label {
      font-weight: 600;
      margin-bottom: 6px;
      color: var(--primary);
      display: block;
    }

    .form-control, textarea {
      width: 100%;
      padding: 12px 14px;
      font-size: 15px;
      background: rgba(255,255,255,0.7);
      border: 1px solid var(--border);
      border-radius: 12px;
      color: var(--text-main);
      margin-bottom: 16px;
      transition: all 0.2s;
    }

    .form-control:focus, textarea:focus {
      border-color: var(--accent);
      box-shadow: 0 0 0 3px rgba(255, 107, 53, 0.2);
      outline: none;
    }

    textarea {
      resize: vertical;
      min-height: 100px;
    }

    .btn-submit {
      background: var(--accent);
      color: white;
      font-weight: bold;
      padding: 14px 24px;
      border: none;
      border-radius: 14px;
      font-size: 16px;
      width: 100%;
      transition: transform 0.2s, box-shadow 0.2s;
      box-shadow: 0 8px 24px rgba(255, 107, 53, 0.25);
    }

    .btn-submit:hover {
      transform: translateY(-2px);
      box-shadow: 0 12px 30px rgba(255, 107, 53, 0.35);
    }

    /* 파일 업로드 꾸미기 */
    .file-upload-box {
      display: flex;
      flex-direction: column;
      gap: 6px;
      margin-bottom: 20px;
    }

    .custom-file-upload {
      display: inline-block;
      padding: 12px 20px;
      background-color: var(--accent);
      color: white;
      font-weight: bold;
      border-radius: 12px;
      cursor: pointer;
      transition: all 0.2s ease;
      font-size: 14px;
      text-align: center;
      width: fit-content;
    }

    .custom-file-upload:hover {
      background-color: #ff5a20;
      transform: translateY(-2px);
    }

    input[type="file"] {
      display: none;
    }

    #file-name-text {
      font-size: 14px;
      color: var(--text-sub);
    }

    @media (max-width: 768px) {
      .form-container {
        padding: 30px 20px;
      }
    }
  </style>
</head>
<body>

<%-- ✅ action 경로 분기 처리 유지 --%>
<c:choose>
  <c:when test="${mode eq 'edit'}">
    <c:set var="formAction" value='/mypage/expert/update' />
  </c:when>
  <c:when test="${fn:contains(pageContext.request.requestURI, '/signUp')}">
    <c:set var="formAction" value='/expertSubmit' />
  </c:when>
  <c:otherwise>
    <c:set var="formAction" value='/mypage/expert/submit' />
  </c:otherwise>
</c:choose>

<div class="form-container">
  <h3>전문가 추가 정보 입력</h3>

  <form action="${pageContext.request.contextPath}${formAction}" method="post" enctype="multipart/form-data">
    <label>경력 사항</label>
    <textarea name="career" class="form-control" placeholder="예: 5년간 디자인 프리랜서 경험">${expertProfile.career}</textarea>

    <label>대학교</label>
    <input type="text" name="university" class="form-control" value="${expertProfile.university}" placeholder="예: 한양대학교 디자인학부">

    <label>자격증 및 기타</label>
    <input type="text" name="certification" class="form-control" value="${expertProfile.certification}" placeholder="예: GTQ 1급, 정보처리기사 등">

    <label>자기소개</label>
    <textarea name="introduction" class="form-control" placeholder="자기소개">${expertProfile.introduction}</textarea>

    <label>관련 증빙 파일 첨부</label>
    <div class="file-upload-box">
      <label for="file-upload" class="custom-file-upload">파일 선택</label>
      <input id="file-upload" type="file" name="expertFiles" multiple />
      <span id="file-name-text">선택된 파일 없음</span>
    </div>

    <input type="hidden" name="memberId" value="${memberId}" />

    <button type="submit" class="btn-submit">제출하기</button>
  </form>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

<script>
  const fileInput = document.getElementById("file-upload");
  const fileText = document.getElementById("file-name-text");

  fileInput?.addEventListener("change", function () {
    if (fileInput.files.length === 0) {
      fileText.innerText = "선택된 파일 없음";
    } else if (fileInput.files.length === 1) {
      fileText.innerText = fileInput.files[0].name;
    } else {
      fileText.innerText = fileInput.files.length + "개의 파일 선택됨";
    }
  });
</script>

</body>
</html>
