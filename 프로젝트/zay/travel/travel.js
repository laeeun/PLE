




//이벤트 발생할 요소

let btn = document.querySelector(".addBtn"); //버튼
let toDoList = document.querySelector("#itemList"); //할일 리스트창

let ulNode = document.createElement("ul");
toDoList.appendChild(ulNode);

/**
 * 
 * 최종 목적
 * 
 * input안에 텍스트 내용을 입력한다.
 * 
 * 버튼을 클릭하면 li를 생성
 * input에 입력한 텍스트 내용이 
 * itemList 안에  삽입 될 수 있도록 한다.
 * 
 * itemList안에 있는 list들에 mouseover시 background-color 변경
 * itemList안에 있는 list들 옆에 X표시 클릭 하면 list삭제 되게끔하기
 * 
 */





btn.addEventListener("click", (event) => {
  event.preventDefault();
  let item = document.querySelector("#item").value.trim();   //할일 입력창

  let newLi = document.createElement('li'); //li 생성
  toDoList.children[1].innerHTML += `<li>${item}<span class="close">X</span></li>`
  ulNode.appendChild(newLi);
  //ulNode.removeChild(ulNode.childeren[0]);

  

  


  document.querySelector("#item").value = "";
});




