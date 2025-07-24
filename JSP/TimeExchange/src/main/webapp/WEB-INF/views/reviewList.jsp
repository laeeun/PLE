<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>리뷰 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet" />
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
            background: rgba(255, 255, 255, 0.75);
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
        .reaction-btn {
            border: none;
            background-color: #f3f4f6;
            color: #4b5563;
            border-radius: 25px;
            padding: 6px 14px;
            font-size: 16px;
            cursor: pointer;
            margin-right: 8px;
            display: inline-flex;
            align-items: center;
            gap: 6px;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        .reaction-btn.liked {
            background-color: #22c55e;
            color: white;
            font-weight: bold;
            box-shadow: 0 0 8px rgba(34, 197, 94, 0.7);
        }
        .reaction-btn.disliked {
            background-color: #ef4444;
            color: white;
            font-weight: bold;
            box-shadow: 0 0 8px rgba(239, 68, 68, 0.7);
        }
        .reaction-btn:hover {
            filter: brightness(0.9);
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
        .empty-message {
            color: #999;
            font-style: italic;
            text-align: center;
            padding: 20px 0;
        }
        small.text-muted {
            color: #666;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<!-- 내가 쓴 리뷰 -->
<div class="card">
    <div class="top-bar">
        <h2>내가 쓴 리뷰</h2>
        <a href="<c:url value='/'/>" class="btn-home">🏠 홈으로</a>
    </div>

    <c:if test="${empty writtenReviews}">
        <p class="empty-message">작성한 리뷰가 없습니다.</p>
    </c:if>

    <c:forEach var="review" items="${writtenReviews}">
        <div class="review-item">
            <div>
                <span class="star">
                    <c:forEach begin="1" end="${review.rating}" var="i">★</c:forEach>
                </span>
            </div>
            <p>${fn:substring(review.comment, 0, 30)}...</p>
            <small class="text-muted">작성일: ${review.createdAt}</small>

            <!-- 좋아요/싫어요 버튼 -->
            <c:if test="${not empty loggedInUser}">
                <button type="button"
                        class="reaction-btn ${review.myReaction eq 'LIKE' ? 'liked' : ''}"
                        onclick="toggleReaction(${review.reviewId}, 'LIKE', '${loggedInUser.member_id}')">
                    👍 ${review.likeCount}
                </button>
                <button type="button"
                        class="reaction-btn ${review.myReaction eq 'DISLIKE' ? 'disliked' : ''}"
                        onclick="toggleReaction(${review.reviewId}, 'DISLIKE', '${loggedInUser.member_id}')">
                    👎 ${review.dislikeCount}
                </button>
            </c:if>

            <div class="mt-2">
                <a href="<c:url value='/review/view?id=${review.reviewId}'/>" class="btn-detail">자세히 보기</a>
            </div>
        </div>
    </c:forEach>
</div>

<!-- 내가 받은 리뷰 -->
<div class="card">
    <div class="top-bar">
        <h2>내가 받은 리뷰</h2>
        <a href="<c:url value='/'/>" class="btn-home">🏠 홈으로</a>
    </div>

    <c:if test="${empty receivedReviews}">
        <p class="empty-message">받은 리뷰가 없습니다.</p>
    </c:if>

    <c:forEach var="review" items="${receivedReviews}">
        <div class="review-item">
            <div>
                <span class="star">
                    <c:forEach begin="1" end="${review.rating}" var="i">★</c:forEach>
                </span>
            </div>
            <p>${fn:substring(review.comment, 0, 30)}...</p>
            <small class="text-muted">작성일: ${review.createdAt}</small>

            <!-- 좋아요/싫어요 버튼 항상 보이게 -->
            <button type="button"
                    class="reaction-btn ${review.myReaction eq 'LIKE' ? 'liked' : ''}"
                    <c:if test="${empty loggedInUser}">disabled</c:if>
                    onclick="toggleReaction(${review.reviewId}, 'LIKE', '${loggedInUser != null ? loggedInUser.member_id : ''}')">
                👍 ${review.likeCount}
            </button>
            <button type="button"
                    class="reaction-btn ${review.myReaction eq 'DISLIKE' ? 'disliked' : ''}"
                    <c:if test="${empty loggedInUser}">disabled</c:if>
                    onclick="toggleReaction(${review.reviewId}, 'DISLIKE', '${loggedInUser != null ? loggedInUser.member_id : ''}')">
                👎 ${review.dislikeCount}
            </button>

            <!-- 답글 표시 -->
            <c:if test="${not empty review.reply}">
                <div class="alert alert-info mt-3" style="border-radius: 12px; background-color: #e3f2fd;">
                    <strong>판매자 답글:</strong> ${review.reply.content}
                    <br>
                    <small class="text-muted">작성일: ${review.reply.createdAt}</small>
                </div>
            </c:if>

            <!-- 답글 작성 (판매자만) -->
            <c:if test="${empty review.reply && loggedInUser != null && loggedInUser.member_id eq review.targetId}">
                <form action="<c:url value='/review/reply/submit' />" method="post" class="mt-3">
                    <input type="hidden" name="reviewId" value="${review.reviewId}" />
                    <div class="input-group">
                        <textarea name="content" class="form-control" placeholder="답글을 작성하세요" rows="2" required></textarea>
                        <button type="submit" class="btn btn-outline-primary">답글 등록</button>
                    </div>
                </form>
            </c:if>

            <div class="mt-2">
                <a href="<c:url value='/review/view?id=${review.reviewId}'/>" class="btn-detail">자세히 보기</a>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>
function toggleReaction(reviewId, type, memberId) {
    if(!memberId) {
        alert('로그인 후 이용해주세요.');
        return;
    }

    const formData = new URLSearchParams();
    formData.append("reviewId", reviewId);
    formData.append("reactionType", type);
    formData.append("memberId", memberId);

    fetch("<c:url value='/review/reaction/toggle' />", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData.toString()
    })
    .then(response => response.text())
    .then(result => {
        if (result === "success") {
            location.reload();
        } else if (result === "unauthorized") {
            alert("로그인 정보가 올바르지 않습니다.");
        } else {
            alert("처리에 실패했습니다.");
        }
    })
    .catch(error => {
        console.error("오류 발생:", error);
        alert("요청 중 오류가 발생했습니다.");
    });
}
</script>

</body>
</html>
