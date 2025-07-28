<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>내가 팔로우한 사람들</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: #fef4f8;
            padding: 40px;
        }
        .user-card {
            display: flex;
            align-items: center;
            background: white;
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(255, 192, 203, 0.3);
        }
        .user-card img {
            width: 48px;
            height: 48px;
            border-radius: 50%;
            margin-right: 1rem;
            object-fit: cover;
        }
        .user-name {
            font-size: 1.1rem;
            color: #d63384;
            text-decoration: none;
        }
        .user-name:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<h2>💗 내가 팔로우한 사람들</h2>

<c:choose>
    <c:when test="${not empty followingList}">
        <c:forEach var="user" items="${followingList}">
            <div class="user-card">
            	<c:choose>
				  <c:when test="${not empty user.profileImage}">
				    <img src="<c:url value='/resources/images/${user.profileImage}' />" alt="프로필" 
				         class="rounded-circle" width="48" height="48" />
				  </c:when>
				  <c:otherwise>
				    <img src="<c:url value='/resources/images/default-profile.png' />" alt="기본 프로필"
				         class="rounded-circle" width="48" height="48" />
				  </c:otherwise>
				</c:choose>				              
                <a class="user-name" href="${pageContext.request.contextPath}/profile/${user.following_id}">${user.userName}</a>
                <c:if test="${loggedInUser.member_id ne user.following_id}">
                    <button class="follow-btn btn btn-sm btn-outline-danger"
                            data-id="${user.following_id}">
                        언팔로우 💔
                    </button>
                </c:if>
                
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <p>팔로우한 유저가 없습니다.</p>
    </c:otherwise>
</c:choose>

<a href="<c:url value='/mypage' />">← 마이페이지로 돌아가기</a>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    const API_URL = {
        toggleFollow: '<c:url value="/follow/toggle" />'
    };

    $(document).ready(function () {
        $('.follow-btn').click(function () {
            const $btn = $(this);
            const userId = $btn.data('id');

            $.post(API_URL.toggleFollow, { followingId: userId }, function (result) {
                if (result === 'followed') {
                    $btn.text('언팔로우 💔').removeClass('btn-outline-primary').addClass('btn-outline-danger');
                } else if (result === 'unfollowed') {
                    $btn.text('팔로우 💗').removeClass('btn-outline-danger').addClass('btn-outline-primary');
                }
            });
        });
    });
</script>
</html>
