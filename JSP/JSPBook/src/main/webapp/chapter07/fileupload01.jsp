<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload</title>
</head>
<body>
	<form name="fileForm" method="post" enctype="multipart/form-data" action="exam7_1">
		<p>이 름 : <input type="text" name="name1"></p>
		<p>제 목 : <input type="text" name="subject1"></p>
		<p>파 일 : <input type="file" name="filename1_2"></p>
		<p>파 일 : <input type="file" name="filename2_2"></p>
		<p>파 일 : <input type="file" name="filename3_2"></p>
		<p>파 일 : <input type="file" name="filename4_2"></p>
		<p><input type="submit" value="파일올리기"></p>
	</form>
	

</body>
</html>