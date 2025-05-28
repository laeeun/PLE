<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="loginForm" action="#" method="post" id="login">
		<p>아이디 : <input type="text" name="id" id="id"></p>
		<p>비밀번호 : <input type="password" name="passwd" id="pw"></p>
		<p><input type="button" value="전송" id="btn"></p>
	</form>

	<script type="text/javascript">
		let btn = document.querySelector("#btn")
		btn.addEventListener("click", checkForm)
		function checkForm(){
			let id = document.querySelector("#id").value;
			let pw = document.querySelector("#pw").value;
			alert("아이디 :" + id +"\n"+"비밀번호 :"+ pw);
		}
	</script>
</body>
</html>