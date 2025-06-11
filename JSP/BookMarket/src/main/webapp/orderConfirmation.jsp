<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="dto.*" %>
<%@ page import="dao.*" %>
<%
    request.setCharacterEncoding("utf-8");

    String cartId = session.getId();
    String Shipping_cartId = "", Shipping_name = "", Shipping_shippingDate = "";
    String Shipping_country = "", Shipping_zipCode = "", Shipping_addressName = "";

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie thisCookie : cookies) {
            String name = thisCookie.getName();
            String value = URLDecoder.decode(thisCookie.getValue(), "utf-8");
            switch (name) {
                case "Shipping_cartId": Shipping_cartId = value; break;
                case "Shipping_name": Shipping_name = value; break;
                case "Shipping_shippingDate": Shipping_shippingDate = value; break;
                case "Shipping_country": Shipping_country = value; break;
                case "Shipping_zipCode": Shipping_zipCode = value; break;
                case "Shipping_addressName": Shipping_addressName = value; break;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 정보</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
        <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold">주문 정보</h1>
            <p class="col-md-8 fs-4">Order Info</p>
        </div>
    </div>

    <div class="row align-items-md-stretch alert alert-info">
        <div class="text-center mb-4">
            <h1>영수증</h1>
        </div>

        <div class="row justify-content-between">
            <div class="col-4 text-start">
                <strong>배송주소</strong><br>
                성명 : <%= Shipping_name %><br>
                우편번호 : <%= Shipping_zipCode %><br>
                주소 : <%= Shipping_addressName %> (<%= Shipping_country %>)<br>
            </div>

            <div class="col-4 text-end">
                <p><em>배송일 : <%= Shipping_shippingDate %></em></p>
            </div>
        </div>

        <div class="py-5">
            <table class="table table-hover text-center">
                <thead>
                    <tr>
                        <th>도서</th>
                        <th>#</th>
                        <th>가격</th>
                        <th>소계</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int sum = 0;
                        ArrayList<Book> cartList = (ArrayList<Book>) session.getAttribute("cartList");
                        if (cartList == null) cartList = new ArrayList<Book>();

                        for (Book book : cartList) {
                            int total = book.getUnitPrice() * book.getQuantity();
                            sum += total;
                    %>
                    <tr>
                        <td><em><%= book.getName() %></em></td>
                        <td><%= book.getQuantity() %></td>
                        <td><%= book.getUnitPrice() %>원</td>
                        <td><%= total %>원</td>
                    </tr>
                    <%
                        }
                    %>
                    <tr>
                        <td colspan="2"></td>
                        <td class="text-end"><strong>총액 :</strong></td>
                        <td class="text-danger"><strong><%= sum %></strong></td>
                    </tr>
                </tbody>
            </table>

            <div class="d-flex justify-content-between">
                <a href="./shippingInfo.jsp?cartId=<%= Shipping_cartId %>" class="btn btn-secondary">이전</a>
                <div>
                    <a href="./thankCustomer.jsp" class="btn btn-success">주문완료</a>
                    <a href="./checkOutCancelled.jsp" class="btn btn-danger">취소</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
