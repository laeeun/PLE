

let pages = document.querySelector(".pages");
let subList = document.querySelector(".sub_menu");

pages.addEventListener("click", showSubList);

function showSubList(){
  subList.classList.toggle("active");
}


let page = document.querySelector(".page");
let subMenu = document.querySelector("#sub_menu");

