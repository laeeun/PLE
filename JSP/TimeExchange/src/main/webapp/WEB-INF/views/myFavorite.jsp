<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>나의 찜 목록</title>

    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(135deg, #e0d6ff, #f5f2ff);
            margin: 0;
            
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #4a3f78;
        }

        .favorites-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
        }

        .favorite-card {
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(10px);
            border-radius: 16px;
            padding: 20px;
            width: 300px;
            position: relative;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transition: transform 0.2s ease;
            cursor: pointer;
        }

        .favorite-card:hover {
            transform: translateY(-5px);
        }

        .favorite-title {
            font-size: 18px;
            font-weight: bold;
            color: #352f58;
            margin-bottom: 8px;
        }

        .favorite-description {
            font-size: 14px;
            color: #555;
            height: 60px;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .favorite-meta {
            font-size: 12px;
            color: #888;
            margin-top: 10px;
        }

        .delete-btn {
            position: absolute;
            top: 15px;
            right: 15px;
            background-color: #f96d6d;
            color: white;
            border: none;
            border-radius: 6px;
            padding: 6px 10px;
            font-size: 12px;
            cursor: pointer;
        }

        .delete-btn:hover {
            background-color: #d94a4a;
        }

        .pagination {
            text-align: center;
            margin-top: 40px;
        }

        .pagination a, .pagination strong {
            margin: 0 6px;
            font-size: 15px;
            text-decoration: none;
        }

        .pagination a {
            color: #555;
        }

        .pagination strong {
            color: #6c5ce7;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<h2>💖 내가 찜한 재능 목록</h2>

<div class="favorites-container">
    <c:forEach var="favorite" items="${myFavoriteList}">
        <div class="favorite-card" data-talent-id="${favorite.talentId}" onclick="location.href='<c:url value='/talent/view?id=${favorite.talentId}'/>'">
            <div class="favorite-title">${favorite.title}</div>
            <div class="favorite-description">${favorite.description}</div>
            <div class="favorite-meta">
                카테고리: ${favorite.category} | 제공시간: ${favorite.timeSlot}분
            </div>
            <button class="delete-btn">삭제</button>
        </div>
    </c:forEach>
	
    <c:if test="${empty myFavoriteList}">
        <p style="text-align:center; width:100%; color:#666;">찜한 재능이 없습니다.</p>
    </c:if>
</div>

<div class="pagination">
    <c:forEach begin="1" end="${totalPage}" var="i">
        <c:choose>
            <c:when test="${i == currentPage}">
                <strong>[${i}]</strong>
            </c:when>
            <c:otherwise>
                <a href="?page=${i}">[${i}]</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

<jsp:include page="/WEB-INF/views/footer.jsp" />
<script>
    const deleteUrl = '<c:url value="/favorite/delete" />';

    $('.delete-btn').click(function (e) {
        e.stopPropagation(); // 카드 클릭 이벤트 방지
        const card = $(this).closest('.favorite-card');
        const talentId = card.data('talent-id');

        $.ajax({
            url: deleteUrl,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ talentId }),
            success: function (data) {
                if (data.status === 'success') {
                    card.remove();
                } else {
                    alert("삭제 실패: " + data.status);
                }
            },
            error: function () {
                alert("서버와의 통신 중 오류가 발생했습니다.");
            }
        });
    });
</script>

</body>
</html>
