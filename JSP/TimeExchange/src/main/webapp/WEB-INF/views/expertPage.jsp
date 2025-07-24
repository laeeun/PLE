<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 게시판</title>

    <!-- 감성 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        /* ✅ 파란 라인 완전 제거 */
        a.list-group-item:focus,
        a.list-group-item:focus-visible,
        a.list-group-item:active,
        a.list-group-item.active:focus,
        a.list-group-item.active:focus-visible,
        a.list-group-item.active:active {
            border: none !important;
            outline: none !important;
            box-shadow: none !important;
            border-color: transparent !important;
            background-color: inherit !important;
            color: inherit !important;
        }

        input:focus, textarea:focus, select:focus, button:focus,
        .form-control:focus, .form-select:focus {
            outline: none !important;
            box-shadow: none !important;
            border-color: transparent !important;
            background-color: rgba(255,255,255,0.65);
        }

        h5 {
            color: #6b21a8;
            font-weight: bold;
        }

        .card {
            border: none;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 18px rgba(0, 0, 0, 0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 28px rgba(0, 0, 0, 0.15);
        }

        .card-title a {
            color: #7e22ce;
            text-decoration: none;
        }

        .card-title a:hover {
            color: #6b21a8;
            text-decoration: underline;
        }

        .list-group-item {
            background-color: rgba(255, 255, 255, 0.5);
            border: none;
            border-radius: 12px;
            margin-bottom: 8px;
            color: #4c1d95;
            font-weight: 500;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            backdrop-filter: blur(6px);
            transition: all 0.25s ease;
            position: relative;
            overflow: hidden;
        }

        .list-group-item:hover {
            background-color: #f3e8ff;
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
        }

        .list-group-item.active {
            background: linear-gradient(to right, #d946ef, #a855f7);
            color: white;
            font-weight: bold;
            box-shadow: 0 6px 14px rgba(168, 85, 247, 0.4);
        }

        .list-group-item::after {
            content: "";
            position: absolute;
            top: 0;
            left: -100%;
            width: 60%;
            height: 100%;
            background: linear-gradient(to right, rgba(255,255,255,0.4), rgba(255,255,255,0));
            transform: skewX(-25deg);
            transition: all 0.5s ease;
            pointer-events: none;
        }

        .list-group-item:hover::after {
            left: 130%;
        }

        .pastel-register-btn {
            background: linear-gradient(135deg, #f3e8ff, #fbcfe8);
            color: #6b21a8;
            font-weight: 600;
            border: 1.5px solid rgba(255, 255, 255, 0.5);
            border-radius: 12px;
            padding: 14px 16px;
            font-size: 16px;
            text-align: center;
            width: 100%;
            box-shadow: 0 4px 12px rgba(174, 182, 255, 0.3);
            position: relative;
            overflow: hidden;
            transition: all 0.35s ease;
        }

        .pastel-register-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(245, 183, 255, 0.5);
        }

        .input-group {
            display: flex;
            width: 100%;
            border-radius: 14px;
            overflow: hidden;
            background: rgba(255, 255, 255, 0.4);
            backdrop-filter: blur(8px);
            box-shadow: 0 6px 20px rgba(245, 183, 255, 0.15);
        }

        .input-group input {
            flex: 1;
            border: none;
            padding: 12px 16px;
            font-size: 15px;
            color: #6b21a8;
            background: transparent;
        }

        .input-group input::placeholder {
            color: #b48fdb;
        }

        .search-btn {
            border: none;
            padding: 12px 20px;
            font-size: 15px;
            font-weight: 600;
            color: #6b21a8;
            background: linear-gradient(to right, #d8b4fe, #fbcfe8);
            transition: all 0.3s ease;
            cursor: pointer;
            border-left: 1px solid rgba(255,255,255,0.4);
        }

        .search-btn:hover {
            background: linear-gradient(to right, #c084fc, #f9a8d4);
        }

        .pagination .page-item.active .page-link {
            background-color: #a855f7;
            border-color: #a855f7;
            color: white;
        }

        .page-link {
            color: #7e22ce;
            border: 1px solid #f3d1fb;
        }

        .page-link:hover {
            background-color: #f3e8ff;
            color: #6b21a8;
            border-color: #c084fc;
        }

        .fs-4.text-muted {
            font-size: 1.25rem;
            color: #888;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-4">
    <div class="row align-items-start">
        <div class="col-md-3">
            <h5 class="mb-3">카테고리</h5>
            <div class="list-group mb-3">
                <a href="<c:url value='/expert/전체' />" class="list-group-item ${category == '전체' ? 'active' : ''}">전체</a>
                <a href="<c:url value='/expert/디자인' />" class="list-group-item ${category == '디자인' ? 'active' : ''}">디자인</a>
                <a href="<c:url value='/expert/프로그래밍' />" class="list-group-item ${category == '프로그래밍' ? 'active' : ''}">프로그래밍</a>
                <a href="<c:url value='/expert/마케팅' />" class="list-group-item ${category == '마케팅' ? 'active' : ''}">마케팅</a>
                <a href="<c:url value='/expert/상담' />" class="list-group-item ${category == '상담' ? 'active' : ''}">상담</a>
                <a href="<c:url value='/expert/기획' />" class="list-group-item ${category == '기획' ? 'active' : ''}">기획</a>
            </div>
            <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.expert}">
                <a href="<c:url value='/expert/add' />" class="pastel-register-btn">➕ 전문가 등록</a>
            </c:if>
        </div>

        <div class="col-md-9">
            <form class="mb-4" action="<c:url value='/expert/search' />" method="get">
                <div class="input-group">
                    <input type="text" name="keyword" placeholder="전문가를 검색하세요" value="${keyword}" required />
                    <button class="search-btn" type="submit">검색</button>
                </div>
            </form>

            <div class="row">
                <c:choose>
                    <c:when test="${not empty expertList}">
                        <c:forEach var="dto" items="${expertList}">
                            <div class="col-md-6 mb-4">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <a href="<c:url value='/expert/view?id=${dto.expert_board_id}' />">${dto.title}</a>
                                        </h5>
                                        <p>${dto.content}</p>
                                        <p>카테고리: ${dto.category}</p>
                                        <p>닉네임: ${dto.expert_id}</p>
                                        <p>예약 가능 시간: ${dto.available_time}</p>
                                        <p>가격: ${dto.price}원</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12 d-flex justify-content-center align-items-center" style="height: 200px;">
                            <p class="fs-4 text-muted">표시할 전문가가 없습니다.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${totalPages > 1}">
                <div class="d-flex justify-content-center mt-4">
                    <nav>
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&category=${category}${isSearch ? '&keyword=' + keyword : ''}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
