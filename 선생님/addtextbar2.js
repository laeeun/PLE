//Step 1 : 요소를 Select 한다.
let addBtn = document.querySelector('#add');
let itemBox = document.querySelector("#itemList");
console.log(addBtn); //선택되었는지 확인

let ulNode = document.createElement("ul");
itemBox.appendChild(ulNode);
// itemBox.innerHTML += "<ul></ul>"; 와 같은 결과

//Step 2 : 이벤트 핸들러 등록하기
addBtn.addEventListener("click", addList); //버튼을 클릭하면 addList 함수로 이동

//Step 3 : 함수 선언하기
function addList() {
  /*
  Step4. 함수 구현하기
  인풋박스의 value값을 가져와서 준비할 목록 div 안에 li태그요소로 추가한다.
  1. 인풋박스의 value값을 가져오기
  2. 가져온 value값 변수에 담기
  3. 준비할 목록 div 선택하기
  4. div 안에 li태그 요소 + 인풋박스의 value값을 추가하기
  */
  // 요소 추가 방식 > 1.innerHTML 2.createElement, .textContent. appendChild();
  
  let item = document.querySelector("#item").value.trim();    
  // 비어있는 값에 대한 처리 추가 필요
  
  /*
  방법1. 보안 취약, 잦은 렌더링 변화
  itemBox.children[1].innerHTML += `<li>${item}<span class="close">X</span></li>`
  */

  /*
  방법2. 필요한 부분만 렌더링
  createElement(), .textContent, appendChild()
  */
  let liNode = document.createElement("li");
  liNode.textContent = item;
  itemBox.children[1].appendChild(liNode);

  // 인풋상자의 값 초기화
  document.querySelector("#item").value = "";
}