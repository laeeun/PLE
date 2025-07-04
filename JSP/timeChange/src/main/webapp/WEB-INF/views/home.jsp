<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>홈입니다.
	
	
	<h1>회원가입</h1><br>
	<form:form modelAttribute="/signUp" id="signUp" vlaue="회원가입">
		<p> ID : <input type="text" name="id" id="userId" placeholder="아이디를 입력하세요." required/></p><br/>
		<p> PW : <input type="password" name="pw" id="pw" placeholder="비밀번호를 입력하세요." required/></p><br/>
		<p> Name : <input type="text" name="name" id="name" placeholder="이름를 입력하세요." required/></p><br/>
		<p> Email : <input type="email" name="email" id="email" placeholder="이메일을 입력하세요." required/></p><br/>
		<p> phone : <select name="phone1" id="phone1" required>
						  <option value="">선택</option>
						  <option value="010">010</option>
						  <option value="011">011</option>
						  <option value="016">016</option>
						  <option value="017">017</option>
						  <option value="018">018</option>
						  <option value="019">019</option>
					</select>
					 -
					<input type="text" name="phone2" id="phone2"
					       minlength="3" maxlength="4"
					       pattern="\d{3,4}" placeholder="1234" required />
					 -
					<input type="text" name="phone3" id="phone3"
					       minlength="4" maxlength="4"
					       pattern="\d{4}" placeholder="5678" required /></p><br/>
		<p> address: <input type="text" id="address" name="addr" /></p><br/>
		<p> 전문가이면 Check하세요 <input type="checkbox" id="isExpert" name="isExpert" value="false" /></p><br/>
		<p>	<input type="submit" value="전송">
	</form:form>
</body>
</html>