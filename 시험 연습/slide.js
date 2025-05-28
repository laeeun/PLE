let left = document.querySelector(".left");
let right = document.querySelector(".right");
let slides = document.querySelectorAll(".slide");
let slideUl = document.querySelector(".slides")


left.addEventListener("click", leftSliding);
right.addEventListener("click", rightSliding);

let firstContent = slides[0].cloneNode(true);
let lastContent = slides[slides.length - 1].cloneNode(true);
slideUl.appendChild(firstContent);
slideUl.insertBefore(lastContent, slides[0]);
slides = document.querySelectorAll(".slide");

let index = 1;
sliding(false);

function leftSliding() { //좌측이동
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

function rightSliding() { //우측이동
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

}
