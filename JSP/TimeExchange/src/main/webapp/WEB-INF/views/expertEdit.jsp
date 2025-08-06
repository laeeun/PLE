<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>전문가 정보 수정</title>

<!-- 폰트 및 공통 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap"
	rel="stylesheet">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>"
	rel="stylesheet">

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
	--danger: #ef4444;
}

* {
	box-sizing: border-box;
}

body {
	font-family: 'Pretendard', sans-serif;
	background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
	background-size: 400% 400%;
	animation: gradientBG 15s ease infinite;
	color: var(--text-main);
	padding: 80px 20px;
	min-height: 100vh;
}

@
keyframes gradientBG { 0% {
	background-position: 0% 50%;
}

50
%
{
background-position
:
100%
50%;
}
100
%
{
background-position
:
0%
50%;
}
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
	font-size: 32px;
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

.form-container form {
	display: flex;
	flex-direction: column;
	gap: 16px;
}

.form-control, textarea {
	width: 100%;
	padding: 12px 14px;
	font-size: 15px;
	background: rgba(255, 255, 255, 0.7);
	border: 1px solid var(--border);
	border-radius: 12px;
	color: var(--text-main);
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

.btn-delete {
	background: var(--danger);
	color: white;
	border: none;
	border-radius: 6px;
	padding: 4px 10px;
	font-size: 13px;
	margin-left: 8px;
	cursor: pointer;
}

ul#fileList {
	list-style: none;
	padding-left: 0;
	margin-bottom: 10px;
}

ul#fileList li {
	margin-bottom: 8px;
	font-size: 14px;
	color: var(--text-sub);
}

/* 커스텀 파일 업로드 버튼 */
.file-upload-box {
	display: flex;
	flex-direction: column;
	gap: 6px;
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

@media ( max-width : 768px) {
	.form-container {
		padding: 30px 20px;
	}
}
</style>

</head>
<body>

	<div class="form-container">
		<h3>전문가 정보 수정</h3>

		<form action="<c:url value='/mypage/expert/update'/>" method="post"
			enctype="multipart/form-data">
			<label>경력 사항</label>
			<textarea name="career" rows="3" class="form-control">${expertDTO.career}</textarea>

			<label>대학교</label> <input type="text" name="university"
				class="form-control" value="${expertDTO.university}" /> <label>자격증
				및 기타</label> <input type="text" name="certification" class="form-control"
				value="${expertDTO.certification}" /> <label>자기소개</label>
			<textarea name="introduction" rows="4" class="form-control">${expertDTO.introduction}</textarea>

			<label>기존 업로드 파일 목록 (삭제)</label>
			<ul id="fileList">
				<c:forEach var="file" items="${expertDTO.fileNames}">
					<li id="file-${file.hashCode()}">${file}
						<button type="button" class="btn-delete"
							onclick="deleteFile('${file}', 'file-${file.hashCode()}')">삭제</button>
					</li>
				</c:forEach>
			</ul>

			<div class="file-upload-box">
			    <label>새 파일 추가 업로드</label>
			    <label for="file-upload" class="custom-file-upload">파일 선택</label>
			    <input id="file-upload" type="file" name="expertFiles" multiple />
			    <span id="file-name-text">선택된 파일 없음</span>
			  </div>

			<button type="submit" class="btn-submit">수정 완료</button>
		</form>
	</div>

	<script>
function deleteFile(fileName, elementId) {
  if (!confirm(fileName + " 파일을 삭제하시겠습니까?")) return;

  fetch("${pageContext.request.contextPath}/mypage/expert/deleteFile", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    body: 'fileName=' + encodeURIComponent(fileName)
  })
  .then(response => {
    if (response.ok) {
      document.getElementById(elementId).remove();
    } else {
      alert("삭제 실패");
    }
  })
  .catch(error => {
    console.error("삭제 중 오류 발생:", error);
    alert("서버 오류");
  });
}
</script>

	<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
</body>
</html>
