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
		//데이터 베이스 접속
		Connection conn = null;
		//데이터 베이스 위치
		String url = "jdbc:mysql://localhost:3306/BookmarketDB";
		//접속 id
		String user = "root";
		//접속 pw
		String pw = "1234";
		
		//데이터 베이스 연결(2줄)
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, pw);
		
		if (conn == null) {
			System.out.println("데이터베이스가 연결되지 않았습니다.");
		} else {
			System.out.println("데이터베이스가 연결되었습니다.");
		}
	%>
	<%	
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		
		System.out.println(id);
		System.out.println(passwd);
		System.out.println(name);
		
		Statement stmt = null;
		//데이터베이스에 전달할 명령어
		//항상 sql을 완성시키고 워크벤치에서 테스트한 후 변수처리 한다.
		String sql = "insert into member(id, passwd, name) values('" + id + "', '" + passwd + "', '" + name + "')";
		
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	%>
</body>
</html>
