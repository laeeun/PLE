let pages = document.querySelector(".pages");
let submenu = document.querySelector(".submenu");

pages.addEventListener("click", showList);

let cnt = 0;
let isShow = false;
function showList(){
  submenu.classList.toggle("show");
  // 1번 클릭하면 서브메뉴 나타남
  // 2번 클릭하면 서브메뉴 사라짐
  // ... 홀수번 클릭 : 서브메뉴 나타남
  // ... 짝수번 클릭 : 서브메뉴 사라짐
  // cnt++;
  // if(isShow){
  //   submenu.classList.remove("show");
  // }else {
  //   submenu.classList.add("show");
  // }
  // isShow = !isShow;
}
