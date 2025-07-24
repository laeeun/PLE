<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .review-detail {
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

        textarea {
            resize: none;
        }


        
        .reaction-btn {
		    border: none;
		    background-color: #f3f4f6; /* 기본 배경 */
		    margin-right: 10px;
		    font-size: 20px;
		    cursor: pointer;
		    border-radius: 30px;
		    padding: 6px 16px;
		    color: #4b5563; /* 회색 텍스트 */
		    transition: all 0.2s ease-in-out;
		    display: inline-flex;
		    align-items: center;
		    gap: 6px;
		}
		
		/* 좋아요 누른 상태 - 연두 배경 */
		.reaction-btn.liked {
		    background-color: #4ade80; /* 연두 */
		    color: white;
		    font-weight: bold;
		    box-shadow: 0 0 10px rgba(74, 222, 128, 0.5);
		}
		
		/* 싫어요 누른 상태 - 붉은 배경 */
		.reaction-btn.disliked {
		    background-color: #f87171; /* 붉은 */
		    color: white;
		    font-weight: bold;
		    box-shadow: 0 0 10px rgba(248, 113, 113, 0.5);
		}
		
		/* 살짝 호버 효과 */
		.reaction-btn:hover {
		    transform: scale(1.05);
		}

    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="review-detail">
    <h2>리뷰 상세 보기</h2>

    <div class="field">
        <span class="label">작성자:</span> ${review.writerId}
    </div>
    <div class="field">
        <span class="label">대상자:</span> ${review.targetId}
    </div>
    <div class="field">
        <span class="label">카테고리:</span> ${review.category}
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

    <!-- 👍👎 좋아요/싫어요 버튼 -->
    <c:if test="${not empty loggedInUser}">
	    <div class="mt-3">
	        <!-- 👍 좋아요 버튼 -->
			<button class="reaction-btn ${fn:toUpperCase(review.myReaction) eq 'LIKE' ? 'liked' : ''}"
			        onclick="toggleReaction('${review.reviewId}', 'LIKE', '${loggedInUser.member_id}')">
			    👍 <span>${review.likeCount}</span>
			</button>
			
			<button class="reaction-btn ${fn:toUpperCase(review.myReaction) eq 'DISLIKE' ? 'disliked' : ''}"
			        onclick="toggleReaction('${review.reviewId}', 'DISLIKE', '${loggedInUser.member_id}')">
			    👎 <span>${review.dislikeCount}</span>
			</button>
	    </div>
	</c:if>
	
	
    <!-- ✅ 답글 영역 -->
    <c:if test="${not empty review.reply}">
        <c:choose>
            <c:when test="${editMode}">
                <form action="${pageContext.request.contextPath}/review/reply/update" method="post" class="mt-4">
                    <input type="hidden" name="reviewId" value="${review.reviewId}" />
                    <div class="form-group">
                        <label class="label">답글 수정:</label>
                        <textarea name="content" class="form-control" rows="3" required>${review.reply.content}</textarea>
                    </div>
                    <div class="text-end mt-2">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/review/view?id=${review.reviewId}" class="btn btn-secondary">취소</a>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="field" style="background-color: #f8f9fa; padding: 15px; border-radius: 8px;">
                    <span class="label" style="color: #0d6efd;">판매자 답글:</span>
                    <p class="mt-2">${review.reply.content}</p>
                    <small class="text-muted">답변일: ${review.reply.createdAt}</small>

                    <c:if test="${loggedInUser != null && loggedInUser.member_id eq review.targetId}">
                        <div class="mt-2 text-end">
                            <a href="${pageContext.request.contextPath}/review/reply/edit?reviewId=${review.reviewId}" class="btn btn-sm btn-secondary">✏ 답글 수정</a>
                            <form action="${pageContext.request.contextPath}/review/reply/delete" method="post" style="display:inline;">
                                <input type="hidden" name="reviewId" value="${review.reviewId}" />
                                <button type="submit" class="btn btn-sm btn-outline-danger"
                                        onclick="return confirm('정말 삭제하시겠습니까?')">❌ 답글 삭제</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <!-- 답글이 없고, 판매자일 경우 답글 작성 -->
    <c:if test="${empty review.reply && loggedInUser != null && loggedInUser.member_id eq review.targetId}">
        <form action="${pageContext.request.contextPath}/review/reply/submit" method="post" class="mt-4">
            <input type="hidden" name="reviewId" value="${review.reviewId}" />
            <div class="form-group">
                <label class="label">답글 작성:</label>
                <textarea name="content" class="form-control" rows="3" placeholder="답글을 입력하세요" required></textarea>
            </div>
            <div class="text-end mt-2">
                <button type="submit" class="btn btn-primary">등록</button>
            </div>
        </form>
    </c:if>

    <!-- ✏ 리뷰 수정/삭제 버튼 (작성자 전용) -->
    <div class="text-center mt-4">
        <c:if test="${review.writerId == loggedInUser.member_id}">
            <a href="${pageContext.request.contextPath}/review/update?id=${review.reviewId}" 
               class="btn btn-outline-primary me-2">✏ 리뷰 수정</a>

            <form action="${pageContext.request.contextPath}/review/delete" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${review.reviewId}">
                <button type="submit" class="btn btn-outline-danger"
                        onclick="return confirm('정말 삭제하시겠습니까?')">❌ 리뷰 삭제</button>
            </form>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
<script>
function toggleReaction(review_id, type, member_id) {
    const formData = new URLSearchParams();
    formData.append("reviewId", review_id);
    formData.append("reactionType", type);
    formData.append("memberId", member_id);

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
            alert("로그인 정보가 올바르지 않아요.");
        } else {
            alert("처리 실패 😢");
        }
    })
    .catch(error => {
        console.error("오류 발생:", error);
        alert("요청 중 오류 발생!");
    });
}

</script>

</body>
</html>
