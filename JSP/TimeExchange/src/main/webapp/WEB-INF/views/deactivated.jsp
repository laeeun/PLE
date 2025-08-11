<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>계정 탈퇴 완료 - TimeFair</title>

<!-- 폰트 -->
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap"
	rel="stylesheet" />

<!-- 아이콘 -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
	rel="stylesheet" />

<style>
:root {
	--primary: #1F2C40; /* 미드나잇 블루 */
	--accent: #FF6B35; /* 밝은 오렌지 */
	--accent-light: #FFEEEA;
	--surface: #F9F9F9;
	--surface-alt: #FFFFFF;
	--border: #E8E8E8;
	--text-main: #1F2C40;
	--text-sub: #6A737D;
}

/* 공통 레이아웃(전역 OK) */
body {
	font-family: 'Pretendard', sans-serif;
	background: var(--surface);
	color: var(--text-main);
	display: flex;
	flex-direction: column;
	min-height: 100vh;
	margin: 0;
	line-height: 1.6;
}

/* ====== 이 페이지 전용 스코프 ====== */
.deactivated-page .centered-content {
	flex-grow: 1;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	width: 100%;
	padding: 0 20px;
}

.deactivated-page .centered-content h1 {
	font-family: 'Montserrat', sans-serif;
	font-weight: 900;
	font-size: 36px;
	margin-bottom: 16px;
	color: var(--primary);
	text-align: center;
}

.deactivated-page .centered-content p {
	font-size: 18px;
	color: var(--text-sub);
	margin-bottom: 36px;
	text-align: center;
	max-width: 360px;
}

/* 폼/버튼 전역 오염 방지: 클래스 스코프 */
.deactivated-page .restore-form {
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 12px;
	width: 100%;
	max-width: 360px;
}

.deactivated-page .restore-form label {
	align-self: flex-start;
	font-weight: 600;
	margin-bottom: 6px;
	color: var(--primary);
}

/* 🔧 text + password 모두 동일 스타일 적용 */
.deactivated-page .restore-form input[type="text"],
.deactivated-page .restore-form input[type="password"] {
	width: 100%;
	padding: 12px 14px;
	border: 1px solid var(--border);
	border-radius: 12px;
	font-size: 16px;
	font-family: 'Pretendard', sans-serif;
	transition: border-color 0.25s, box-shadow 0.25s;
	background: #fff;
}

/* 포커스 효과도 동일 적용 */
.deactivated-page .restore-form input[type="text"]:focus,
.deactivated-page .restore-form input[type="password"]:focus {
	outline: none;
	border-color: var(--accent);
	box-shadow: 0 0 8px var(--accent-light);
}

.deactivated-page .cta-btn {
	width: 100%;
	padding: 16px 0;
	font-family: 'Montserrat', sans-serif;
	font-weight: 700;
	font-size: 18px;
	color: #fff;
	background-color: var(--accent);
	border: none;
	border-radius: 14px;
	cursor: pointer;
	box-shadow: 0 8px 24px rgba(255, 107, 53, 0.2);
	transition: background-color 0.25s, box-shadow 0.25s;
}

.deactivated-page .cta-btn:hover {
	background-color: #ff5b1c;
	box-shadow: 0 12px 30px rgba(255, 107, 53, 0.3);
}

.deactivated-page .alert-script {
	display: none;
}

@media (max-width: 480px) {
	.deactivated-page {
		padding: 30px 12px;
	}
	.deactivated-page .centered-content h1 {
		font-size: 28px;
	}
	.deactivated-page .centered-content p {
		font-size: 16px;
		max-width: 100%;
	}
	.deactivated-page .cta-btn {
		font-size: 16px;
	}
}
</style>

</head>

<body class="deactivated-page">

	<jsp:include page="/WEB-INF/views/nav.jsp" />

	<main class="centered-content">
		<h1>탈퇴한 회원입니다.</h1>
		<p>탈퇴를 취소하시겠습니까?</p>

		<c:if test="${not empty message}">
			<script class="alert-script">
				alert('${fn:escapeXml(message)}');
			</script>
		</c:if>

		<form class="restore-form"
			action="<c:url value='/mypage/deactivated/restore'/>" method="post">
			<c:choose>
				<c:when test="${not empty sessionScope.loggedInUser}">
					<input type="hidden" name="memberId"
						value="${sessionScope.loggedInUser.member_id}">
				</c:when>
				<c:when test="${not empty param.u}">
					<input type="hidden" name="memberId"
						value="${fn:escapeXml(param.u)}" />
				</c:when>
				<c:when test="${not empty param.memberId}">
					<input type="hidden" name="memberId" value="${param.memberId}" />
				</c:when>
				<c:otherwise>
					<label for="memberId">회원 아이디를 입력하세요:</label>
					<input type="text" id="memberId" name="memberId" required
						autocomplete="username" />

					<label for="password">비밀번호 확인</label>
					<input type="password" id="password" name="password" required
						autocomplete="current-password" />
				</c:otherwise>
			</c:choose>

			<button type="submit" class="cta-btn">탈퇴 취소하기</button>
		</form>
	</main>

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
