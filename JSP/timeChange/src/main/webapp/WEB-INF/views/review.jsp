<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #e0c3fc 0%, #8ec5fc 100%);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .review-detail {
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            width: 600px;
        }

        h2 {
            text-align: center;
            color: #7e22ce;
            margin-bottom: 30px;
        }

        .star {
            color: #fbbf24;
            font-size: 24px;
        }

        .field {
            margin-bottom: 20px;
        }

        .label {
            font-weight: 600;
            color: #6b21a8;
        }
    </style>
</head>
<body>
<div class="review-detail">
    <h2>리뷰 상세 보기</h2>
    <div class="field">
        <span class="label">작성자:</span> ${review.writerId}
    </div>
    <div class="field">
        <span class="label">대상자:</span> ${review.targetId}
    </div>
    <div class="field">
        <span class="label">재능 ID:</span> ${review.talentId}
    </div>
    <div class="field">
        <span class="label">작성일:</span> ${review.createdAt}
    </div>
    <div class="field">
        <span class="label">평점:</span>
        <span class="star">
            <c:forEach begin="1" end="${review.rating}" var="i">★</c:forEach>
        </span>
    </div>
    <div class="field">
        <span class="label">리뷰 내용:</span>
        <p>${review.comment}</p>
    </div>
</div>
</body>
</html>
