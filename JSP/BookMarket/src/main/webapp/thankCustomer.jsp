<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String Shipping_cartId="";
		String Shipping_name="";
		String Shipping_ShippingDate="";
		String Shipping_country="";
		String Shipping_zipCode="";
		String Shipping_addressName="";
		
		Cookie[] cookies=request.getCookies();
		
		if(cookies != null){
			for(int i =0; i < cookies.length; i++){
				Cookie thisCookie=cookies[i];
				String n =thisCookie.getName();
				if(n.equals("Shipping_cartId")){
					Shipping_cartId=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_ShippingDate")){
					Shipping_ShippingDate=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
			}
		}
	%>
	
	<div class="container py-4">
		<%@ include file="menu.jsp" %>
		
		<div class="p-5 mb-4 bg-body-tertary eounded-3">
			<div class="container-fluid py-5">
				<h1 class="display-5 fw-bold">주문완료</h1>
				<p class="col-md-8 fs-4">Order Completed</p>
			</div>
		</div>
		
		<div class="row align-items-md-stretch">
			<h2 class="arert alert-danger">주문해주셔서 감사합니다</h2>
			<p>주문은<%out.println(Shipping_ShippingDate); %>에 배송될 예정입니다!</p>
			<p>주문번호 :<% out.println(Shipping_cartId); %></p>
		</div>
		
		<div class="container">
			<p><a href="./books.jsp" class="btn btn-secondary"> &raquo; 도서 목록</a>
		</div>
		<%@ include file="footer.jsp" %>
	</div>
</body>
</html>

<%
	session.invalidate();
	
	for(int i=0; i < cookies.length; i++){
		Cookie thisCookie=cookies[i];
		String n =thisCookie.getName();
		if(n.equals("Shipping_cartId")){
			thisCookie.setMaxAge(0);
		}
		if(n.equals("Shipping_name")){
			thisCookie.setMaxAge(0);
		}
		if(n.equals("Shipping_ShippingDate")){
			thisCookie.setMaxAge(0);
		}
		if(n.equals("Shipping_country")){
			thisCookie.setMaxAge(0);
		}
		if(n.equals("Shipping_zipCode")){
			thisCookie.setMaxAge(0);
		}
		if(n.equals("Shipping_addressName")){
			thisCookie.setMaxAge(0);
		}
		
		response.addCookie(thisCookie);
	}
%>