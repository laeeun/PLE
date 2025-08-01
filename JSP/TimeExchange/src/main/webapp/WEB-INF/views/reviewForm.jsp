<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 작성</title>

    <!-- Pretendard 폰트 + Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .review-card {
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            width: 600px;
            animation: fadeInUp 0.7s ease both;
        }

        @keyframes fadeInUp {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        h2 {
            font-weight: bold;
            color: #7e22ce;
            margin-bottom: 30px;
            text-align: center;
        }

        label {
            font-weight: 600;
            color: #6b21a8;
        }

        textarea {
            resize: vertical;
            min-height: 100px;
            border-radius: 12px;
            padding: 15px;
            border: none;
            background: rgba(255, 255, 255, 0.6);
            box-shadow: 0 2px 10px rgba(245, 183, 255, 0.15);
            outline: none;
        }

        .btn-gradient {
            background: linear-gradient(to right, #a855f7, #ec4899);
            color: white;
            font-weight: bold;
            padding: 12px 24px;
            border: none;
            border-radius: 12px;
            margin-top: 20px;
            box-shadow: 0 6px 16px rgba(168, 85, 247, 0.3);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .btn-gradient:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(168, 85, 247, 0.4);
        }

        .btn-gradient::after {
            content: "";
            position: absolute;
            top: 0;
            left: -70%;
            width: 60%;
            height: 100%;
            background: linear-gradient(to right, rgba(255,255,255,0.4), rgba(255,255,255,0));
            transform: skewX(-25deg);
            animation: shine 2.5s infinite;
        }

        @keyframes shine {
            0% { left: -70%; }
            100% { left: 130%; }
        }

        /* ⭐ 별점 스타일 */
        .star-rating {
            direction: rtl;
            display: flex;
            gap: 8px;
            font-size: 36px;
            justify-content: center;
            margin-bottom: 20px;
        }

        .star-rating input[type="radio"] {
            display: none;
        }

        .star-rating label {
            color: #ddd;
            cursor: pointer;
            transition: transform 0.2s, color 0.3s;
            text-shadow: 0 0 5px rgba(168, 85, 247, 0.1);
        }

        .star-rating label:hover,
        .star-rating label:hover ~ label,
        .star-rating input[type="radio"]:checked ~ label {
            color: #fbbf24;
            text-shadow: 0 0 8px rgba(255, 215, 0, 0.6);
            transform: scale(1.1);
        }
    </style>
</head>
<body>

<div class="review-card">
    <h2>리뷰 작성하기</h2>

    <form action="<c:url value='/review/submit' />" method="post">
        
        <input type="hidden" name="historyId" value="${historyId}">
        <input type="hidden" name="sellerId" value="${sellerId}">
		<input type="hidden" name="talentId" value="${talentId}">
		<input type="hidden" name="category" value="${category}">
		

        <label class="form-label">평점</label>
        <div class="star-rating">
            <input type="radio" id="star5" name="rating" value="5"><label for="star5">★</label>
            <input type="radio" id="star4" name="rating" value="4"><label for="star4">★</label>
            <input type="radio" id="star3" name="rating" value="3"><label for="star3">★</label>
            <input type="radio" id="star2" name="rating" value="2"><label for="star2">★</label>
            <input type="radio" id="star1" name="rating" value="1"><label for="star1">★</label>
        </div>

        <div class="mb-3">
            <label for="comment" class="form-label">리뷰 내용</label>
            <textarea name="comment" class="form-control" placeholder="리뷰를 입력해주세요" required></textarea>
        </div>

        <button type="submit" class="btn-gradient">제출하기</button>

    </form>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />


</body>
</html>
