<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p><jsp:useBean id="now" class="java.util.Date" /></p>
	<p>한국 :<fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="full" /></p>
	<p><fmt:timeZone value="America/New_York">
	<p>뉴욕 :<fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="full" /></p>
	</fmt:timeZone>
	<p><fmt:timeZone value="Europe/London">
	<p>런던 :<fmt:formatDate value="${now}" type="both" dateStyle="full" timeStyle="full" /></p>
	</fmt:timeZone>
</body>
</html>