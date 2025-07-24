<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="updateUrl" value="/talent/update" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 수정</title>

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
            box-shadow: 0 4px 10px rgba(255, 105, 180, 0.15);
        }

        .card-header {
            background-color: #ff99cc;
            color: white;
            font-weight: bold;
        }

        .form-label {
            color: #ff69b4;
        }

        .btn-success {
            background-color: #ff99cc;
            border-color: #ff99cc;
        }

        .btn-success:hover {
            background-color: #ff66b2;
            border-color: #ff66b2;
        }

        .form-check-label {
            color: #333;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <div class="card">
        <div class="card-header text-white" style="background-color: #ff99cc;">
    		<h4>재능 수정</h4>
		</div>
        <div class="card-body">
            <!-- 기존 form:form 코드 그대로 유지 -->
            <form:form modelAttribute="updateTalent" method="post" action="${updateUrl}">
                <form:hidden path="talent_id"/>

                <div class="mb-3">
                    <label class="form-label">제목</label>
                    <form:input path="title" class="form-control" />
                </div>

                <div class="mb-3">
                    <label class="form-label">설명</label>
                    <form:textarea path="description" rows="5" class="form-control" />
                </div>

                <div class="mb-3">
                    <label class="form-label">카테고리</label><br/>
                    <div class="form-check">
                        <form:radiobutton path="category" value="디자인" class="form-check-input" id="cat1" />
                        <label class="form-check-label" for="cat1">디자인</label>
                    </div>
                    <div class="form-check">
                        <form:radiobutton path="category" value="프로그래밍" class="form-check-input" id="cat2" />
                        <label class="form-check-label" for="cat2">프로그래밍</label>
                    </div>
                    <div class="form-check">
                        <form:radiobutton path="category" value="번역" class="form-check-input" id="cat3" />
                        <label class="form-check-label" for="cat3">번역</label>
                    </div>
                    <div class="form-check">
                        <form:radiobutton path="category" value="영상제작" class="form-check-input" id="cat4" />
                        <label class="form-check-label" for="cat4">영상제작</label>
                    </div>
                    <div class="form-check">
                        <form:radiobutton path="category" value="상담/코칭" class="form-check-input" id="cat5" />
                        <label class="form-check-label" for="cat5">상담/코칭</label>
                    </div>
                    <div class="form-check">
                        <form:radiobutton path="category" value="기타" class="form-check-input" id="cat6" />
                        <label class="form-check-label" for="cat6">기타</label>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">판매 시간 (분)</label>
                    <form:input path="timeSlot" type="number" class="form-control" />
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-success">수정하기</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
