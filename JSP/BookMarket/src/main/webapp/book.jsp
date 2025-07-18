<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.Book" %>
<%@ page import="dao.BookRepository" %>
<%@ page errorPage="exceptionNoBookId.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>도서 상세 정보</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">도서정보</h1>
        <p class="col-md-8 fs-4">BookInfo</p>
      </div>
    </div>

    <%
      String id = request.getParameter("id");
      BookRepository dao = BookRepository.getInstance();
      Book book = dao.getBookById(id);
    %>

    <div class="row align-items-md-stretch">
      <div class="col-md-5">
        <img src="./resources/images/<%= book.getFileName() %>" style="width: 70%;">
      </div>
      <div class="col-md-6">
        <h3><b><%=book.getName() %></b></h3>
        <p><%=book.getDescription() %></p>
        <p><b>도서코드 :</b> <span class="badge text-bg-danger"><%=book.getBookId() %></span></p>
        <p><b>저자</b> : <%=book.getAuthor() %></p>
        <p><b>출판사</b> : <%=book.getPublisher() %></p>
        <p><b>출판일</b> : <%=book.getReleaseDate() %></p>
        <p><b>분류</b> : <%=book.getCategory()%></p>
        <p><b>재고수</b> : <%=book.getUnitsInStock() %></p>
        <h4><%=book.getUnitPrice() %>원</h4>

        <form name="addForm" action="./addCart.jsp?id=<%=book.getBookId() %>" method="post">
          <a href="#" class="btn btn-info" onclick="addToCart()">도서 주문 &raquo;</a>
          <a href="./cart.jsp" class="btn btn-warning">장바구니 &raquo;</a>
          <a href="./books.jsp" class="btn btn-secondary">도서 목록 &raquo;</a>
        </form>
      </div>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>
  </div>

  <script type="text/javascript">
    function addToCart() {
      if (confirm("도서를 장바구니에 추가하시겠습니까?")) {
        document.addForm.submit();
      } else {
        document.addForm.reset();
      }
    }
  </script>
</body>
</html>
