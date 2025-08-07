<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url var="updateUrl" value="/talent/update" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }

        body {
            background-color: var(--surface);
            font-family: 'Pretendard', sans-serif;
            color: var(--text-main);
        }

        .container {
            max-width: 800px;
        }

        .card {
            border: none;
            border-radius: 16px;
            box-shadow: 0 4px 16px rgba(31, 44, 64, 0.1);
            background-color: var(--surface-alt);
        }

        .card-header {
            border-top-left-radius: 16px;
            border-top-right-radius: 16px;
            background-color: var(--primary) !important;
            color: white;
            padding: 1rem 1.5rem;
        }

        .card-header h4 {
            margin: 0;
            font-weight: bold;
        }

        .card-body {
            padding: 2rem;
        }

        label.form-label {
            font-weight: 600;
            color: var(--text-main);
            margin-bottom: 0.5rem;
            display: block;
        }

        .form-control {
            border-radius: 10px;
            border: 1px solid var(--border);
            font-size: 15px;
            padding: 10px 14px;
        }

        .form-control:focus {
            box-shadow: 0 0 0 2px rgba(255, 107, 53, 0.2);
            border-color: var(--accent);
        }

        img[alt="첨부 이미지"] {
            margin-top: 10px;
            border: 1px solid var(--border);
            max-width: 100%;
            height: auto;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
        }

        a[download] {
            color: var(--accent);
            font-weight: 500;
            display: inline-block;
            margin-top: 6px;
        }

        .btn-success {
            background-color: var(--accent);
            border: none;
            padding: 10px 20px;
            font-weight: 600;
            font-size: 15px;
            border-radius: 12px;
            transition: background-color 0.2s;
        }

        .btn-success:hover {
            background-color: #e65b28;
        }

        .custom-file-btn {
            padding: 8px 16px;
            font-weight: 500;
            font-size: 14px;
            border-radius: 8px;
            background-color: var(--surface-alt);
            border: 1px solid var(--border);
            color: var(--text-main);
            cursor: pointer;
            transition: background-color 0.2s ease;
        }

        .custom-file-btn:hover {
            background-color: var(--accent-100);
            color: var(--accent);
            border-color: var(--accent);
        }

        .custom-radio-group {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            margin-top: 8px;
        }

        .custom-radio-card {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 12px 20px;
            border-radius: 16px;
            border: 2px solid var(--border);
            background-color: var(--surface-alt);
            font-weight: 500;
            font-size: 14px;
            color: var(--text-main);
            cursor: pointer;
            transition: all 0.3s ease;
            min-width: 110px;
            text-align: center;
            position: relative;
            user-select: none;
            box-sizing: border-box;
        }

        .custom-radio-card:hover {
            background-color: var(--accent-100);
            border-color: var(--accent);
            color: var(--accent);
        }

        .custom-radio-card input[type="radio"]:checked ~ * {
		    color: white; /* fallback */
		}
		
		/* ✅ 선택된 상태에서 label 강조 */
		.custom-radio-card input[type="radio"]:checked + label,
		.custom-radio-card.selected {
		    background-color: var(--accent);
		    color: white;
		    font-weight: bold;
		    box-shadow: 0 0 0 2px rgba(255, 107, 53, 0.3);
		    border-color: var(--accent);
		}
        .custom-radio-card.selected {
		    background-color: var(--accent);
		    color: white;
		    font-weight: bold;
		    box-shadow: 0 0 0 2px rgba(255, 107, 53, 0.3);
		}
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <div class="card">
        <div class="card-header text-white">
            <h4>재능 수정</h4>
        </div>
        <div class="card-body">
            <form:form modelAttribute="updateTalent" method="post" action="${updateUrl}" enctype="multipart/form-data">
                <form:hidden path="talent_id"/>
                <form:hidden path="filename"/>

                <div class="mb-3">
                    <label class="form-label">제목</label>
                    <form:input path="title" class="form-control" required="required"/>
                </div>

                <div class="mb-3">
                    <label class="form-label">설명</label>
                    <form:textarea path="description" rows="5" class="form-control" />
                </div>

                <div class="mb-3">
                    <label class="form-label">카테고리</label>
                    <div class="custom-radio-group">
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="디자인" style="display:none;" />
                            <span>디자인</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="프로그래밍" style="display:none;" />
                            <span>프로그래밍</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="번역" style="display:none;" />
                            <span>번역</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="음악" style="display:none;" />
                            <span>음악</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="영상편집" style="display:none;" />
                            <span>영상편집</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="글쓰기" style="display:none;" />
                            <span>글쓰기</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="과외" style="display:none;" />
                            <span>과외</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="생활서비스" style="display:none;" />
                            <span>생활서비스</span>
                        </label>
                        <label class="custom-radio-card">
                            <form:radiobutton path="category" value="기획창작" style="display:none;" />
                            <span>기획창작</span>
                        </label>
                    </div>
                </div>

                <c:if test="${not empty updateTalent.filename}">
                    <div class="mb-3">
                        <label class="form-label">기존 첨부 파일</label><br/>
                        <c:choose>
                            <c:when test="${fn:endsWith(updateTalent.filename, '.jpg') || fn:endsWith(updateTalent.filename, '.jpeg') || fn:endsWith(updateTalent.filename, '.png') || fn:endsWith(updateTalent.filename, '.gif')}">
                                <img src="<c:url value='/resources/uploads/${updateTalent.filename}' />" alt="첨부 이미지" />
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/resources/uploads/${updateTalent.filename}' />" download>
                                    ${updateTalent.filename}
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:if>

                <div class="mb-3">
                    <label class="form-label">첨부 파일 변경</label><br/>
                    <label for="uploadFile" class="btn btn-outline-secondary custom-file-btn">파일 선택</label>
                    <input type="file" name="uploadFile" id="uploadFile" class="form-control d-none" />
                    <span id="selectedFileName" class="ms-2 text-muted">선택된 파일 없음</span>
                    <small class="text-muted d-block mt-1">※ 파일을 선택하지 않으면 기존 파일이 유지됩니다</small>
                </div>

                <div class="mb-3">
                    <label class="form-label">판매 시간 (분)</label>
                    <form:input path="timeSlot" type="number" class="form-control" id="timeSlot" min="1" required="required" />
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-success">수정하기</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>
    document.getElementById('uploadFile').addEventListener('change', function (e) {
        const fileNameSpan = document.getElementById('selectedFileName');
        if (this.files.length > 0) {
            fileNameSpan.textContent = this.files[0].name;
        } else {
            fileNameSpan.textContent = "선택된 파일 없음";
        }
    });
    document.querySelectorAll('.custom-radio-card input[type="radio"]').forEach(rb => {
        rb.addEventListener('change', function () {
            document.querySelectorAll('.custom-radio-card').forEach(label => label.classList.remove('selected'));
            this.closest('label').classList.add('selected');
        });
    });
</script>
</body>
</html>
