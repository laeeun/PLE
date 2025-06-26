<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<title>ajax test</title>
</head>
<body>
	<h1>홈이다 !!!!</h1>
	
	<!-- case 1 html -->
	<h3>간단한 ajax 테스트 case 1</h3>
	<button id="b1">test case1</button>
	<div id="result1"></div>
	
	
	
	<!-- case javaScript -->
	<script>
		var btn = document.querySelector("#b1");
		console.log(btn);
		
		btn.addEventListener("click", case1);
		
		var obj = {"name":"kim", "age":"30"};
		function case1(){
			console.log("case1 실행");
			$.ajax({
				url : "case1",
				type : "post",
				data : JSON.stringify(obj),
				contentType : "application/json",
				success : function(data){
					alert("success");
					$("#result1").text("응답 :" + data);
				},
				error : function(errorThrown){
					alert("fail");
				}
			});
		}
		
	</script>
	<!-- case 2 html -->
	<h3>간단한 ajax test case2</h3>
	ID : <input type="text" id="text2">
	<button id="b2">test case2</button>
	<div id="result2"></div>
	
	
	<script>
		<!-- case2 javaScript -->
		var btn2 = document.querySelector("#b2");
		console.log(btn);
		btn2.addEventListener("click", case2);
		function case2(){
			var inputdata = document.querySelector("#text2").value;
			$.ajax({
				url : "case2",
				type : "post",
				/*data : {id:inputdata},*/
				data : JSON.stringify({id:inputdata}),
				contentType : "application/json",
				success : function(data){
					alert(JSON.stringify(data));
					$("#result2").text("응답 :" + data.id);
				},
				error : function(errorThrown){
					alert("fail");
				}
			});

		}
		
	</script>
	
	<!-- case 3 html -->
	<h3>간단한 ajax test case3</h3>
	ID : <input type="text" id="text3">
	<button id="b3">test case3</button>
	<div id="result3"></div>
	
	
	<script>
		<!-- case3 javaScript -->
		var btn3 = document.querySelector("#b3");
		console.log(btn);
		btn3.addEventListener("click", case3);
		function case3(){
			console.log("case3 실행")
			var inputdata = document.querySelector("#text3").value;
			$.ajax({
				url : "case3",
				type : "post",
				/*data : {id:inputdata},*/
				data : JSON.stringify({id:inputdata}),
				contentType : "application/json",
				success : function(data){
					alert(JSON.stringify(data));
					$("#result3").empty();
					$("#result3").select();
					$.each(data, function(i,dto){
						$("#result3").append("<li>"+dto.id + "," + dto.name + "</li>");
					});
				},
				error : function(errorThrown){
					alert("fail");
				}
			});

		}
		
	</script>	
</body>
</html>