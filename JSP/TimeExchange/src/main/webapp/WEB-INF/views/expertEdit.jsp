<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>전문가 정보 수정</title>
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
        .btn-delete {
            margin-left: 10px;
            background-color: #e11d48;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 13px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h3>전문가 정보 수정</h3>
    
    <form action="<c:url value='/mypage/expert/update'/>" method="post" enctype="multipart/form-data">
        
        <label>경력 사항</label>
        <textarea name="career" class="form-control" rows="3">${expertDTO.career}</textarea>
        
        <label>대학교</label>
        <input type="text" name="university" class="form-control" value="${expertDTO.university}">
        
        <label>자격증 및 기타</label>
        <input type="text" name="certification" class="form-control" value="${expertDTO.certification}">
        
        <label>자기소개</label>
        <textarea name="introduction" class="form-control" rows="4">${expertDTO.introduction}</textarea>
        
        <label>기존 업로드 파일 목록 (삭제)</label>
        <ul id="fileList">
            <c:forEach var="file" items="${expertDTO.fileNames}">
                <li id="file-${file.hashCode()}">
                    ${file}
                    <button type="button" class="btn-delete"
                            onclick="deleteFile('${file}', 'file-${file.hashCode()}')">삭제</button>
                </li>
            </c:forEach>
        </ul>

        <label>새 파일 추가 업로드</label>
        <input type="file" name="expertFiles" class="form-control" multiple />

        <input type="hidden" name="memberId" value="${expertDTO.memberId}" />

        <div style="text-align: right;">
            <button type="submit" class="btn-submit">수정 완료</button>
        </div>
    </form>
</div>

<script>
function deleteFile(fileName, elementId) {
    if (!confirm(fileName + " 파일을 삭제하시겠습니까?")) return;

    fetch("${pageContext.request.contextPath}/mypage/expert/deleteFile", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'fileName=' + encodeURIComponent(fileName)
    })
    .then(response => {
        if (response.ok) {
            document.getElementById(elementId).remove();
        } else {
            alert("삭제 실패");
        }
    })
    .catch(error => {
        console.error("삭제 중 오류 발생:", error);
        alert("서버 오류");
    });
}
</script>

</body>
</html>
