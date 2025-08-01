<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고 상세보기</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="container mt-5">
    <h2>🔍 신고 상세</h2>

    <table class="table mt-4">
        <tr><th>신고 ID</th><td>${report.report_id}</td></tr>
        <tr><th>신고자</th><td>${report.reporter_id}</td></tr>
        <tr><th>대상자</th><td>${report.target_id}</td></tr>
        <tr><th>대상 타입</th><td>${report.target_type}</td></tr>
        <tr><th>대상 ID</th><td>${report.target_ref_id}</td></tr>
        <tr><th>신고 사유</th><td>${report.reason}</td></tr>
        <tr><th>신고일</th><td>${report.getFormattedCreatedAt()}</td></tr>
        <tr><th>처리 상태</th><td id="reportStatus">${report.status}</td></tr>
        <tr><th>관리자 메모</th><td id="adminNote">${report.admin_note}</td></tr>
    </table>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
