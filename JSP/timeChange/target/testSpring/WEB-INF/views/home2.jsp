<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시간 거래소 메인</title>
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
        }
        .hero-section {
            height: 80vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            background: linear-gradient(to right, #e0f7fa, #ffffff);
        }
        .sliding-text {
            font-size: 2.5rem;
            font-weight: bold;
            color: #343a40;
            transition: opacity 0.8s ease-in-out;
            opacity: 1;
        }
        .sliding-text.fade-out {
            opacity: 0;
        }
        .start-btn {
            margin-top: 30px;
            padding: 14px 32px;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="hero-section">
    <h1 id="heroText" class="sliding-text">재능을 사고팔고, 시간을 나누세요</h1>

    <c:choose>
        <c:when test="${not empty sessionScope.loginId}">
            <a href="<c:url value='/talent' />" class="btn btn-primary btn-lg start-btn">지금 시작하기</a>
        </c:when>
        <c:otherwise>
            <a href="<c:url value='/login' />" class="btn btn-outline-primary btn-lg start-btn">로그인하고 시작하기</a>
        </c:otherwise>
    </c:choose>
</div>

<script>
    const texts = [
        "재능을 사고팔고, 시간을 나누세요",
        "당신의 능력을 공유해보세요",
        "시간으로 가치를 거래하세요",
        "지금 나의 재능을 판매해보세요",
        "능력으로 커뮤니티에 기여하세요"
    ];

    let index = 0;
    const heroText = document.getElementById("heroText");

    setInterval(() => {
        heroText.classList.add("fade-out");

        setTimeout(() => {
            index = (index + 1) % texts.length;
            heroText.innerText = texts[index];
            heroText.classList.remove("fade-out");
        }, 800);
    }, 4000);
</script>

</body>
</html>
