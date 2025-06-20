<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
    <h2>회원 등록</h2>
    <form action="create" method="post">
        아이디: <input type="text" name="id"><br>
        비밀번호: <input type="text" name="pw"><br>
        <input type="submit" value="등록">
    </form>
</body>
</html>


<!-- 

	createForm.jsp → create_controller → member_repository.create()
                                             ↓
                                         DB에 INSERT

	readAll_controller → member_repository.readall()
                                ↓
                          allview.jsp에서 출력
	
	
 -->