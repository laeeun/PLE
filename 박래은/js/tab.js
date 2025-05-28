


let btns = document.querySelectorAll(".tab label");
let contents = document.querySelectorAll(".tab .all_product") 


btns.forEach(function (btn) {
  btn.addEventListener("click", showTabContents); 
  btn.addEventListener("click", changeColor); 
});


function showTabContents() { 
  let activeContents = document.querySelectorAll("." + this.id);

  contents.forEach(function (content) {
    content.classList.remove("active");
  })

  activeContents.forEach(function(active){ 
    active.classList.add("active");
  });
}



function changeColor(){ 
  btns.forEach(function (btn) {
    btn.classList.remove("active");
  })

  this.classList.add("active");
}
