<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>거래 내역</title>

    <!-- 감성 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        h2 {
            margin-top: 30px;
            color: #7e22ce;
            font-weight: bold;
            text-align: center;
        }

        .card {
            border: none;
            border-radius: 20px;
            background: rgba(255, 255, 255, 0.65);
            backdrop-filter: blur(12px);
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            transition: all 0.3s ease;
            padding: 20px;
        }

        .card:hover {
            transform: translateY(-6px);
            box-shadow: 0 12px 28px rgba(0, 0, 0, 0.12);
        }

        .history-item {
            margin-bottom: 24px;
        }

        .history-title {
            font-size: 20px;
            font-weight: 700;
            color: #6b21a8;
        }

        .history-info {
            font-size: 15px;
            color: #5b5b5b;
            margin-bottom: 4px;
        }

        .text-muted {
            font-size: 14px;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container">
    <h2>📜 거래 내역</h2>
    <div class="row mt-4 mb-5">

        <c:choose>
            <c:when test="${not empty historyList}">
                <c:forEach var="dto" items="${historyList}">
                    <div class="col-md-6 history-item">
                        <div class="card">
						    <div class="history-title">${dto.type} 거래</div>
						    <div class="history-info">카테고리: ${dto.category}</div>
						    <div class="history-info">구매자: ${dto.buyer_id}</div>
						    <div class="history-info">판매자: ${dto.seller_id}</div>
						    <div class="history-info">거래 시간: ${dto.account}시간</div>
						    <div class="history-info">변동 시간: ${dto.balance_change}시간</div>
						    <div class="text-muted">거래 일시: ${dto.created_at}</div>
						
						    <!-- 리뷰 버튼 -->
						    <div class="mt-3">
						        <c:choose>
						            <c:when test="${dto.review_written}">
						                <a href="${pageContext.request.contextPath}/mypage/review/view?id=${dto.review_id}" class="btn btn-outline-success btn-sm">✅ 리뷰 보기</a>
						                <a href="${pageContext.request.contextPath}/mypage/review/form?id=${dto.review_id}" class="btn btn-outline-primary btn-sm">🛠 리뷰 수정</a>
						            </c:when>
						            <c:otherwise>
						                <a href="${pageContext.request.contextPath}/mypage/review/form?talent_id=${dto.talent_id}&seller_id=${dto.seller_id}" class="btn btn-outline-warning btn-sm">✍ 리뷰 작성</a>
						            </c:otherwise>
						        </c:choose>
						    </div>
						</div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12 d-flex justify-content-center align-items-center" style="height: 200px;">
                    <p class="fs-4 text-muted">표시할 거래 내역이 없습니다 😥</p>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
