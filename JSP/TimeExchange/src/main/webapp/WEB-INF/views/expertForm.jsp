<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 정보 입력</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #e0f2fe, #f3e8ff);
            padding: 40px;
        }
        .form-container {
            background: rgba(255, 255, 255, 0.85);
            padding: 30px;
            border-radius: 20px;
            max-width: 700px;
            margin: 0 auto;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
        }
        label {
            font-weight: 600;
            color: #6b21a8;
        }
        .form-control {
            margin-bottom: 15px;
        }
        .btn-submit {
            background: linear-gradient(to right, #6366f1, #8b5cf6);
            border: none;
            color: white;
            font-weight: bold;
            padding: 10px 20px;
            border-radius: 8px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h3>전문가 추가 정보 입력</h3>
    
    <form action="<c:url value='/signUp/expertSubmit'/>" method="post" enctype="multipart/form-data">
        
        <label>경력 사항</label>
        <textarea name="career" class="form-control" rows="3" placeholder="예: 5년간 디자인 프리랜서 경험"></textarea>
        
        <label>대학교</label>
        <input type="text" name="university" class="form-control" placeholder="예: 한양대학교 디자인학부">
        
        <label>자격증 및 기타</label>
        <input type="text" name="certification" class="form-control" placeholder="예: GTQ 1급, 정보처리기사 등">
        
        <label>자기소개</label>
		<textarea name="introduction" class="form-control" rows="4" placeholder="예: 안녕하세요! 디자인 분야에서 5년간 활동해왔으며, 사용자 중심의 UI/UX 설계에 강점을 가지고 있습니다."></textarea>
        
        <label>관련 증빙 파일 첨부</label>
        <input type="file" name="expertFiles" class="form-control" multiple />

        <input type="hidden" name="memberId" value="${memberId}" />

        <div style="text-align: right;">
            <button type="submit" class="btn-submit">제출하기</button>
        </div>
    </form>
</div>

</body>
</html>
