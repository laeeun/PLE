<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dto.member"%>   
<!DOCTYPE html>
<html>
<head>
 <link href = "./resources/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%@ include file="nav.jsp" %>
    
    <%
       ArrayList<member> allmember = (ArrayList<member>)request.getAttribute("allmember");
    %>
    
    <h1>전체멤버 출력</h1>
    <table>
        <tr><td>아이디</td><td>패스워드</td><td>수정</td><td>삭제</td></tr>
        <% 
           for(int i=0; i<allmember.size();i++){ 
             member mb = allmember.get(i);
        %>
        <tr>
        <td><%=mb.getId() %></td><td><%=mb.getPw() %></td>
        <td><a role="button" href="update?id=<%=mb.getId()%>">수정</a></td><td><a href="delete?id=<%=mb.getId()%>">삭제</a></td>
        </tr>    
        <%} %>
    </table>
</body>
</html>