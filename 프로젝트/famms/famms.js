

//html안에 head 에 자바스크립트 링크를 넣을때는 defer를 꼭 넣기 !!!!


//1.셀렉트하기
let pages = document.querySelector(".pages");
let subList = document.querySelector(".sub_menu");
console.log(pages);

//2.이벤트 할당
pages.addEventListener("click", showSubList);

let cnt = 0; // 클릭 횟수

//3.함수 선언
function showSubList(){
  //4. 함수 내부 코드 작성하기
  /**
   * 최종 :
   * 한번 클릭하면 서브메뉴가 display:block;
   * 두번 클릭하면 서브메뉴가 display:none;
   */
  
  // cnt++;
  // if(cnt % 2 == 1){ //클릭 횟수가 홀수
  //   subList.style.display = "block";
  //   // subList.classList.remove("display_none");
  // }else{ //클릭횟수가 짝수
  //   subList.style.display = "none";
  //   // subList.classList.add("display_none");
  // }

  subList.classList.toggle("display_none");
  //위에 꺼랑 같음

  console.log(cnt);

}