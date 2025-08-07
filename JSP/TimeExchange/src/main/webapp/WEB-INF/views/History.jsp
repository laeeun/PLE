<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>TimeFair - 거래 내역</title>

<!-- 폰트 및 외부 리소스 -->
<link
	href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/bootstrap.min.css' />"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap"
	rel="stylesheet" />

<style>
:root {
	--primary: #1F2C40;
	--accent: #FF6B35;
	--accent-light: #FFEDE4;
	--surface: #ffffff;
	--surface-alt: #f9f9f9;
	--border: #e5e7eb;
	--text-main: #1F2C40;
	--text-sub: #6B7280;
}

* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Pretendard', sans-serif;
	color: var(--text-main);
	background: var(--surface);
}

.container {
	max-width: 1000px;
	margin: 0 auto;
	padding: 0 24px 80px;
}

h2 {
	margin-top: 60px;
	text-align : center;
	font-family: 'Montserrat', sans-serif;
	font-size: 32px;
	font-weight: 800;
	margin-bottom: 40px;
	color: var(--primary);
	text-align: center;
}

.card {
	background: var(--surface-alt);
	border: 1px solid var(--border);
	border-radius: 16px;
	padding: 24px;
	margin-bottom: 24px;
	box-shadow: 0 6px 16px rgba(0, 0, 0, 0.05);
	transition: transform 0.25s, box-shadow 0.25s;
}

.card:hover {
	transform: translateY(-4px);
	box-shadow: 0 10px 24px rgba(0, 0, 0, 0.08);
}

.history-title {
	font-size: 20px;
	font-weight: 700;
	color: var(--primary);
	margin-bottom: 12px;
}

.history-info {
	font-size: 15px;
	color: var(--text-sub);
	margin-bottom: 4px;
}

.text-muted {
	font-size: 13px;
	color: #999;
}

.btn-sm {
	padding: 6px 12px;
	font-size: 13px;
}

.empty-box {
	text-align: center;
	padding: 80px 0;
	color: var(--text-sub);
	font-size: 18px;
}
</style>
</head>
<body>

	<!-- 상단 고정 내비게이션 -->
	<jsp:include page="/WEB-INF/views/nav.jsp" />

	<div class="container">
		<h2>📜 거래 내역</h2>

		<c:choose>
			<c:when test="${not empty historyList}">
				<c:forEach var="dto" items="${historyList}">
					<div class="card">
						<div class="history-title">${dto.type}거래</div>
						<div class="history-info">카테고리: ${dto.category}</div>
						<div class="history-info">구매자: ${dto.buyer_id}</div>
						<div class="history-info">판매자: ${dto.seller_id}</div>
						<div class="history-info">거래 시간: ${dto.account}시간</div>
						<div class="history-info">변동 시간: ${dto.balance_change}시간</div>
						<div class="text-muted">거래 일시: ${dto.created_at}</div>

						<div class="mt-3">
							<c:choose>
								<c:when test="${dto.review_written}">
									<a
										href="<c:url value='/review/myreviews'/>?id=${dto.review_id}"
										class="btn btn-outline-success btn-sm"> ✅ 리뷰 보기 </a>
								</c:when>
								<c:otherwise>
									<c:if
										test="${dto.buyer_id eq loggedInUser.member_id and dto.review_id == null}">
										<a
											href="<c:url value='/review/form' />?historyId=${dto.history_id}&buyerId=${dto.buyer_id}&sellerId=${dto.seller_id}&talentId=${dto.talent_id}&category=${dto.category}"
											class="btn btn-outline-warning btn-sm"> ✍ 리뷰 작성 </a>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="empty-box">표시할 거래 내역이 없습니다 😥</div>
			</c:otherwise>
		</c:choose>
	</div>

	<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
	<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
