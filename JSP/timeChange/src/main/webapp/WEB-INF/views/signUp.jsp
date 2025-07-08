<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <style>
        input, select {
            padding: 6px;
            margin-top: 4px;
            width: 250px;
        }
        p {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h1>회원가입</h1><br>

<form:form modelAttribute="signUp" action="${pageContext.request.contextPath}/signUp" method="post">
    
    <p>ID: <form:input path="userId" placeholder="아이디를 입력하세요." required="true"/></p><br/>

    <p>PW: <form:password path="pw" placeholder="비밀번호를 입력하세요." required="true"/></p><br/>

    <p>Name: <form:input path="name" placeholder="이름을 입력하세요." required="true"/></p><br/>

    <p>Email: <form:input path="email" type="email" placeholder="이메일을 입력하세요." required="true"/></p><br/>

    <p>Phone: 
        <select name="phone1" required>
            <option value="">선택</option>
            <option value="010">010</option>
            <option value="011">011</option>
            <option value="016">016</option>
            <option value="017">017</option>
            <option value="018">018</option>
            <option value="019">019</option>
        </select>
        -
        <input type="text" name="phone2" pattern="\d{3,4}" maxlength="4" placeholder="1234" required />
        -
        <input type="text" name="phone3" pattern="\d{4}" maxlength="4" placeholder="5678" required />
    </p><br/>

    <p>Address: <form:input path="addr" placeholder="주소를 입력하세요."/></p><br/>

    <p>전문가입니까?
        <form:checkbox path="isExpert" />
    </p><br/>

    <p><input type="submit" value="회원가입" /></p>

</form:form>

</body>
</html>
	
</body>
</html>