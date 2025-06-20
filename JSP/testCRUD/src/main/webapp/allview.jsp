<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, dto.member" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
</head>
<body>
    <h2>전체 회원 목록</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>PW</th>
        </tr>
        <%
            ArrayList<member> allmember = (ArrayList<member>) request.getAttribute("allmember");
            for (member mb : allmember) {
        %>
        <tr>
            <td><%= mb.getId() %></td>
            <td><%= mb.getPw() %></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
