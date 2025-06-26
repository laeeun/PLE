function addToCart(action){
	console.log("들어옴");
	document.addForm.action = action;
	document.addForm.submit();
	alert("도서가 장바구니에 추가되었습니다!!");
}

function removeFromCart(action){
	console.log("delete들어옴");
	document.removeForm.action = action;
	document.removeForm.submit();
	console.log("remove submit함");
	//요청하고 함수실행하고 리로드해야하는데 그냥 리로드해버려서 setTimeOut을 꼭해야함
	setTimeout(() =>
	   {
	      console.log("1초 후 reload()");
	      window.location.reload();
	   }, 100);
}



function clearCart(){
	console.log("clear들어옴");
	document.clearForm.submit();
	setTimeout(() =>
		   {
		      console.log("1초 후 reload()");
		      window.location.reload();
		   }, 100);
}