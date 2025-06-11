<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<%@ page import="java.io.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>도서 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
        <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold">도서 등록</h1>
            <p class="col-md-8 fs-4">Add Book</p>
        </div>
    </div>

    <!-- enctype 속성 필수!! -->
    <form action="processAddBook.jsp" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="bookId" class="form-label">도서 ID</label>
            <input type="text" class="form-control" id="bookId" name="bookId" required>
        </div>
        <div class="mb-3">
            <label for="name" class="form-label">도서명</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="mb-3">
            <label for="unitPrice" class="form-label">가격</label>
            <input type="number" class="form-control" id="unitPrice" name="unitPrice" required>
        </div>
        <div class="mb-3">
            <label for="author" class="form-label">저자</label>
            <input type="text" class="form-control" id="author" name="author">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">설명</label>
            <textarea class="form-control" id="description" name="description"></textarea>
        </div>
        <div class="mb-3">
            <label for="publisher" class="form-label">출판사</label>
            <input type="text" class="form-control" id="publisher" name="publisher">
        </div>
        <div class="mb-3">
            <label for="category" class="form-label">분류</label>
            <input type="text" class="form-control" id="category" name="category">
        </div>
        <div class="mb-3">
            <label for="unitsInStock" class="form-label">재고 수</label>
            <input type="number" class="form-control" id="unitsInStock" name="unitsInStock">
        </div>
        <div class="mb-3">
            <label for="releaseDate" class="form-label">출판일</label>
            <input type="text" class="form-control" id="releaseDate" name="releaseDate">
        </div>
        <div class="mb-3">
            <label for="condition" class="form-label">상태</label>
            <input type="text" class="form-control" id="condition" name="condition">
        </div>
        <div class="mb-3">
            <label for="BookImage" class="form-label">도서 이미지</label>
            <input type="file" class="form-control" id="BookImage" name="BookImage" required>
        </div>
        <button type="submit" class="btn btn-primary">도서 등록</button>
    </form>

    <%@ include file="footer.jsp" %>
</div>
</body>
</html>
