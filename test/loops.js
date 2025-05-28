let left = document.querySelector(".left")
let right = document.querySelector(".right")
let slides = document.querySelectorAll(".slide")

left.addEventListener("click", leftsliding);
right.addEventListener("click", rightsliding);


let index = 0;

function leftsliding() {
  index = index - 1;
  if (index < 0) {
    index = slides.length - 1;
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
  })
}

