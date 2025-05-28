let left = document.querySelector('.left');
let right = document.querySelector('.right');
let slides = document.querySelector('.slide');

left.addEventListener("click", leftsliding);
right.addEventListener("click", rightsliding);

index = 0;

function leftsliding(){
  
  if(index = index - 1){
    index < slides.length - 1;
  }
  sliding();
}

function rightsliding(){
  if(index >= slides.length){
    index = 0;
  }
  sliding();
}

function sliding(){
  slides.forEach(function(slide){
    slide.style.transform = `translateX(${index*(-100)})`;
  })
}
