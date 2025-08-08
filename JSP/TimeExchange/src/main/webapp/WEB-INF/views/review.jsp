<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세</title>

    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #f3e8ff, #e0e7ff, #fce7f3);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .review-detail {
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(10px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            width: 90%;
            max-width: 700px;
            margin: 0 auto;
            margin-top: 80px;
            margin-bottom: 40px;
        }

        h2 {
            text-align: center;
            color: #1F2C40;
            margin-bottom: 30px;
            font-weight: bold;
        }

        .star {
            color: #fbbf24;
            font-size: 20px;
        }

        .field {
            margin-bottom: 20px;
            font-size: 15px;
            color: #1f2937;
        }

        .label {
            font-weight: 600;
            color: #6A737D;
        }

        textarea {
            resize: none;
        }

        .btn-primary, .btn-secondary, .btn-outline-primary, .btn-outline-danger {
            border-radius: 10px;
            padding: 6px 14px;
            font-weight: 500;
            font-size: 14px;
        }

        .btn-outline-primary {
            border-color: #1F2C40;
            color: #1F2C40;
        }

        .btn-outline-primary:hover {
            background-color: #1F2C40;
            color: #fff;
        }

        .btn-outline-danger {
            border-color: #FF6B35;
            color: #FF6B35;
        }

        .btn-outline-danger:hover {
            background-color: #FF6B35;
            color: #fff;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="review-detail">
    <h2><i class="fas fa-comment-dots"></i> 리뷰 상세 보기</h2>

    <div class="field">
        <span class="label"><i class="fas fa-user"></i> 작성자:</span> ${review.writerName}
    </div>
    <div class="field">
        <span class="label"><i class="fas fa-user-check"></i> 대상자:</span> ${review.targetName}
    </div>
    <div class="field">
        <span class="label"><i class="fas fa-tags"></i> 카테고리:</span> ${review.category}
    </div>
    <div class="field">
        <span class="label"><i class="fas fa-clock"></i> 작성일:</span> ${review.formattedCreatedAt}
    </div>
    <div class="field">
        <span class="label"><i class="fas fa-star"></i> 평점:</span>
        <span class="star">
            <c:forEach begin="1" end="${review.rating}" var="i">★</c:forEach>
        </span>
    </div>
    <div class="field">
        <span class="label"><i class="fas fa-pen"></i> 리뷰 내용:</span>
        <p>${review.comment}</p>
    </div>

    <!-- ✅ 답글 영역 -->
    <c:if test="${not empty review.reply}">
        <c:choose>
            <c:when test="${editMode}">
                <form action="${pageContext.request.contextPath}/review/reply/update" method="post" class="mt-4">
                    <input type="hidden" name="reviewId" value="${review.reviewId}" />
                    <input type="hidden" name="replyId" value="${review.reply.replyId}" />
                    <div class="form-group">
                        <label class="label">✏ 답글 수정:</label>
                        <textarea name="content" class="form-control" rows="3" required>${review.reply.replyContent}</textarea>
                    </div>
                    <div class="text-end mt-2">
                        <button type="submit" class="btn btn-primary">수정 완료</button>
                        <a href="${pageContext.request.contextPath}/review/view?id=${review.reviewId}" class="btn btn-secondary">취소</a>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="field" style="background-color: #f9fafb; padding: 15px; border-radius: 8px;">
                    <span class="label text-primary"><i class="fas fa-reply"></i> 판매자 답글:</span>
                    <p class="mt-2">${review.reply.replyContent}</p>
                    <small class="text-muted">답변일: ${review.reply.formattedReplyCreatedAt}</small>

                    <c:if test="${loggedInUser != null && loggedInUser.member_id eq review.targetName}">
                        <div class="mt-2 text-end">
                            <a href="${pageContext.request.contextPath}/review/reply/edit?reviewId=${review.reviewId}" class="btn btn-sm btn-outline-primary"><i class="fas fa-edit me-1"></i> 답글 수정</a>
                            <form action="${pageContext.request.contextPath}/review/reply/delete" method="post" style="display:inline;">
                                <input type="hidden" name="reviewId" value="${review.reviewId}" />
                                <button type="submit" class="btn btn-sm btn-outline-danger"
                                        onclick="return confirm('정말 삭제하시겠습니까?')"><i class="fas fa-trash-alt me-1"></i> 답글 삭제</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${empty review.reply && loggedInUser != null && loggedInUser.member_id eq review.targetName}">
        <form action="${pageContext.request.contextPath}/review/reply/submit" method="post" class="mt-4">
            <input type="hidden" name="reviewId" value="${review.reviewId}" />
            <div class="form-group">
                <label class="label"><i class="fas fa-reply"></i> 답글 작성:</label>
                <textarea name="content" class="form-control" rows="3" placeholder="답글을 입력하세요" required></textarea>
            </div>
            <div class="text-end mt-2">
                <button type="submit" class="btn btn-primary">등록</button>
            </div>
        </form>
    </c:if>

    <!-- ✏ 리뷰 수정/삭제 버튼 -->
    <div class="text-center mt-4">
        <c:if test="${review.writerName == loggedInUser.userName}">
            <a href="${pageContext.request.contextPath}/review/update?id=${review.reviewId}" 
               class="btn btn-outline-primary me-2"><i class="fas fa-edit me-1"></i> 리뷰 수정</a>

            <form action="${pageContext.request.contextPath}/review/delete" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${review.reviewId}">
                <button type="submit" class="btn btn-outline-danger"
                        onclick="return confirm('정말 삭제하시겠습니까?')"> <i class="fas fa-trash-alt me-1"></i> 리뷰 삭제</button>
            </form>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
