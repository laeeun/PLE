<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${member.userName}님의 마이페이지</title>

    <!-- 감성 폰트 Pretendard -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <!-- Bootstrap -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
        }

        .card {
            border: 1px solid #ffcce0;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(255, 105, 180, 0.1);
            margin-bottom: 2rem;
        }

        .card-header {
            background-color: #ff99cc;
        }

        .card-header h4 {
            color: #ff69b4;
            margin-bottom: 0;
        }

        p {
            font-size: 1rem;
            margin: 0.5rem 0;
        }

        strong {
            color: #ff69b4;
        }

        .talent-list li {
            margin: 0.75rem 0;
        }

        .talent-title {
            font-weight: 600;
            color: #d63384;
            text-decoration: none;
        }

        .talent-title:hover {
            text-decoration: underline;
        }

        .talent-category {
            font-size: 0.9rem;
            color: #888;
            margin-left: 8px;
        }

        .talent-date {
            font-size: 0.85rem;
            color: #aaa;
            margin-left: 12px;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <!-- 프로필 카드 -->
    <div class="card">
        <div class="card-header">
            <h4>${member.userName}님의 프로필</h4>
        </div>
        <div class="card-body">
            <p><strong>ID:</strong> ${member.member_id}</p>
            <p><strong>닉네임:</strong> ${member.userName}</p>
            <p><strong>이름:</strong> ${member.name}</p>
            <p><strong>이메일:</strong> ${member.email}</p>
        </div>
    </div>

    <!-- 재능 목록 카드 -->
    <div class="card">
        <div class="card-header">
            <h4>${member.userName}님의 재능 목록</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${not empty talentlist}">
                    <ul class="talent-list list-unstyled">
                        <c:forEach var="talent" items="${talentlist}">
                            <li>
                                <a class="talent-title" href="<c:url value='/talent/view?id=${talent.talent_id}' />">
                                    ${talent.title}
                                </a>
                                <span class="talent-category">[${talent.category}]</span>
                                <span class="talent-date">| 등록일: ${talent.formattedCreatedAt}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p>등록된 재능이 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
