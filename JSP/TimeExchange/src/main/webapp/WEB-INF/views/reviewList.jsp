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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <style>
    	:root {
		  --primary:      #1F2C40;  /* 미드나잇 블루 */
		  --accent:       #FF6B35;  /* 밝은 오렌지 */
		  --accent-100:   #FFEEEA;
		  --surface:      #F9F9F9;
		  --surface-alt:  #FFFFFF;
		  --border:       #E8E8E8;
		  --text-main:    #1F2C40;
		  --text-sub:     #6A737D;
		}
        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            color: var(--text-main);
           
        }

        .card {
            background: var(--surface-alt);
            border: 1px solid var(--border);
            border-radius: 16px;
            padding: 30px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
            max-width: 900px;
            margin: 0 auto 40px;
            margin-top: 40px;
        }

        .review-item {
            border-bottom: 1px solid var(--border);
            padding: 20px 0;
        }

        .review-item:last-child {
            border-bottom: none;
        }

        .star {
            color: #fbbf24;
            font-size: 18px;
        }

        .btn-detail, .btn-home, .btn-outline-primary {
            background: var(--accent);
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 8px 16px;
            text-decoration: none;
            transition: box-shadow 0.3s ease;
            font-weight: 500;
        }

        .btn-detail:hover, .btn-home:hover, .btn-outline-primary:hover {
		    filter: brightness(1.08);
		    transform: translateY(-1px);
		    border-color: var(--accent);
		    transition: all 0.3s ease;
		    text-decoration: none;
    		color: inherit;
		}

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .empty-message {
            color: var(--text-sub);
            font-style: italic;
            text-align: center;
            padding: 20px 0;
        }

        textarea {
            resize: none;
        }

        .alert-info {
            background-color: var(--accent-100);
            border: 1px solid var(--accent);
            color: var(--text-main);
        }
        a {
		    text-decoration: none;
		    color: inherit; 
		}
		.btn-reply {
		  background-color: var(--primary);      
		  color: var(--surface-alt);             
		  border: none;
		  padding: 8px 16px;
		  font-size: 14px;
		  font-weight: 600;
		  border-radius: 8px;
		  cursor: pointer;
		  transition: background-color 0.3s ease, transform 0.2s ease;
		  display: inline-flex;
		  align-items: center;
		  gap: 6px;
		  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
		}
		
		.btn-reply i {
		  color: var(--accent); 
		}
		
		.btn-reply:hover {
		  background-color: var(--accent);       
		  color: var(--primary);
		  text-decoration: none;
		  color: inherit;                 
		}
		
		.btn-reply:hover i {
		  color: var(--primary);                 /* 아이콘도 동일하게 반전 */
		}
		
		.btn-reply:active {
		  transform: scale(0.96);                /* 클릭 시 살짝 눌림 효과 */
		}
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<!-- 내가 쓴 리뷰 -->
<div class="card">
    <div class="top-bar">
        <h2><i class="fas fa-pen"></i> 내가 쓴 리뷰</h2>
        <a href="<c:url value='/'/>" class="btn-home"><i class="fas fa-home"></i> 홈으로</a>
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
            <small class="text-muted">작성일: ${review.formattedCreatedAt}</small>
            <div class="mt-2">
                <a href="<c:url value='/review/view?id=${review.reviewId}'/>" class="btn-detail">
                    <i class="fas fa-magnifying-glass"></i> 자세히 보기
                </a>
            </div>
        </div>
    </c:forEach>
</div>

<!-- 내가 받은 리뷰 -->
<div class="card">
    <div class="top-bar">
        <h2><i class="fas fa-envelope-open-text"></i> 내가 받은 리뷰</h2>
        <a href="<c:url value='/'/>" class="btn-home"><i class="fas fa-home"></i> 홈으로</a>
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

            <c:if test="${not empty review.reply}">
                <div class="alert alert-info mt-3">
                    <strong><i class="fas fa-reply"></i> 판매자 답글:</strong> ${review.reply.replyContent}<br>
                    <small class="text-muted">작성일: ${review.reply.replyCreatedAt}</small>
                </div>
            </c:if>

            <c:if test="${empty review.reply && loggedInUser != null && loggedInUser.userName eq review.targetName}">
                <form action="<c:url value='/review/reply/submit' />" method="post" class="mt-3">
                    <input type="hidden" name="reviewId" value="${review.reviewId}">
                    <div class="input-group">
                        <textarea name="content" class="form-control" placeholder="답글을 작성하세요" rows="2" required></textarea>
                        <button type="submit" class="btn-reply">
						  <i class="fas fa-paper-plane"></i> 답글 등록
						</button>
                    </div>
                </form>
            </c:if>

            <div class="mt-2">
                <a href="<c:url value='/review/view?id=${review.reviewId}'/>" class="btn-detail">
                    <i class="fas fa-magnifying-glass"></i> 자세히 보기
                </a>
            </div>
        </div>
    </c:forEach>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
