<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>TimeFair - 신고 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEDE7;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Pretendard', sans-serif;
            background-color: #ffffff;
            color: var(--text-main);
            padding: 100px 20px 60px;
            min-height: 100vh;
        }
        h2 {
            font-size: 32px;
            font-weight: 900;
            margin-bottom: 40px;
            color: var(--primary);
            font-family: 'Montserrat', sans-serif;
            text-align: center;
        }
        .report-wrapper {
            max-width: 880px;
            margin: 0 auto;
            padding: 60px;
            background: #ffffff;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
        }
        table.report-table {
            width: 100%;
            border-collapse: collapse;
        }
        table.report-table th {
            background-color: var(--accent-100);
            font-weight: 600;
            width: 30%;
            padding: 20px 22px;
            color: var(--text-main);
            vertical-align: middle;
            text-align: left;
        }
        table.report-table td {
            background-color: #ffffff;
            color: var(--text-main);
            padding: 20px 22px;
        }
    </style>
</head>
<body>

<h2>🔍 신고 상세</h2>

<div class="report-wrapper">
    <table class="report-table">
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
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>
</html>
