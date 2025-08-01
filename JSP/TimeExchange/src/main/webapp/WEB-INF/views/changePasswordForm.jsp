<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경</title>

    <!-- 감성 폰트 Pretendard -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fbcfe8, #e0e7ff, #f3e8ff, #fce7f3);
            background-size: 400% 400%;
            animation: gradientBG 12s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .form-box {
            background: rgba(255, 255, 255, 0.65);
            backdrop-filter: blur(15px);
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 480px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #6b21a8;
            font-weight: bold;
        }

        .form-label {
            color: #9333ea;
            font-weight: 600;
        }

        .form-control {
            margin-bottom: 20px;
            border-radius: 10px;
        }

        .btn-gradient {
            background: linear-gradient(to right, #38bdf8, #6366f1);
            color: white;
            border: none;
            padding: 12px;
            width: 100%;
            font-weight: bold;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(99, 102, 241, 0.4);
            transition: all 0.3s ease;
        }

        .btn-gradient:hover {
            transform: translateY(-2px);
            opacity: 0.95;
        }

        .alert {
            text-align: center;
            color: #ef4444;
            margin-bottom: 15px;
        }

        .success {
            color: #10b981;
        }
    </style>
</head>
<body>

<div class="form-box">
    <h2>비밀번호 변경</h2>

    <!-- 에러 또는 성공 메시지 출력 -->
    <c:if test="${not empty error}">
        <div class="alert">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/mypage/changePw" method="post">
    <label class="form-label">현재 비밀번호</label>
    <input type="password" name="currentPw" class="form-control" required>

    <label class="form-label">새 비밀번호</label>
    <input type="password" name="newPw" class="form-control" required>

    <label class="form-label">비밀번호 확인</label>
    <input type="password" name="pwConfirm" class="form-control" required>

    <button type="submit" class="btn-gradient">변경하기</button>
</form>
</div>

<c:if test="${not empty error}">
<script>
    alert('${fn:escapeXml(error)}');
</script>
</c:if>

<c:if test="${not empty success}">
<script>
    alert('${fn:escapeXml(success)}');
</script>
</c:if>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
