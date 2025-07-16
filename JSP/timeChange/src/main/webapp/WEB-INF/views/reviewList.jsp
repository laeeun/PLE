<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            padding: 60px;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .card {
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 8px 24px rgba(0,0,0,0.15);
            max-width: 800px;
            margin: 0 auto;
        }

        .review-item {
            border-bottom: 1px solid #ddd;
            padding: 20px 0;
        }

        .review-item:last-child {
            border-bottom: none;
        }

        .star {
            color: #fbbf24;
            font-size: 20px;
        }

        .btn-detail, .btn-home {
            background: linear-gradient(to right, #a855f7, #ec4899);
            color: white;
            border: none;
            border-radius: 8px;
            padding: 8px 16px;
            text-decoration: none;
            transition: box-shadow 0.3s ease;
        }

        .btn-detail:hover, .btn-home:hover {
            box-shadow: 0 0 12px #ec4899;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

    <%-- 공통 내비게이션 --%>
    <jsp:include page="/WEB-INF/views/nav.jsp" />

    <div class="card">
        <div class="top-bar">
            <h2 class="mb-0">📋 나의 리뷰 목록</h2>
            <a href="<c:url value='/'/>" class="btn-home">🏠 홈으로</a>
        </div>

        <c:forEach var="review" items="${reviews}">
            <div class="review-item">
                <div>
                    <span class="star">
                        <c:forEach begin="1" end="${review.rating}" var="i">★</c:forEach>
                    </span>
                </div>
                <p>${fn:substring(review.comment, 0, 30)}...</p>
                <small class="text-muted">작성일: ${review.createdAt}</small>
                <div class="mt-2">
                    <a href="<c:url value='/review/view?id=${review.reviewId}'/>" class="btn-detail">자세히 보기</a>
                </div>
            </div>
        </c:forEach>
    </div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
