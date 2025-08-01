<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이 재능의 전체 리뷰</title>
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
            margin: 0 auto 40px;
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

        .btn-back {
            background: linear-gradient(to right, #a855f7, #ec4899);
            color: white;
            border: none;
            border-radius: 8px;
            padding: 8px 16px;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
            transition: box-shadow 0.3s ease;
        }

        .btn-back:hover {
            box-shadow: 0 0 12px #ec4899;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .empty-message {
            color: #999;
            font-style: italic;
            text-align: center;
            padding: 20px 0;
        }

        /* 🎨 페이징 스타일 */
        .pagination .page-link {
            color: #7c3aed;
            border: none;
            margin: 0 4px;
            font-weight: bold;
            border-radius: 8px;
        }

        .pagination .page-item.active .page-link {
            background-color: #a855f7;
            color: white;
            box-shadow: 0 0 6px rgba(168, 85, 247, 0.8);
        }

        .pagination .page-link:hover {
            background-color: #f3e8ff;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="card">
    <div class="top-bar">
        <h2>📝 이 재능에 달린 리뷰 목록</h2>
        <a href="<c:url value='/talent/view?id=${talentId}' />" class="btn-back">← 게시글로 돌아가기</a>
    </div>

    <c:if test="${empty reviews}">
        <p class="empty-message">아직 등록된 리뷰가 없습니다.</p>
    </c:if>

    <c:forEach var="review" items="${reviews}">
        <div class="review-item">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <div><strong>${review.writerName}</strong> → ${review.targetName}</div>
                <small class="text-muted">
                    <div><strong>${review.createdAt}</strong></div>
                </small>
            </div>
            <div>
                <span class="star">
                    <c:forEach var="i" begin="1" end="5">
                        <c:choose>
                            <c:when test="${i <= review.rating}">★</c:when>
                            <c:otherwise>☆</c:otherwise>
                        </c:choose>
                    </c:forEach>
                </span>
                (${review.rating}점)
            </div>
            <p class="mt-2">${review.comment}</p>
        </div>
    </c:forEach>

    <!-- 💫 페이징 블럭 -->
    <c:if test="${totalPages > 1}">
        <div class="text-center mt-4">
            <ul class="pagination justify-content-center">
                <!-- 이전 페이지 -->
                <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
                    <a class="page-link" href="?id=${talentId}&page=${currentPage - 1}">〈</a>
                </li>

                <!-- 페이지 번호 -->
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <li class="page-item <c:if test='${i == currentPage}'>active</c:if>'">
                        <a class="page-link" href="?id=${talentId}&page=${i}">${i}</a>
                    </li>
                </c:forEach>

                <!-- 다음 페이지 -->
                <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
                    <a class="page-link" href="?id=${talentId}&page=${currentPage + 1}">〉</a>
                </li>
            </ul>
        </div>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
