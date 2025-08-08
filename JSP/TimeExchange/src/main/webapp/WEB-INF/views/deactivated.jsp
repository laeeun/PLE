<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
    <title>계정 탈퇴 완료</title>
</head>
<body>
<h1>탈퇴가 처리되었습니다.</h1>
    <p>계정을 다시 사용하려면 아래 버튼을 눌러 탈퇴를 취소하세요.</p>
    <form action="<c:url value='/mypage/deactivated/restore'/>" method="post">
        <button type="submit">탈퇴 취소하기</button>
    </form>
</body>
</html>
