<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내가 팔로우한 사람들</title>
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet">
<style>
:root {
	--accent: #FF6B35;
	--accent-light: #FFEDE4;
	--surface: #ffffff;
	--surface-alt: #f9f9f9;
	--text-main: #1F2C40;
	--text-sub: #6B7280;
}

body {
	margin: 0;
	font-family: 'Pretendard', sans-serif;
	background: var(--surface-alt);
	padding: 60px 20px 40px;
	color: var(--text-main);
}

h2 {
	font-size: 24px;
	font-weight: 800;
	margin-bottom: 30px;
	color: var(--text-main);
	text-align: center;
}

.user-card {
	display: flex;
	align-items: center;
	justify-content: space-between;
	background: var(--surface);
	padding: 1rem 1.5rem;
	margin: 0 auto 1.2rem;
	border-radius: 12px;
	max-width: 600px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.06);
}

.user-info {
	display: flex;
	align-items: center;
	gap: 1rem;
}

.user-card img {
	width: 48px;
	height: 48px;
	border-radius: 50%;
	object-fit: cover;
}

.user-name {
	font-size: 1.1rem;
	color: var(--accent);
	text-decoration: none;
	font-weight: 600;
}

.user-name:hover {
	text-decoration: underline;
}

.follow-btn {
	padding: 6px 12px;
	font-size: 14px;
	font-weight: 600;
	border: 1px solid var(--accent);
	color: var(--accent);
	background-color: transparent;
	border-radius: 6px;
	transition: 0.3s;
	cursor: pointer;
}

.follow-btn:hover {
	background-color: var(--accent);
	color: white;
}

.no-follow {
	text-align: center;
	font-size: 16px;
	font-weight: 500;
	color: var(--text-sub);
	margin-top: 50px;
}

.back-button {
	display: block;
	width: fit-content;
	margin: 40px auto 0;
	padding: 10px 20px;
	background-color: transparent;
	border: 2px solid var(--accent);
	color: var(--accent);
	border-radius: 30px;
	font-size: 14px;
	font-weight: 600;
	text-decoration: none;
	transition: all 0.3s ease;
}

.back-button:hover {
	background-color: var(--accent-light);
	text-decoration: none;
}

/* ✅ 주황색 버튼 공통 */
.btn-outline-accent {
	border: 1.5px solid var(--accent);
	color: var(--accent);
	background-color: transparent;
}

.btn-outline-accent:hover {
	background-color: var(--accent-light);
}
</style>
</head>
<body>

	<h2>💗 내가 팔로우한 사람들</h2>

	<c:choose>
		<c:when test="${not empty followingList}">
			<c:forEach var="user" items="${followingList}">
				<div class="user-card">
					<div class="user-info">
						<c:choose>
							<c:when test="${not empty user.profileImage}">
								<img
									src="<c:url value='/upload/profile/${user.profileImage}' />"
									alt="프로필" />
							</c:when>
							<c:otherwise>
								<img
									src="<c:url value='/resources/images/default-profile.png' />"
									alt="기본 프로필" />
							</c:otherwise>
						</c:choose>
						<a class="user-name"
							href="${pageContext.request.contextPath}/profile/${user.following_id}">
							${user.userName} </a>
					</div>

					<c:if test="${loggedInUser.member_id ne user.following_id}">
						<button class="follow-btn" data-id="${user.following_id}">
							언팔로우 💔</button>
					</c:if>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p class="no-follow">팔로우한 유저가 없습니다 😥</p>
		</c:otherwise>
	</c:choose>

	<a href="<c:url value='/mypage' />" class="back-button">← 마이페이지로
		돌아가기</a>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		const API_URL = {
			toggleFollow : '<c:url value="/follow/toggle" />'
		};

		$(document).ready(function() {
			$('.follow-btn').click(function() {
				const $btn = $(this);
				const userId = $btn.data('id');

				$.post(API_URL.toggleFollow, {
					followingId : userId
				}, function(result) {
					if (result === 'followed') {
						$btn.text('언팔로우 💔');
					} else if (result === 'unfollowed') {
						$btn.text('팔로우 💗');
					}
				});
			});
		});
	</script>

	<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
