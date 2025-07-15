<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>내 구매 요청 목록</title></head>
<body>
    <h2>내가 구매 요청한 재능 목록</h2>
    <c:choose>
        <c:when test="${not empty sentRequests}">
            <table border="1">
                <tr>
                    <th>재능 ID</th>
                    <th>판매자</th>
                    <th>요청 상태</th>
                    <th>요청 시간</th>
                </tr>
                <c:forEach var="req" items="${sentRequests}">
                    <tr>
                        <td>${req.talent_id}</td>
                        <td>${req.seller_id}</td>
                        <td>${req.status}</td>
                        <td>${req.requested_at}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <p>요청한 재능이 없습니다.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
