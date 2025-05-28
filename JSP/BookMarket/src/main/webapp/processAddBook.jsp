<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<% System.out.println("Step 1.processAddBook.jsp 입장");%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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

		
		BookRepository dao=BookRepository.getInstance();
		
		Book newBook= new Book();
		newBook.setBookId(bookId);
		newBook.setName(name);
		newBook.setUnitPrice(price);
		newBook.setAuthor(author);
		newBook.setPublisher(publisher);
		newBook.setReleaseDate(releaseDate);
		newBook.setDescription(description);
		newBook.setCategory(category);
		newBook.setUnitsInStok(stock);
		newBook.setCondition(condition);
		newBook.setFilename(filename);
		
		System.out.println(newBook);
		
		dao.addBook(newBook);
		System.out.println("저장 완료");
		
		response.sendRedirect("books.jsp");
		
	%>
</body>
</html>