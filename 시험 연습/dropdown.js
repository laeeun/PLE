

let page = document.querySelector(".page");
let subMenu = document.querySelector("#sub_menu");

console.log(page);

page.addEventListener("click", showSubMenu);

let cnt = 0;

function showSubMenu(){

  // cnt++;
  // if(cnt % 2 == 1){
  //   subMenu.classList.remove("display_none");
  // }else{
  //   subMenu.classList.add("display_none");
  // }
  // console.log(cnt);

  subMenu.classList.toggle("active"); 

}