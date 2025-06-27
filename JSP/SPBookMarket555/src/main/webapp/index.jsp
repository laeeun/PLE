<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% System.out.println("👉 hello world 입짱~"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World</title>
    <link href="./resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/main.css'/>" rel="stylesheet">
   
</head>
<body>
    <div class="hero">
        <h1>🎈Hello World🎈</h1>
        <a class="btn btn-pink" href="<c:url value='/home'/>">시작하기 →</a>
    </div>
</body>
</html>
