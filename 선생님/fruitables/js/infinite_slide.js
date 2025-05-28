// Step1. 이벤트 줄 요소 선택하기
let left = document.querySelector(".left");
let right = document.querySelector(".right");
let slides = document.querySelectorAll(".slide");
let slideUl = document.querySelector(".slides");

// Step2. 이벤트 등록하기
left.addEventListener("click", leftsliding);
right.addEventListener("click", rightsliding);

let firstContent = slides[0].cloneNode(true);
let lastContent = slides[slides.length - 1].cloneNode(true);
slideUl.appendChild(firstContent);
slideUl.insertBefore(lastContent, slides[0]);
slides = document.querySelectorAll(".slide"); //4개 데이터

/*
초기 장면은 1번 채널이고, 실제로도 1번 채널 위치로 이동
*/
let index = 1;
sliding(false);

/* 
 왼쪽
 1번째 --효과O--> 0번째 --효과X --> 2번째
 2번째 --효과O--> 1번째
*/
//Step3. 함수 선언하기
function leftsliding() {
  /* Step4. 함수 구현하기
 최종 기능 : index장면으로 왼쪽 이동
 1번째 --효과O--> 0번째 --효과X --> slides.length - 2번째
 2번째 --효과O--> 1번째
 3번째 --효과O--> 2번째
 4번째 --효과O--> 3번째
 index번째 --효과0--> index-1번째
*/
  index--;
  if (index <= 0) {
    sliding(true);
    index = slides.length - 2;
    setTimeout(function () {
      sliding(false);
    }, 300);
  } else {
    sliding(true);
  }
}

function rightsliding() {
/*
 오른쪽
 1번째 --효과0--> 2번째 
 2번째 --효과0--> 3번째 
 3번째 --효과0--> 4번째 
 index번째 --효과0--> index+1번째 장면
 4번째 --효과0--> 5번째 -- 효과X --> 1번째
*/
  index++;
  if (index >= slides.length - 1) {
    sliding(true);
    index = 1;
    setTimeout(function () {
      sliding(false);
    }, 300);
  } else {
    console.log(sliding(true));
  }
}

function sliding(state) {
  if (state) {
    slides.forEach(function (slide) {
      slide.style.transition = `all .3s`;
    });
  } else {
    slides.forEach(function (slide) {
      slide.style.transition = `none`;
    });
  }
  slides.forEach(function (slide) {
    slide.style.transform = `translateX(${index * -100}%)`;
  });

  return "transition : " + slides[0].style.transition;
}
