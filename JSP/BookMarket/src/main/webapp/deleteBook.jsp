<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ include file="dbconn.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서 삭제 처리</title>
</head>
<body>
	<%
		String bookId = request.getParameter("id");
			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
			
		String sql ="select * from book";
		pstmt=conn.prepareStatement(sql);
		rs=pstmt.executeQuery();
		
		if(rs.next()){
			sql="delete from book where b_id=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, bookId);
			pstmt.executeUpdate();
		}else{
			out.println("일치하는 도서가 없습니다.");
		}
		
		if(rs != null){
			rs.close();
		}
		
		if(pstmt != null){
			pstmt.close();
		}
			
		if(conn != null){
			conn.close();
		}
			
		
		response.sendRedirect("editBook.jsp?edit=delete");
	%>
</body>
</html>