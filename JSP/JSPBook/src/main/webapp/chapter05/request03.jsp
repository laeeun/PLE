<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>클라이언트 IP : <%=request.getRemoteAddr() %><br>
	<p>요청 정보 길이 : <%=request.getContentLength() %><br>
	<p>요청 정보 인코딩 : <%=request.getCharacterEncoding() %><br>
	<p>요청 정보 콘텐츠 유형 : <%=request.getContentType() %><br>
	<p>요청 정보 프로토콜 : <%=request.getProtocol() %><br>
	<p>요청 정보 전송 방식 : <%=request.getMethod() %><br>
	<p>요청 URI : <%=request.getRequestURI() %><br>
	<p>콘텍스트 경로 : <%=request.getContextPath() %><br>
	<p>서버이름 : <%=request.getServerName() %><br>
	<p>서버포트 : <%=request.getServerPort() %><br>
	<p>쿼리문 : <%=request.getQueryString() %><br>
</body>
</html>