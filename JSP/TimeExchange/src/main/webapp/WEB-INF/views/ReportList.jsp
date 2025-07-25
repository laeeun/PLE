<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고 목록</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body class="container mt-5">
    <h2>📄 신고 목록</h2>

    <table class="table table-bordered mt-4">
        <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>신고자</th>
                <th>대상자</th>
                <th>대상 타입</th>
                <th>대상 ID</th>
                <th>상태</th>
                <th>신고일</th>
                <th>자세히</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="r" items="${reportlist}">
                <tr>
                    <td>${r.report_id}</td>
                    <td>${r.reporter_id}</td>
                    <td>${r.target_id}</td>
                    <td>${r.target_type}</td>
                    <td>${r.target_ref_id}</td>
                    <td>${r.status}</td>
                    <td>${r.created_at}</td>
                    <td>
                        <a class="btn btn-sm btn-outline-primary"
                           href="<c:url value='/report/view?id=${r.report_id}' />">보기</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 -->
    <div class="mt-4">
        <nav>
            <ul class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link"
                           href="<c:url value='/report?page=${i}&size=6&target_type=${targetType}&status=${status}' />">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</body>
</html>
