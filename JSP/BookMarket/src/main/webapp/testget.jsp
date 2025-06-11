<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--  데이터베이스 연결 -->
	<%  
		// 데이터 베이스 접속
		Connection conn = null;
		// 데이터 베이스 위치
		String url = "jdbc:mysql://localhost:3306/BookmarketDB";
		// 접속 id
		String user = "root";
		// 접속 pw
		String pw = "1234";
		
		// 데이터 베이스 연결(2줄)
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, pw);
		
		if (conn == null) {
			System.out.println("데이터베이스가 연결되지 않았습니다.");
		} else {
			System.out.println("데이터베이스가 연결되었습니다.");
		}
	%>
	
	<%
		PreparedStatement pstmt = null;
		String sql = "select * from member";
		pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
	%>
	
	<%
		while (rs.next()) {
			String id = rs.getString("id");
			String passwd = rs.getString("passwd");
			String name = rs.getString("name");
	%>
	<%= id %>, <%= passwd %>, <%= name %><br>
	<% 
		} 
	%>
</body>
</html>
