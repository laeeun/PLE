<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 랭킹</title>

    <!-- Pretendard 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <!-- Bootstrap -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
        }

        h2 {
            color: #ff69b4;
            margin-bottom: 30px;
        }

        table {
            background-color: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(255, 105, 180, 0.1);
        }

        thead {
            background-color: #ffb6d9;
            color: white;
        }

        th, td {
            text-align: center;
            vertical-align: middle;
        }

        .btn-outline-primary {
            color: #ff69b4;
            border-color: #ff69b4;
        }

        .btn-outline-primary:hover {
            background-color: #ff69b4;
            color: white;
        }

        a.username-link, a.talent-link {
            color: #ff69b4;
            text-decoration: none;
        }

        a.username-link:hover, a.talent-link:hover {
            color: #ff3366;
            text-decoration: underline;
        }

        .category-select {
        max-width: 200px;
        border-radius: 12px;
        padding: 8px 12px;
        border: 1px solid #ffb6d9;
        background-color: #fff0f5;
        color: #ff69b4;
        font-family: 'Pretendard', sans-serif;
        font-size: 16px;
        transition: border-color 0.3s, box-shadow 0.3s;
        box-shadow: none;
    	}

	    .category-select:focus {
	        border-color: #ff69b4;
	        outline: none;
	        box-shadow: 0 0 0 0.2rem rgba(255, 105, 180, 0.25);
	    }
	
	    .category-select option {
	        background-color: white;
	        color: #ff69b4;
	    }
    </style>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container py-5">
    <h2 class="text-center">재능 랭킹 TOP 10</h2>

    <!-- 카테고리 선택 -->
    <select class="form-select category-select"
        onchange="location.href='${contextPath}/ranking/' + this.value;">
	    <option value="">전체</option>
	    <option value="design">디자인</option>
	    <option value="programming">프로그래밍</option>
	    <option value="translation">번역</option>
	    <option value="music">음악</option>
	    <option value="video">영상편집</option>
	    <option value="writing">글쓰기</option>
	    <option value="tutoring">과외</option>
	    <option value="lifestyle">생활서비스</option>
	    <option value="creative">기획창작</option>
	</select>

    <!-- 랭킹 테이블 -->
    <c:choose>
        <c:when test="${not empty ranklist}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>순위</th>
                        <th>재능 제목</th>
                        <th>판매자</th>
                        <th>판매 수</th>
                        <th>점수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rank" items="${ranklist}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>
                                <a class="talent-link" href="<c:url value='/talent/view?id=${rank.talent_id}' />">${rank.title}</a>
                            </td>
                            <td>
                                <a class="username-link" href="<c:url value='/profile?id=${rank.member_id}' />">${rank.username}</a>
                            </td>
                            <td>${rank.total_sales}</td>
                            <td>${rank.score}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning text-center">랭킹 정보가 없습니다.</div>
        </c:otherwise>
    </c:choose>
</div>

<!-- 푸터 -->
<div class="text-center mt-5 mb-3 text-muted">
    <jsp:include page="/WEB-INF/views/footer.jsp" />
</div>

</body>
</html>
