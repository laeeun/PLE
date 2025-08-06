<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="addurl" value="/addtalent" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>재능 등록 - TimeFair</title>

  <!-- 폰트 & 아이콘 -->
  <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet" />
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />

  <style>
    :root {
      --primary:      #1F2C40;
      --accent:       #FF6B35;
      --accent-100:   #FFEEEA;
      --surface:      #F9F9F9;
      --surface-alt:  #FFFFFF;
      --border:       #E8E8E8;
      --text-main:    #1F2C40;
      --text-sub:     #6A737D;
    }

    * { margin: 0; padding: 0; box-sizing: border-box; }
    body {
      font-family: 'Pretendard', sans-serif;
      background: var(--surface);
      color: var(--text-main);
      line-height: 1.6;
      min-height: 100vh;
    }

    .container {
      max-width: 800px;
      margin: 60px auto;
      background: var(--surface-alt);
      border: 1px solid var(--border);
      border-radius: 16px;
      padding: 50px 40px;
      box-shadow: 0 8px 32px rgba(0, 0, 0, 0.06);
    }

    h2 {
      font-family: 'Montserrat', sans-serif;
      font-size: 36px;
      font-weight: 900;
      color: var(--primary);
      text-align: center;
      margin-bottom: 30px;
    }

    label.form-label {
      font-weight: 600;
      color: var(--text-main) !important;
      margin-bottom: 8px;
      display: block;
    }

    .form-control,
    .form-check-input {
      width: 100%;
      background: rgba(255,255,255,0.6);
      border: 1px solid var(--border);
      border-radius: 10px;
      padding: 10px 14px;
      margin-bottom: 20px;
      font-size: 15px;
      color: var(--text-main) !important;
    }

    .form-check {
      display: flex;
      align-items: center;
      margin-bottom: 12px;
    }

    .form-check-input {
      width: auto;
      margin-right: 8px;
    }

    .form-check-label {
      font-size: 15px;
      color: var(--text-sub) !important;
    }

    .btn-submit {
      display: inline-block;
      background: var(--accent);
      color: #fff;
      border: none;
      border-radius: 14px;
      padding: 14px 36px;
      font-size: 17px;
      font-weight: bold;
      cursor: pointer;
      box-shadow: 0 8px 24px rgba(255, 107, 53, 0.2);
      transition: all 0.3s ease;
    }

    .btn-submit:hover {
      transform: translateY(-2px);
      box-shadow: 0 12px 30px rgba(255, 107, 53, 0.3);
    }

    @media (max-width: 768px) {
      .container {
        margin: 40px 20px;
        padding: 30px 24px;
      }
      h2 {
        font-size: 28px;
      }
    }
    
    .category-grid {
	  display: grid;
	  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
	  gap: 14px 20px;
	}
	
	.category-grid .form-check {
	  display: inline-flex;
	  align-items: center;
	  gap: 8px;
	  margin: 0;
	}
	
	.category-grid .form-check-input {
	  appearance: none;
	  -webkit-appearance: none;
	  width: 16px;
	  height: 16px;
	  border: 2px solid var(--border);
	  border-radius: 50%;
	  background: #fff;
	  position: relative;
	  cursor: pointer;
	  transition: border-color 0.2s ease;
	  vertical-align: middle;
	  margin: 0;
	
	  /* ✅ 포커스시 검정 테두리 제거 */
	  outline: none;
	  box-shadow: none;
	}
	
	.category-grid .form-check-input:checked {
	  border-color: var(--accent);
	  background-color: #fff;
	}
	
	.category-grid .form-check-input:checked::before {
	  content: "";
	  position: absolute;
	  top: 50%;
	  left: 50%;
	  width: 8px;
	  height: 8px;
	  background: var(--accent);
	  border-radius: 50%;
	  transform: translate(-50%, -50%);
	}
	
	.category-grid .form-check-input:hover {
	  border-color: var(--accent);
	}
	
	.category-grid .form-check-label {
	  font-size: 15px;
	  line-height: 1;
	  color: var(--text-sub);
	  cursor: pointer;
	}


	
	label[for="uploadFile"] {
	  margin-top: 20px;
	  display: block;
	}
  </style>
</head>

<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container">
  <h2>재능 등록</h2>

  <form:form modelAttribute="newTalent" method="post" action="${addurl}" enctype="multipart/form-data">

    <!-- 제목 -->
    <label for="title" class="form-label">제목</label>
    <form:input path="title" id="title" cssClass="form-control" required="required"/>

    <!-- 설명 -->
    <label for="description" class="form-label">설명</label>
    <form:textarea path="description" id="description" cssClass="form-control" rows="5"/>

    <!-- ✅ 카테고리 -->
    <div class="mb-4">
  <label class="form-label">카테고리</label>
  <div class="category-grid">
    <div class="form-check"><form:radiobutton path="category" value="디자인" cssClass="form-check-input" id="cat1"/><label for="cat1" class="form-check-label">디자인</label></div>
    <div class="form-check"><form:radiobutton path="category" value="프로그래밍" cssClass="form-check-input" id="cat2"/><label for="cat2" class="form-check-label">프로그래밍</label></div>
    <div class="form-check"><form:radiobutton path="category" value="번역" cssClass="form-check-input" id="cat3"/><label for="cat3" class="form-check-label">번역</label></div>
    <div class="form-check"><form:radiobutton path="category" value="음악" cssClass="form-check-input" id="cat4"/><label for="cat4" class="form-check-label">음악</label></div>
    <div class="form-check"><form:radiobutton path="category" value="영상편집" cssClass="form-check-input" id="cat5"/><label for="cat5" class="form-check-label">영상편집</label></div>
    <div class="form-check"><form:radiobutton path="category" value="글쓰기" cssClass="form-check-input" id="cat6"/><label for="cat6" class="form-check-label">글쓰기</label></div>
    <div class="form-check"><form:radiobutton path="category" value="과외" cssClass="form-check-input" id="cat7"/><label for="cat7" class="form-check-label">과외</label></div>
    <div class="form-check"><form:radiobutton path="category" value="생활서비스" cssClass="form-check-input" id="cat8"/><label for="cat8" class="form-check-label">생활서비스</label></div>
    <div class="form-check"><form:radiobutton path="category" value="기획창작" cssClass="form-check-input" id="cat9"/><label for="cat9" class="form-check-label">기획/창작</label></div>
  </div>
</div>


    <!-- 파일 업로드 -->
    <label for="uploadFile" class="form-label">첨부 파일</label>
    <input type="file" name="uploadFile" id="uploadFile" class="form-control" />

    <!-- 판매 시간 -->
    <label for="timeSlot" class="form-label">판매 시간 (분 단위)</label>
    <input type="number" name="timeSlot" id="timeSlot" class="form-control" min="1" required />

    <!-- 등록 버튼 -->
    <div style="text-align:center; margin-top:40px;">
      <button type="submit" class="btn-submit">등록하기</button>
    </div>
  </form:form>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

</body>
</html>
