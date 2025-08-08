<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Login</title>

<!-- 폰트 & 아이콘 -->
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap"
	rel="stylesheet" />
<script src="https://unpkg.com/lucide@latest"></script>

<style>
/* ---------- 1. Color System (home.jsp와 동일) ---------- */
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

/* ---------- 2. Base ---------- */
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

body {
	font-family: 'Pretendard', sans-serif;
	background: var(--surface);
	color: var(--text-main);
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
}

a {
	color: inherit;
	text-decoration: none;
}

/* ---------- 3. Login Card ---------- */
.login-box {
	width: 360px;
	padding: 40px 30px 30px;
	background: var(--surface-alt);
	border: 1px solid var(--border);
	border-radius: 20px;
	box-shadow: 0 6px 20px rgba(0, 0, 0, .05);
	text-align: center;
}

/* 로고 원형 */
.logo-circle {
	width: 70px;
	height: 70px;
	background: var(--accent-100);
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 0 auto 30px;
}

.logo-circle i {
	width: 36px;
	height: 36px;
	color: var(--accent);
}

/* ---------- 4. Input ---------- */
.input-group {
	margin-bottom: 18px;
	position: relative;
}

.input-group input {
	width: 100%;
	padding: 12px 14px;
	font-size: 15px;
	background: var(--surface);
	border: 1px solid var(--border);
	border-radius: 14px;
	color: var(--text-main);
	outline: none;
	transition: border .2s;
}

.input-group input::placeholder {
	color: var(--text-sub);
}

.input-group input:focus {
	border-color: var(--accent);
}

/* ---------- 5. Button ---------- */
.login-btn {
	width: 100%;
	padding: 12px;
	background: var(--accent);
	border: none;
	border-radius: 14px;
	font-size: 16px;
	font-weight: 700;
	color: #fff;
	cursor: pointer;
	transition: transform .25s, box-shadow .25s;
	box-shadow: 0 8px 24px rgba(255, 107, 53, .2);
}

.login-btn:hover {
	transform: translateY(-3px);
	box-shadow: 0 12px 30px rgba(255, 107, 53, .3);
}

/* ---------- 6. Links & Error ---------- */
.links {
	margin-top: 26px;
	font-size: 13px;
	color: var(--text-sub);
}

.links a {
	margin: 0 6px;
}

.links a:hover {
	text-decoration: underline;
}

.error {
	color: #ef4444;
	font-size: 0.9em;
	margin-top: 14px;
}
</style>
</head>
<body>

	<div class="login-box">
		<div class="logo-circle">
			<i data-lucide="user"></i>
		</div>

		<!-- ✅ 기능 로직 그대로 유지 -->
		<form action="${pageContext.request.contextPath}/loginProcess"
			method="post">
			<div class="input-group">
				<input type="text" name="username" placeholder="아이디" required />
			</div>

			<div class="input-group">
				<input type="password" name="password" placeholder="비밀번호" required />
			</div>

			<c:if test="${showCaptcha}">
				<div class="input-group">
					<p
						style="font-size: 13px; color: var(--text-main); text-align: left;">
						보안문자를 입력해주세요: <strong>${captcha}</strong>
					</p>
					<input type="text" name="captchaInput" placeholder="보안문자 입력"
						required />
				</div>
			</c:if>

			<c:if test="${pwResetSuccess}">
				<script>
					alert("임시 비밀번호가 이메일로 발송되었습니다. 메일을 확인해주세요.");
				</script>
			</c:if>

			<button type="submit" class="login-btn">LOGIN</button>

			<c:if test="${not empty message}">
				<script>
					alert('${fn:escapeXml(message)}');
				</script>
			</c:if>
		</form>

		<div class="links">
			<a href="${pageContext.request.contextPath}/findId">아이디 찾기</a> | <a
				href="${pageContext.request.contextPath}/findPw">비밀번호 찾기</a> | <a
				href="${pageContext.request.contextPath}/signUp">회원가입</a> | <a
				href="${pageContext.request.contextPath}/">홈</a>
		</div>
	</div>

	<!-- Lucide 아이콘 -->
	<script>
		lucide.createIcons();
	</script>
</body>
</html>
