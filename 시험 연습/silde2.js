
//Step1. 이벤트 줄 요소 선택하기
let left = document.querySelector(".search_right .left");
let right = document.querySelector(".search_right .right");
let slides = document.querySelectorAll(".search_right .slide");

//Step2. 이벤트 등록하기
//이벤트 줄 요소.addEventListener();
left.addEventListener("click", leftsliding);
right.addEventListener("click", rightsliding);

let index = 0; //초기화 : 1번만 수행
//index : tv채널 : 몇번째 채널

//Step3. 함수 선언하기
function leftsliding(){
  index = index -1;
  if(index < 0){
    index = slides.length - 1 ;
  }
  sliding();

}

function rightsliding(){
  index++;
  if(index >= slides.length){
    index = 0;
  }
  sliding();
}

function sliding(){
  slides.forEach(function(slide){
    slide.style.transform = `translateX(${index*(-100)}%)`;
  });
}