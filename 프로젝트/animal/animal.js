
// header
//dropdown

let pages = document.querySelector(".pages");
let subMenu = document.querySelector("#sub_menu");

pages.addEventListener("click", showSubMenu);


function showSubMenu() {
  subMenu.classList.toggle("active");
}


//main_banner
//slide


//요소 선택 
let btns = document.querySelectorAll(".slidebox .btns button");
console.log(btns);
let slides = document.querySelectorAll(".slide");

// 이벤트 리스너 등록
// btns.forEach(function(btn){
//   btn.addEventListener("click", sliding);  //버튼 클릭시 슬라이더 이동
//   btn.addEventListener("click", changeColor); //버튼 클릭시 버튼 색상변경
// });

for(let i = 0; i < btns.length; i++){
  btns[i].addEventListener("click", sliding);  //버튼 클릭시 슬라이더 이동
  btns[i].addEventListener("click", changeColor); //버튼 클릭시 버튼 색상변경
}


//슬라이더 이동 함수
let index = 0;

function sliding(){
  index = parseInt(this.id.charAt(this.id.length-1));  // 버튼 id옆 숫자를 인덱스로 사용
  // index = this.id.charAt(1);
  

  slides.forEach(function(slide){
    slide.style.transform = `translateX(${index * (-100)}%)`; //가로 슬라이더 이동
  }); 
}

function changeColor(){
  btns.forEach(function(btn){
    btn.classList.remove("active");
  });

  this.classList.add("active");
}

