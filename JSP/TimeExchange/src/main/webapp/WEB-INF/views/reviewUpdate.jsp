<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 수정</title>

    <!-- 공통 폰트 및 부트스트랩 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <!-- 💜 감성 스타일 -->
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #e0c3fc 0%, #8ec5fc 100%);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .review-form {
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            width: 600px;
            margin: 0 auto;
            margin-top: 80px;
            margin-bottom: 40px;
        }

        h2 {
            text-align: center;
            color: #7e22ce;
            margin-bottom: 30px;
        }

        .form-group label {
            font-weight: 600;
            color: #6b21a8;
        }

        .form-control, .form-select {
            background-color: rgba(255,255,255,0.6);
            border: 1px solid #ddd;
            border-radius: 10px;
        }

        .btn-submit {
            background-color: #9333ea;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            margin-top: 20px;
            transition: 0.3s ease;
        }

        .btn-submit:hover {
            background-color: #7e22ce;
        }

        /* ⭐ 별점 스타일 */
        .rating-stars {
            display: flex;
            gap: 8px;
            font-size: 32px;
            justify-content: center;
            margin-top: 10px;
        }

        .rating-stars .star {
            cursor: pointer;
            color: #ddd;
            transition: color 0.2s;
        }

        .rating-stars .star.selected {
            color: #facc15;
        }
    </style>
</head>
<body>

<!-- ✅ 네비게이션 바 인클루드 -->
<jsp:include page="/WEB-INF/views/nav.jsp" />

<!-- 💜 리뷰 수정 폼 -->
<div class="review-form">
    <h2>리뷰 수정하기</h2>
    <form action="${pageContext.request.contextPath}/review/update" method="post">

        <!-- 숨겨진 필드들 (수정 시 필수) -->
        <input type="hidden" name="reviewId" value="${review.reviewId}">
        <input type="hidden" name="writerId" value="${review.writerId}">
        <input type="hidden" name="targetId" value="${review.targetId}">
        <input type="hidden" name="talentId" value="${review.talentId}">
        <input type="hidden" name="historyId" value="${review.historyId}">

        <!-- ⭐ 별점 선택 -->
        <div class="form-group mb-3">
            <label for="rating">평점 (1~5)</label>
            <div class="rating-stars" id="rating-stars">
                <c:forEach var="i" begin="1" end="5">
                    <span class="star ${i <= review.rating ? 'selected' : ''}" data-value="${i}">&#9733;</span>
                </c:forEach>
            </div>
            <input type="hidden" id="rating" name="rating" value="${review.rating}" />
        </div>

        <div class="form-group mb-3">
            <label for="comment">리뷰 내용</label>
            <textarea class="form-control" name="comment" id="comment" rows="5" required>${review.comment}</textarea>
        </div>

        <div class="text-center">
            <button type="submit" class="btn btn-submit">✏ 리뷰 수정 완료</button>
        </div>
    </form>
</div>

<!-- ✅ 푸터 인클루드 -->
<jsp:include page="/WEB-INF/views/footer.jsp" />

<!-- ⭐ 별 클릭 이벤트 스크립트 -->
<script>
    const stars = document.querySelectorAll('.rating-stars .star');
    const ratingInput = document.getElementById('rating');

    stars.forEach(star => {
        star.addEventListener('click', function () {
            const rating = parseInt(this.getAttribute('data-value'));
            ratingInput.value = rating;

            // 모든 별 초기화
            stars.forEach(s => s.classList.remove('selected'));

            // 클릭한 별까지 selected 클래스 추가
            for (let i = 0; i < rating; i++) {
                stars[i].classList.add('selected');
            }
        });
    });
</script>

</body>
</html>
