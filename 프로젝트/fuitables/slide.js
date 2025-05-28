

let left = document.querySelector(".s_right .left");
let right = document.querySelector(".s_right .right");
let slides = document.querySelectorAll(".s_right .slide");
let slideUl = document.querySelector(".s_right .slides");



left.addEventListener("click", leftsliding);
right.addEventListener("click", rightsliding);


let firstContent = slides[0].cloneNode(true);  //첫번째 슬라이드 복제
let lastContent = slides[slides.length - 1].cloneNode(true);  //마지막 슬라이드 복제
slideUl.appendChild(firstContent);  //복제한 첫 번째 슬라이드를 리스트 맨 끝에 추가
slideUl.insertBefore(lastContent, slides[0]); // 복제한 마지막 슬라이드를 첫 번째 슬라이드 앞에 추가
slides = document.querySelectorAll(".slide");  //복제가 완료된 슬라이드를 다시 선택


let index = 1; //복제된 슬라이드 구조에서 원본 첫번째 위치를 가르킴
sliding(false); //초기 위치 설정시 트레지션 효과 제거


function leftsliding() { //좌측이동
  index--;
  if (index <= 0) {
    sliding(true); //트레지션 활성화
    index = slides.length - 2; //복제 위치로 이동
    setTimeout(function () {
      sliding(false);
    }, 300);  // 300ms 후 트레지션 비활성화로 자연스러운 이동
  } else {
    sliding(true);
  }
}

function rightsliding() { //우측이동
  index++;
  if (index >= slides.length - 1) {
    sliding(true);                                                                                                                                                                                             
    index = 1; //원본 첫 번째 위치로 재설정
    setTimeout(function () {
      sliding(false);  
    }, 300);
  } else {
    console.log(sliding(true));
  }

}

function sliding(state) { //트레지션 제어
  if (state) {
    slides.forEach(function (slide) {
      slide.style.transition = `all .3s`;
    });
  } else {
    slides.forEach(function (slide) { //위치 이동
      slide.style.transition = `none`;
    });
  }
  slides.forEach(function (slide) {
    slide.style.transform = `translateX(${index * -100}%)`;
  });

  return "transition : " + slides[0].style.transition;
}
