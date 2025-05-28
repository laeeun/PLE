const slider = document.querySelector('.slider');
const slides = document.querySelectorAll('.slide');
const slideHeight = 30; // slide 높이
let currentIndex = 0;

// 복제 첫 요소 추가해서 무한 루프처럼 보이게
const clone = slides[0].cloneNode(true);
slider.appendChild(clone);

function nextSlide() {
  currentIndex++;
  slider.style.transition = 'transform 0.5s ease';
  slider.style.transform = `translateY(-${slideHeight * currentIndex}px)`;

  // 마지막 복제 슬라이드 도달 시
  if (currentIndex === slides.length) {
    setTimeout(() => {
      slider.style.transition = 'none';
      slider.style.transform = 'translateY(0)';
      currentIndex = 0;
    }, 600); // transition보다 약간 더 늦게
  }
}

setInterval(nextSlide, 2000); // 2.5초마다 전환
