<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>회원 탈퇴 상태 – TimeFair</title>

<!-- 폰트 & 아이콘 -->
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap"
	rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
	rel="stylesheet" />
<script src="https://unpkg.com/lucide@latest"></script>

<style>
/* ---- 1. 색상 시스템 (홈 화면과 동일) ---- */
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

/* ---- 2. reset & 기본 ---- */
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Pretendard', sans-serif;
	color: var(--text-main);
	background: var(--surface);
	display: flex;
	flex-direction: column;
	min-height: 100vh;
}

a {
	color: inherit;
	text-decoration: none;
}

/* ---- 3. 중앙 카드 ---- */
.wrapper {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 60px 20px;
}

.card {
	width: 100%;
	max-width: 480px;
	background: var(--surface-alt);
	border: 1px solid var(--border);
	border-radius: 20px;
	padding: 48px 40px;
	text-align: center;
	box-shadow: 0 8px 22px rgba(0, 0, 0, .06);
}

.card h1 {
	font-family: 'Montserrat', sans-serif;
	font-size: 32px;
	font-weight: 900;
	margin-bottom: 12px;
	letter-spacing: -1px;
	color: var(--primary);
}

.card p {
	font-size: 16px;
	line-height: 1.7;
	color: var(--text-sub);
	margin-bottom: 38px;
}

/* ---- 4. 버튼 ---- */
.btn {
	display: inline-block;
	padding: 16px 38px;
	font-size: 15px;
	font-weight: 700;
	border: none;
	border-radius: 14px;
	cursor: pointer;
	transition: all .25s;
}

.btn-accent {
	color: #FFFFFF;
	background: var(--accent);
	box-shadow: 0 6px 18px rgba(255, 107, 53, .25);
}

.btn-accent:hover {
	transform: translateY(-3px);
	box-shadow: 0 10px 26px rgba(255, 107, 53, .28);
}

.btn-outline {
	color: var(--primary);
	background: transparent;
	border: 2px solid var(--border);
	margin-left: 12px;
}

.btn-outline:hover {
	background: var(--accent-100);
	border-color: var(--accent);
}

/* ---- 5. 반응형 ---- */
@media ( max-width :480px) {
	.card {
		padding: 32px 28px;
	}
	.card h1 {
		font-size: 26px;
	}
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/nav.jsp" />

	<div class="wrapper">
		<div class="card">
			<i class="fa-solid fa-circle-exclamation fa-2x"
				style="color: var(--accent); margin-bottom: 18px;"></i>
			<h1>탈퇴한 회원입니다</h1>
			<p>
				현재 계정 상태가 <strong>‘INACTIVE’</strong> 입니다.<br> 시간을 다시 나누고 싶다면
				탈퇴를 취소해 주세요!
			</p>

			<!-- ✅ 탈퇴 취소 / 계정 복원 -->
			<form action="${pageContext.request.contextPath}/member/reactivate"
				method="post" style="display: inline;">
				<input type="hidden" name="memberId" value="${param.memberId}" />
				<button type="submit" class="btn btn-accent">탈퇴 취소하기</button>
			</form>

			<!-- 홈으로 -->
			<a href="${pageContext.request.contextPath}/" class="btn btn-outline">메인으로
				돌아가기</a>
		</div>
	</div>


	<jsp:include page="/WEB-INF/views/footer.jsp" />

	<script>
		lucide.createIcons();
	</script>
</body>
</html>
