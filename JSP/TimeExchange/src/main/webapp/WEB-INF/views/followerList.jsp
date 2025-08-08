<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>나를 팔로우한 사람들</title>
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
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
	font-family: 'Pretendard', sans-serif;
	background: var(--surface-alt);
	margin: 0;
	padding: 0px 20px;
}

h2 {
	text-align: center;
	font-size: 24px;
	font-weight: 700;
	color: var(--text-main);
	margin-bottom: 30px;
}

.user-card {
	display: flex;
	align-items: center;
	background: var(--surface);
	padding: 16px;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
	margin-bottom: 16px;
	gap: 16px;
	justify-content: space-between;
}

.user-info {
	display: flex;
	align-items: center;
	gap: 16px;
	flex-grow: 1;
}

.user-card img {
	width: 48px;
	height: 48px;
	border-radius: 50%;
	object-fit: cover;
}

.user-name {
	font-size: 16px;
	font-weight: 600;
	color: var(--text-main);
	text-decoration: none;
}

.user-name:hover {
	text-decoration: underline;
}

.follow-btn {
	border-radius: 8px;
	font-weight: 600;
	font-size: 14px;
	padding: 6px 14px;
}

.no-followers {
	text-align: center;
	color: var(--text-sub);
	margin-top: 60px;
}

/* ✅ 돌아가기 버튼 디자인 */
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
  .section-title {
    margin-top: 48px;
    margin-bottom: 24px;
    font-size: 24px;
    font-weight: bold;
  }

</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
	<h2 class="section-title">📌 나를 팔로우한 사람들</h2>

	<c:choose>
		<c:when test="${not empty followerList}">
			<c:forEach var="user" items="${followerList}">
				<div class="user-card">
					<div class="user-info">
						<c:choose>
							<c:when
								test="${not empty user.profileImage and user.profileImage ne 'default-profile.png'}">
								<img src="<c:url value='/upload/${user.profileImage}'/>"
									alt="프로필" class="rounded-circle me-2"
									style="width: 48px; height: 48px;">
							</c:when>

							<c:otherwise>
								<img
									src="<c:url value='/resources/images/default-profile.png'/>"
									alt="기본 프로필" class="rounded-circle me-2"
									style="width: 48px; height: 48px;">
							</c:otherwise>
						</c:choose>
						<a class="user-name"
							href="${pageContext.request.contextPath}/profile/${user.follower_id}">
							${user.userName} </a>
					</div>

					<c:if test="${loggedInUser.member_id ne user.follower_id}">
						<button class="follow-btn btn btn-sm btn-outline-accent"
							data-id="${user.follower_id}">팔로우 💗</button>
					</c:if>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p class="no-followers">아직 나를 팔로우한 유저가 없습니다.</p>
		</c:otherwise>
	</c:choose>

	<!-- ✅ 감성적인 돌아가기 버튼 -->
	<a class="back-button" href="<c:url value='/mypage' />">← 마이페이지로
		돌아가기</a>

	<!-- ✅ JS -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		const API_URL = {
			toggleFollow : '<c:url value="/follow/toggle" />'
		};

		$(document).ready(
				function() {
					$('.follow-btn').click(
							function() {
								const $btn = $(this);
								const userId = $btn.data('id');

								$.post(API_URL.toggleFollow, {
									followingId : userId
								}, function(result) {
									if (result === 'followed') {
										$btn.text('언팔로우 💔').removeClass(
												'btn-outline-accent').addClass(
												'btn-outline-danger');
									} else if (result === 'unfollowed') {
										$btn.text('팔로우 💗').removeClass(
												'btn-outline-danger').addClass(
												'btn-outline-accent');
									}
								});
							});
				});
	</script>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
</body>
</html>
