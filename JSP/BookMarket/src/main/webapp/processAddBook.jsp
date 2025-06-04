<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.sql.*" %>
<%@ include file="dbconn.jsp" %>
<% System.out.println("Step 1.processAddBook.jsp 입장");%>

<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>도서 등록</title>
</head>
<body>
	<%
		System.out.println();	
//입장
//전처리 : 전달된데이터 받음 , 유효성 검사,묶음
		request.setCharacterEncoding("UTF-8");

		String filename="";
		String realFolder= request.getServletContext().getRealPath("resources/images");
		int maxSize=5 * 1024 * 1024;
		String enctype="utf-8";
		
		MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, enctype, new DefaultFileRenamePolicy());
		
		
		String bookId=multi.getParameter("bookId");
		String name=multi.getParameter("name");
		String unitPrice=multi.getParameter("unitPrice");
		String author=multi.getParameter("author");
		String publisher=multi.getParameter("publisher");
		String releaseDate=multi.getParameter("releaseDate");
		String description=multi.getParameter("description");
		String category=multi.getParameter("category");
		String unitsInStock=multi.getParameter("unitsInStock");
		String condition=multi.getParameter("condition");
		
		System.out.println(bookId);
		System.out.println(name);
		System.out.println(unitPrice);
		System.out.println(publisher);
		System.out.println(releaseDate);
		System.out.println(description);
		System.out.println(category);
		System.out.println(unitsInStock);
		System.out.println(condition);

		String fileName = multi.getFilesystemName("BookImage");
		
		
		int price;
		
		if (unitPrice.isEmpty()) {
			price = 0;
		}
		else{
			price = Integer.valueOf(unitPrice);
		}
		
		long stock;

		if (unitsInStock.isEmpty()){
			stock = 0;
		}
		else{
			stock = Long.valueOf(unitsInStock);
		}
		
			System.out.println("price"+price);
			System.out.println("stock"+stock);

		PreparedStatement pstmt=null;
		
		String sql="insert into book values(?,?,?,?,?,?,?,?,?,?,?)";
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, bookId);
		pstmt.setString(2, name);
		pstmt.setInt(3, price);
		pstmt.setString(4, author);
		pstmt.setString(5, description);
		pstmt.setString(6, publisher);
		pstmt.setString(7, category);
		pstmt.setLong(8, stock);
		pstmt.setString(9, releaseDate);
		pstmt.setString(10, condition);
		pstmt.setString(11, fileName);
		pstmt.executeUpdate();
		
		if(pstmt != null){
			pstmt.close();
		}
		if(conn != null){
			conn.close();
		}
		
		response.sendRedirect("books.jsp");
		
	%>
</body>
</html>