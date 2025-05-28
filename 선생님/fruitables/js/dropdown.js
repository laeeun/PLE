//Step1. 이벤트 줄 요소 선택하기
let pages = document.querySelector("header .dropdown_pages");
let sub = document.querySelector(".sub_menu");

//Step2. 이벤트 등록하기
pages.addEventListener("click", showDropdown);

let cnt = 0;
function showDropdown(){
  // cnt++;
  // console.log(cnt);
  // if(cnt % 2 == 1){
  //   sub.classList.add("show");
  // }
  // else {
  //   sub.classList.remove("show");
  // }

  sub.classList.toggle("show");
}
