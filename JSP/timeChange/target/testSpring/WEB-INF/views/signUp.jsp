<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    
    <script>
        // 에러 메시지가 있으면 alert 띄우기
        window.onload = function() {
            var errorMsg = '${error}';
            if (errorMsg) {
                alert(errorMsg);
            }
        };
    </script>

    <!-- ✅ 감성 폰트 Pretendard -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">

    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            animation: fadeIn 2s ease-in;
        }

        .signup-container {
            background: white;
            padding: 40px 50px;
            border-radius: 25px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            width: 450px;
            animation: popUp 1s ease;
        }

        .signup-container h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #ff69b4;
        }

        p {
            margin-bottom: 15px;
            font-size: 15px;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 10px;
            border: 1px solid #ccc;
            font-size: 14px;
            box-sizing: border-box;
        }

        select {
            width: auto;
        }

        .phone-inputs {
            display: flex;
            gap: 8px;
        }

        .submit-btn {
            width: 100%;
            background-color: #ff99cc;
            border: none;
            padding: 12px;
            color: white;
            font-size: 16px;
            border-radius: 10px;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #ff66b2;
            transform: scale(1.05);
        }

        /* ✅ 체크박스 정렬 보정 */
        .checkbox-wrapper {
            display: flex;
            align-items: center;
            gap: 8px;
            margin: 15px 0 25px 0;
            font-size: 14px;
        }

        .checkbox-input {
            width: 16px;
            height: 16px;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes popUp {
            0% { transform: scale(0.9); opacity: 0; }
            100% { transform: scale(1); opacity: 1; }
        }
    </style>
</head>
<body>

<%
    System.out.println("회원가입 page 도착!!");
%>

<div class="signup-container">
    <h1>회원가입</h1>

    <form:form modelAttribute="signUp" action="${pageContext.request.contextPath}/signUp" method="post">

        <p>ID  
            <form:input path="member_id" placeholder="아이디를 입력하세요." required="true" />
        </p>

        <p>PW  
            <form:password path="pw" placeholder="비밀번호를 입력하세요." required="true" />
        </p>

        <p>PW 확인  
            <form:password path="" name="pwConfirm" placeholder="비밀번호 확인" required="true" />
        </p>

        <p>닉네임  
            <form:input path="userName" placeholder="유저명을 입력하세요." required="true" />
        </p>

        <p>이름  
            <form:input path="name" placeholder="이름을 입력하세요." required="true" />
        </p>

        <p>이메일  
            <form:input path="email" type="email" placeholder="이메일을 입력하세요." required="true" />
        </p>

        <p>전화번호</p>
        <div class="phone-inputs">
            <select name="phone1" required>
                <option value="">선택</option>
                <option value="010">010</option>
                <option value="011">011</option>
                <option value="016">016</option>
                <option value="017">017</option>
                <option value="018">018</option>
                <option value="019">019</option>
            </select>
            <input type="text" name="phone2" pattern="\d{3,4}" maxlength="4" placeholder="1234" required />
            <input type="text" name="phone3" pattern="\d{4}" maxlength="4" placeholder="5678" required />
        </div><br/>

        <p>주소  
            <form:input path="addr" placeholder="주소를 입력하세요." />
        </p>

		 <div class="checkbox-wrapper">
		     <form:checkbox path="expert" cssClass="checkbox-input" />
		     <label>전문가입니까?</label>
		 </div>
		 <button type="submit">회원가입</button>


        <%
            System.out.println("회원가입 전송완료");
        %>
    </form:form>
</div>

</body>
</html>
