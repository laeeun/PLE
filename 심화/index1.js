const slides = document.querySelectorAll(".slide");
const prev = document.querySelector(".prev");
const next = document.querySelector(".next");

let index = 0;

prev.addEventListener("click", function () {
  prevSlide();
});

next.addEventListener("click", function () {
  nextSlide();
});

function prevSlide() {
  if (index == 0) {
    index = slides.length - 1;
  }
  else {
    index--;
  }
  changeSlide();
}


function nextSlide() {
  if (index == slides.length - 1) {
    index = 0;
  }
  else {
    index++;
  }
  changeSlide();
}


function changeSlide(){
  slides.forEach(function(item){
    item.classList.remove("active");
  });

  slides[index].classList.add("active");
  //adding code to show active dot in the indicators

  let indicators = document.querySelectorAll(".indicatorContainer > div");

  indicators.forEach(function(indicator){
    indicator.classList.remove("active");
  });

  indicators[index].classList.add("active");
  resetAutoPlay();
}


//creating dot indicatoes by using JavaScript start

const indicatorContainer = document.querySelector(".indicatorContainer");
function buildIndicators(){
  for(let i =0; i < slides.length; i++){
    let element = document.createElement("div");
    element.dataset.i = i+1;
    element.setAttribute("onclick","gotoSlide(this)");

    //making frist dot as active by default
    if(i == 0){
      element.classList.add("active")
    }

    indicatorContainer.appendChild(element);
  }
}

buildIndicators();
//creating dot indicators by using JavaScript end

//creating gotoSlide Function
function gotoSlide(element){
  let k = element.dataset.iindez = k = 1;
  changeSlide();
}

//slide autoPlay Functionality
let timer = setInterval(nextSlide, 4000);

//1000 = 1 second
function resetAutoPlay() {
  clearInterval(timer); //stop the timer
  timer = setInterval(nextSlide, 4000); //again start the timer
}