<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원정보 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .edit-box {
            width: 100%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            padding: 40px 35px;
        }

        .edit-box h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #7e22ce;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            font-weight: 500;
            margin-bottom: 6px;
            display: block;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.6);
            backdrop-filter: blur(6px);
            font-size: 14px;
            color: #4c1d95;
            box-shadow: inset 0 1px 2px rgba(0,0,0,0.1);
        }

        .btn-purple {
            width: 100%;
            padding: 14px;
            background: linear-gradient(to right, #a855f7, #ec4899);
            border: none;
            color: white;
            font-weight: bold;
            font-size: 16px;
            border-radius: 12px;
            box-shadow: 0 0 14px rgba(168, 85, 247, 0.4);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .btn-purple:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(168, 85, 247, 0.5);
        }

        .row-flex {
            display: flex;
            gap: 10px;
        }
    </style>
</head>
<body>

<div class="edit-box">
    <h2>회원정보 수정</h2>
    <form:form modelAttribute="member" method="post" action="${pageContext.request.contextPath}/mypage/edit">

        <form:hidden path="member_id"/>

        <div class="form-group">
            <label for="name">닉네임</label>
            <form:input path="userName" class="form-control" placeholder="닉네임"/>
        </div>

        <div class="form-group">
            <label for="name">이름</label>
            <form:input path="name" class="form-control" placeholder="이름"/>
        </div>

        <div class="form-group">
            <label for="birthDate">생년월일</label>
            <form:input path="birthDate" id="birthDate" class="form-control" readonly="true"/>
        </div>

        <div class="form-group">
            <label for="gender">성별</label>
            <form:select path="gender" class="form-control">
                <form:option value="">선택</form:option>
                <form:option value="M">남성</form:option>
                <form:option value="F">여성</form:option>
            </form:select>
        </div>

        <div class="form-group">
		    <label>전화번호</label>
		    <div class="row-flex">
		        <select name="phone1" class="form-control" style="flex:1">
		            <option value="">선택</option>
		            <option value="010">010</option>
		            <option value="011">011</option>
		            <option value="016">016</option>
		            <option value="017">017</option>
		            <option value="018">018</option>
		            <option value="019">019</option>
		        </select>
		         <!-- 중간번호: 직접입력 -->
		        <input type="text" name="phone2" placeholder="1234"
		               pattern="\d{3,4}" minlength="3" maxlength="4"
		               class="form-control" style="flex: 1;" required />
		
		        <!-- 뒷번호: 직접입력 -->
		        <input type="text" name="phone3" placeholder="5678"
		               pattern="\d{4}" minlength="4" maxlength="4"
		               class="form-control" style="flex: 1;" required />
		    </div>
		</div>

        <div class="form-group">
            <label for="addr">주소</label>
            <div class="row-flex">
                <form:input path="addr" id="addr" readonly="true" placeholder="주소 검색" style="flex:1"/>
                <button type="button" class="btn-purple" onclick="execDaumPostcode()" style="flex: 0.6">주소 검색</button>
            </div>
        </div>

        <div class="form-group">
            <label>이메일</label>
            <div class="row-flex">
                <form:input path="emailId" placeholder="아이디 입력" style="flex: 1"/>
                <span>@</span>
                <form:select path="emailDomain" style="flex: 1">
                    <form:option value="">선택</form:option>
                    <form:option value="naver.com">naver.com</form:option>
                    <form:option value="gmail.com">gmail.com</form:option>
                    <form:option value="daum.net">daum.net</form:option>
                    <form:option value="hanmail.net">hanmail.net</form:option>
                    <form:option value="nate.com">nate.com</form:option>
                </form:select>
            </div>
        </div>

        <button type="submit" class="btn-purple">저장하기</button>
    </form:form>
</div>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                document.getElementById("addr").value = data.address;
            }
        }).open();
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
<script>
    flatpickr("#birthDate", {
        locale: "ko",
        dateFormat: "Y-m-d",
        maxDate: "today",
        altInput: true,
        altFormat: "Y년 m월 d일"
    });
</script>

</body>
</html>
