<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원정보 수정</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
            padding: 30px;
        }
        .edit-container {
            background: white;
            padding: 40px;
            max-width: 600px;
            margin: auto;
            border-radius: 20px;
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
        }
        .form-label {
            font-weight: bold;
            color: #ff69b4;
        }
        .form-control {
            margin-bottom: 20px;
        }
        .btn-primary {
            background-color: #ff66b2;
            border: none;
        }
        .btn-primary:hover {
            background-color: #ff3385;
        }
    </style>
</head>
<body>

<div class="edit-container">
    <h2 class="text-center mb-4">회원정보 수정</h2>

    <form:form modelAttribute="member" method="post" action="${pageContext.request.contextPath}/mypage/edit">
        <!-- 회원 ID (숨김 처리) -->
        <form:hidden path="member_id"/>

        <!-- 이름 -->
        <label class="form-label">이름</label>
        <form:input path="name" cssClass="form-control" />

        <!-- 이메일 -->
        <label class="form-label">이메일</label>
        <form:input path="email" cssClass="form-control" />

        <!-- 전화번호 -->
        <label class="form-label">전화번호</label>
        <form:input path="phone" cssClass="form-control" />

        <!-- 주소 -->
        <label class="form-label">주소</label>
        <form:input path="addr" cssClass="form-control" />


        <!-- 전문가 여부 -->
        <label class="form-label mt-2">전문가입니까?</label>
        <div class="form-check mb-3">
            <form:checkbox path="expert" cssClass="form-check-input"/>
            <span class="form-check-label">예</span>
        </div>

        <!-- 버튼 영역 -->
        <div class="text-center mt-4">
            <button type="submit" class="btn btn-primary me-2">저장하기</button>
            <a href="${pageContext.request.contextPath}/mypage" class="btn btn-secondary">취소</a>
        </div>
    </form:form>
</div>

</body>
</html>
