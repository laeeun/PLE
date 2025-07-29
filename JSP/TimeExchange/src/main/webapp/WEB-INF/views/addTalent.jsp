<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="addurl" value="/addtalent" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>재능 등록</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    
    <style>
    body {
        font-family: 'Pretendard', sans-serif;
        background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
        background-size: 400% 400%;
        animation: gradientBG 15s ease infinite;
        min-height: 100vh;
        padding-top: 40px;
    }

    @keyframes gradientBG {
        0% { background-position: 0% 50%; }
        50% { background-position: 100% 50%; }
        100% { background-position: 0% 50%; }
    }

    h2 {
        color: #6b21a8;
        font-weight: bold;
        margin-bottom: 30px;
        animation: fadeIn 1s ease;
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(-10px); }
        to { opacity: 1; transform: translateY(0); }
    }

    .container {
        max-width: 800px;
        background: rgba(255, 255, 255, 0.65);
        border-radius: 20px;
        padding: 40px;
        box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        backdrop-filter: blur(12px);
    }

    .form-label {
        font-weight: 600;
        color: #4c1d95;
    }

    .form-control,
    .form-check-input {
        background-color: rgba(255, 255, 255, 0.55);
        border: none;
        backdrop-filter: blur(6px);
        color: #6b21a8;
    }

    .form-control:focus,
    .form-check-input:focus {
        outline: none;
        box-shadow: none;
        border-color: #d8b4fe;
    }

    .form-check-label {
        color: #6b21a8;
        font-weight: 500;
    }

    .btn-primary {
        background: linear-gradient(to right, #a855f7, #ec4899);
        border: none;
        color: white;
        font-weight: 600;
        border-radius: 12px;
        box-shadow: 0 0 12px rgba(168, 85, 247, 0.4);
        transition: all 0.3s ease;
    }

    .btn-primary:hover {
        transform: translateY(-2px);
        box-shadow: 0 0 18px rgba(236, 72, 153, 0.4);
    }

    .btn-primary:focus {
        outline: none;
        box-shadow: 0 0 0 0.2rem rgba(168, 85, 247, 0.3);
    }
</style>
    
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<div class="container mt-5">
    <h2 class="mb-4">재능 등록</h2>

    <form:form modelAttribute="newTalent"
           method="post"
           action="${addurl}"
           enctype="multipart/form-data">

        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <form:input path="title" cssClass="form-control" id="title" required="required"/>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">설명</label>
            <form:textarea path="description" rows="5" cssClass="form-control" id="description"/>
        </div>
		
        <div class="mb-3">
            <label class="form-label">카테고리</label>
            <div class="form-check">
                <form:radiobutton path="category" value="디자인" cssClass="form-check-input" id="cat1" required="required"/>
                <label for="cat1" class="form-check-label">디자인</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="프로그래밍" cssClass="form-check-input" id="cat2"/>
                <label for="cat2" class="form-check-label">프로그래밍</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="번역" cssClass="form-check-input" id="cat3"/>
                <label for="cat3" class="form-check-label">번역</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="음악" cssClass="form-check-input" id="cat4"/>
                <label for="cat4" class="form-check-label">음악</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="영상편집" cssClass="form-check-input" id="cat5"/>
                <label for="cat5" class="form-check-label">영상편집</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="글쓰기" cssClass="form-check-input" id="cat6"/>
                <label for="cat6" class="form-check-label">글쓰기</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="과외" cssClass="form-check-input" id="cat7"/>
                <label for="cat7" class="form-check-label">과외</label>
            </div>
            <div class="form-check">
                <form:radiobutton path="category" value="생활서비스" cssClass="form-check-input" id="cat8"/>
                <label for="cat8" class="form-check-label">생활서비스</label>
            </div>
            <div class="form-check mb-3">
                <form:radiobutton path="category" value="기획창작" cssClass="form-check-input" id="cat9"/>
                <label for="cat9" class="form-check-label">기획/창작</label>
            </div>
        </div>
				
	    <div class="mb-3">
	        <label for="uploadFile" class="form-label">첨부 파일</label>
	        <input type="file" name="uploadFile" id="uploadFile" class="form-control" />
	    </div>
		
        <div class="mb-3">
            <label for="timeSlot" class="form-label">판매 시간 (분 단위)</label>
            <input type="number" name="timeSlot" class="form-control" id="timeSlot" min="1" required />
        </div>
		
        <button type="submit" class="btn btn-primary">등록하기</button>
    </form:form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
