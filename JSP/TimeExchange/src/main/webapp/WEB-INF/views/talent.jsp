<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <style>
        :root {
            --primary:      #1F2C40;
            --accent:       #FF6B35;
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
            min-height: 100vh;
            padding-bottom: 50px;
        }

        h5, h4 {
            color: var(--primary);
            font-weight: bold;
            margin-bottom: 1rem;
        }

        .card {
            border: 1px solid var(--border);
            border-radius: 16px;
            background: var(--surface-alt);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.05);
            transition: all 0.3s ease;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
        }

        .card-title a {
            color: var(--primary);
            font-weight: 700;
            text-decoration: none;
        }

        .card-title a:hover {
            color: var(--accent);
            text-decoration: underline;
        }

        .btn,
        .search-btn,
        .pastel-register-btn {
            border: none;
            border-radius: 12px;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .search-btn, .pastel-register-btn {
            background: var(--accent-100);
            color: var(--accent);
            padding: 10px 20px;
            box-shadow: 0 4px 12px rgba(255, 107, 53, 0.2);
        }

        .search-btn:hover, .pastel-register-btn:hover {
            background: var(--accent);
            color: #fff;
            transform: translateY(-2px);
        }

        .list-group-item,
        .list-group-item-action {
            background: rgba(255, 255, 255, 0.7);
            border: none;
            font-weight: 500;
            border-radius: 12px;
            margin-bottom: 8px;
            color: var(--text-main);
            transition: all 0.3s ease;
        }

        .list-group-item:hover {
            background: var(--accent-100);
            color: var(--primary);
        }

        .list-group-item.active {
            background: var(--accent);
            color: #fff;
            font-weight: bold;
        }

        .pagination .page-item.active .page-link {
            background-color: var(--accent);
            border-color: var(--accent);
            color: white;
        }

        .page-link {
            color: var(--accent);
            border: 1px solid var(--accent-100);
        }

        .page-link:hover {
            background-color: var(--accent-100);
            color: var(--primary);
        }

        .expert-badge {
            position: absolute;
            top: 12px;
            right: 12px;
            background: var(--accent-100);
            color: var(--accent);
            font-size: 0.75rem;
            font-weight: 700;
            padding: 6px 10px;
            border-radius: 12px;
            border: 1px solid var(--border);
        }

        .favorite-indicator {
            position: absolute;
            bottom: 14px;
            right: 14px;
            font-size: 1.4rem;
            color: #ec4899;
        }
        
		a {
		  position: relative;
		  text-decoration: none !important;
 		  color: inherit !important;
		  transition: color 0.2s ease;
		}
		
	
		
		.search-btn {
		  background-color: var(--accent);
		  color: white;
		  border: none;
		  padding: 0 20px;
		  font-size: 14px;
		  font-weight: 500;
		  cursor: pointer;
		  transition: background-color 0.2s ease;
		  font-family: 'Pretendard', sans-serif;
		}
		
		
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<div class="container mt-5">
    <div class="row">
        <div class="col-md-3">
            <h5>카테고리</h5>
            <div class="list-group mb-4">
                <a href="<c:url value='/talent?category=' />" class="list-group-item ${empty category ? 'active' : ''}">전체</a>
                <c:forEach items="${fn:split('디자인,프로그래밍,번역,음악,영상편집,글쓰기,과외,생활서비스,기획창작', ',')}" var="cat">
                    <a href="<c:url value='/talent?category=${cat}' />" class="list-group-item ${category == cat ? 'active' : ''}">${cat}</a>
                </c:forEach>
            </div>
            <h5>전문가 여부</h5>
            <div class="list-group mb-3">
                <a href="<c:url value='/talent?expert=all&category=${category}&keyword=${keyword}' />" class="list-group-item ${expert == 'all' ? 'active' : ''}">전체</a>
                <a href="<c:url value='/talent?expert=true&category=${category}&keyword=${keyword}' />" class="list-group-item ${expert == 'true' ? 'active' : ''}">전문가</a>
                <a href="<c:url value='/talent?expert=false&category=${category}&keyword=${keyword}' />" class="list-group-item ${expert == 'false' ? 'active' : ''}">일반</a>
            </div>
            <c:if test="${not empty sessionScope.loggedInUser}">
                <a href="<c:url value='/addtalent' />" class="pastel-register-btn">재능 등록</a>
            </c:if>
        </div>

        <div class="col-md-9">
            <form class="mb-4" action="<c:url value='/talent' />" method="get">
                <input type="hidden" name="category" value="${category}" />
                <input type="hidden" name="expert" value="${expert}" />
                <div class="input-group">
                    <input type="text" name="keyword" class="form-control" placeholder="검색어를 입력하세요" value="${keyword}" />
                    <button type="submit" class="search-btn">검색</button>
                </div>

            </form>

            <div class="row">
                <c:choose>
                    <c:when test="${not empty talentList}">
                        <c:forEach var="dto" items="${talentList}">
                            <div class="col-md-6 mb-4">
                                <div class="card h-100 position-relative">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <a href="<c:url value='/talent/view?id=${dto.talent_id}' />">${dto.title}</a>
                                        </h5>
                                        <c:if test="${dto.expert}">
                                            <div class="expert-badge">전문가</div>
                                        </c:if>
                                        <c:if test="${dto.favorite}">
                                            <span class="favorite-indicator">
                                                <i class="fas fa-heart"></i>
                                            </span>
                                        </c:if>
                                        <p>카테고리: ${dto.category}</p>
                                        <p>작성자: ${dto.username}</p>
                                        <p>판매 시간: ${dto.timeSlotDisplay}</p>
                                        <p>등록일: ${dto.createdAtDisplay}</p>
                                        <p>⭐ 평균 별점: ${dto.averageRating}점 (${dto.reviewCount}개)</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12 d-flex justify-content-center align-items-center" style="height: 200px;">
                            <p class="fs-4 text-muted">표시할 재능이 없습니다.</p>
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
                                    <a class="page-link" href="<c:url value='/talent?page=${i}&size=6&expert=${expert}&category=${category}&keyword=${keyword}' />">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
