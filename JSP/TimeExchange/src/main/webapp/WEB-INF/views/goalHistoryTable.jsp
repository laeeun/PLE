<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>
    <c:choose>
        <c:when test="${type == 'MONTHLY'}">
            📅 ${start} ~ ${end} 의 월간 목표
        </c:when>
        <c:otherwise>
            📆 ${start} ~ ${end} 의 주간 목표
        </c:otherwise>
    </c:choose>
</h4>

<c:choose>
    <c:when test="${not empty historyGoals}">
        <table border="1" width="100%" style="margin-top: 10px;">
            <tr>
                <th>제목</th>
                <th>설명</th>
                <th>기간</th>
                <th>상태</th>
            </tr>
            <c:forEach var="goal" items="${historyGoals}">
                <tr>
                    <td>${fn:escapeXml(goal.title)}</td>
                    <td>${fn:escapeXml(goal.description)}</td>
                    <td>${goal.startDate} ~ ${goal.endDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${goal.completed}">
                                <span style="color: green;">✔ 완료됨</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: gray;">미완료</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p style="color: gray;">해당 기간에 목표가 없습니다.</p>
    </c:otherwise>
</c:choose>
</body>
</html>