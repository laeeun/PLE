
//요소선택

let btns = document.querySelectorAll("#main .btns button"); //모든 버튼 선택
let slides = document.querySelectorAll("#main .slide"); //모든 슬라이드 선택

//이벤트 리스너 등록

btns.forEach(function(btn){
  btn.addEventListener("click", sliding);  //버튼 클릭시 슬라이더 이동
  btn.addEventListener("click", changeColor); //버튼 클릭시 버튼 색상변경
})

//슬라이더 이동 함수

let index = 0;

function sliding(){
  index = this.id.charAt(1);  // 버튼 id옆 숫자를 인덱스로 사용

  console.log(index);

  slides.forEach(function(slide){
    slide.style.transform = `translateX(${index * (-100)}%)`; //가로 슬라이더 이동
  }); 
}

function changeColor(){
  btns.forEach(function(btn){
    btn.classList.remove("active");
  })

  this.classList.add("active");
}
