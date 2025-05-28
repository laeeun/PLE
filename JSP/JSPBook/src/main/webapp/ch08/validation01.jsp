<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
	<h3>회원 가입</h3>
	<form action="validation05" name="Member" method="post">
		<p>아이디 : <input type="text" name="id">
		<p>비밀번호 : <input type="password" name="passwd">
		<p><input type="button" value="가입하기" id="btn">
	</form>
	
	<script type="text/javascript">
		let btn = document.querySelector("#btn");
		
		btn.addEventListener("click", function() {
			let form = document.forms["Member"];
			let id = form.id.value;
			let pw = form.passwd.value;
			alert("아이디: " + id + "\n비밀번호: " + pw);
		});
	</script>
</body>
</html>
