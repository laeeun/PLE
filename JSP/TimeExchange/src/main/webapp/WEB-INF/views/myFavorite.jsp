<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>나의 찜 목록</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
	
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }
		
        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            color: var(--text-main);
            margin: 0;
            padding: 0;
        }

        /* ✅ nav 제외한 본문에만 여백 적용 */
        .main-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 32px 20px;
            padding-top: 100px;
        }

        .page-title {
            text-align: center;
            font-family: 'Montserrat', sans-serif;
            font-size: 26px;
            font-weight: 700;
            color: var(--primary);
            margin: 60px 0 30px;
            position: relative;
        }

        .page-title::after {
            content: '';
            display: block;
            width: 60px;
            height: 3px;
            background-color: var(--accent);
            margin: 12px auto 0;
            border-radius: 2px;
        }

        .page-title span {
            color: var(--accent);
        }

        .favorites-wrapper {
            border: 1px solid var(--border);
            border-radius: 16px;
            background-color: var(--surface-alt);
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.03);
            padding: 32px;
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
            width: 280px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.06);
            transition: transform 0.2s ease;
            cursor: pointer;
            position: relative;
            border: 1px solid var(--border);
        }

        .favorite-card:hover {
            transform: translateY(-5px);
        }

        .favorite-title {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 8px;
            color: var(--primary);
        }

        .favorite-description {
            font-size: 14px;
            color: var(--text-sub);
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
            background-color: var(--accent);
            color: white;
            border: none;
            border-radius: 8px;
            padding: 6px 10px;
            font-size: 12px;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        .delete-btn:hover {
            background-color: #e85c26;
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
            color: var(--text-sub);
        }

        .pagination strong {
            color: var(--accent);
        }

        .empty-msg {
            text-align: center;
            width: 100%;
            color: #aaa;
            font-size: 15px;
            padding: 40px 0;
        }
    </style>
</head>
<body>
    <!-- ✅ nav는 스타일 영향을 받지 않도록 분리 -->
    <jsp:include page="/WEB-INF/views/nav.jsp" />

    <div class="main-content">
        <h2 class="page-title"><span>내가 찜한</span> 재능 목록</h2>
        <div class="favorites-wrapper">
            <div class="favorites-container">
                <c:forEach var="favorite" items="${myFavoriteList}">
                    <div class="favorite-card" data-talent-id="${favorite.talentId}" onclick="location.href='<c:url value='/talent/view?id=${favorite.talentId}'/>'">
                        <div class="favorite-title">${favorite.title}</div>
                        <div class="favorite-description">${favorite.description}</div>
                        <div class="favorite-meta">
                            <i class="fas fa-tag"></i> ${favorite.category} |
                            <i class="fas fa-clock"></i> ${favorite.timeSlot}분
                        </div>
                        <button class="delete-btn"><i class="fas fa-heart-broken"></i> 삭제</button>
                    </div>
                </c:forEach>

                <c:if test="${empty myFavoriteList}">
                    <p class="empty-msg"><i class="far fa-heart"></i> 찜한 재능이 없습니다.</p>
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
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/footer.jsp" />
    <jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

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
