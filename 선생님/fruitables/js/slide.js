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

  //Step4. 함수 구현하기
  /*
  왼쪽을 클릭했을 때,
  0번째 장면 -> 2번째 장면
  1번째 장면 -> 0번째 장면
  2번째 장면 -> 1번째 장면
  index번째 장면 -> index-1번째 장면

  0번째 장면 -> translateX(0%);
  1번째 장면 -> translateX(-100%);
  2번째 장면 -> translateX(-200%);
  index번째 장면 -> translateX(index * (-100)%)
  */
}

  /*
  오른쪽을 클릭했을 때,
  0번째 장면 -> 1번째 장면
  1번째 장면 -> 2번째 장면
  2번째 장면 -> 0번째 장면
  index번째 장면 -> index+1번째 장면

  0번째 장면 -> translateX(0%);
  1번째 장면 -> translateX(-100%);
  2번째 장면 -> translateX(-200%);
  index번째 장면 -> translateX(index * (-100)%)
  */
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