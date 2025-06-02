<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="dto.*" %>
<%@ page import="dao.*" %>
<%
		request.setCharacterEncoding("utf-8");
	
		String cartId=session.getId();
		
		String Shipping_cartId="";
		String Shipping_name="";
		String Shipping_shippingDate="";
		String Shipping_country="";
		String Shipping_zipCode="";
		String Shipping_addressName="";
		
		Cookie[] cookies=request.getCookies();
		
		if(cookies!=null){
			for(int i=0; i <cookies.length; i++){
				Cookie thisCookie=cookies[i];
				String n=thisCookie.getName();
				if(n.equals("Shipping_cartId")){
					Shipping_cartId=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_name")){
					Shipping_name=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_shippingDate")){
					Shipping_shippingDate=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_country")){
					Shipping_country=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_zipCode")){
					Shipping_zipCode=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
				if(n.equals("Shipping_addressName")){
					Shipping_addressName=URLDecoder.decode((thisCookie.getValue()), "utf-8");
				}
			}
		}
		
	%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
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
			<div class="text-center">
				<h1>영수증</h1>
			</div>
			
			<div class="row justify-content-between">
				<div class="col-4" align="left">
					<strong>배송주소</strong><br>
					성명 : <% out.println(Shipping_name); %><br>
					우편번호 : <% out.println(Shipping_zipCode); %><br>
					주소 : <%out.println(Shipping_addressName);%>
					(<%out.println(Shipping_country); %>)<br>
				</div>
				
				<div class="col-4" align="right">
					<p><em>배송일 :<% out.println(Shipping_shippingDate); %></em></p>
				</div>
			</div>
			
			<div class="py-5">
				<table class="table table-hover">
					<tr>
						<th class="text-center">도서</th>
						<th class="text-center">#</th>
						<th class="text-center">가격</th>
						<th class="text-center">소계</th>
					</tr>
					<%
						int sum=0;
						ArrayList<Book> cartList=(ArrayList<Book>) session.getAttribute("cartList");
						if(cartList==null)
							cartList=new ArrayList<Book>();
							for(int i=0; i < cartList.size(); i++){ //상품리스트 하나씩 출력하기
								Book book=cartList.get(i);
								int total=book.getUnitPrice() * book.getQuantity();
								sum=sum + total;
							
					%>
					<tr>
						<td class="text-center"><em><%=book.getName() %></em></td>
						<td class="text-center"><em><%=book.getQuantity() %></em></td>
						<td class="text-center"><%=book.getUnitPrice() %>원</td>
						<td class="text-center"><%=total %>원</td>
					</tr>
					<%
							}
					%>
					<tr>
						<td></td>
						<td></td>
						<td class="text-right"><strong>총액 :</strong></td>
						<td class="text-center text-danger"><strong><%=sum %></strong></td>
					</tr>
				</table>
				<a href="./shippingInfo.jsp?cartId=<%=Shipping_cartId %>" class="btn btn-secondary" role="button">이전</a>
				<a href="./thankCustomer.jsp" class="btn btn-success" role="button">주문완료</a>
				<a href="./checkOutCancelled.jsp" class="btn btn-secondary" role="button">취소</a>
			</div>
		</div>
	</div>
</body>
</html>