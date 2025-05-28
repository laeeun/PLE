
let left = document.querySelector(".left");
let right = document.querySelector(".right");
let slides = document.querySelectorAll(".slide");

left.addEventListener("click", leftSliding);
right.addEventListener("click", rightSliding);


let index = 0;

function leftSliding(){
  index = index - 1;
  if(index < 0){
    index = slides.length - 1;
  }
  Sliding();
}

function rightSliding(){
  index++;
  if(index >= slides.length){
    index = 0;
  }
  Sliding();
}


function Sliding(){
  slides.forEach(function(slide){
    slide.style.transform = `translateX(${index*(-100)}%)`;
  });
}
