
// 요소 선택

let btns = document.querySelectorAll("#tab label"); // 탭 버튼(라벨) 선택
let contents = document.querySelectorAll("#tab .all_products") // 모든 콘텐츠 영역 선택

// 이벤트 설정
btns.forEach(function (btn) {
  btn.addEventListener("click", showTabContents); //콘텐츠 표시 함수
  btn.addEventListener("click", changeColor); //버튼 색상 변경 함수
});


//콘텐츠 전환 기능(showTabContents)

function showTabContents() { //현재 버튼의 id와 동일한 클래스를 가진 요소 찾기
  let activeContents = document.querySelectorAll("." + this.id);

  contents.forEach(function (content) { //모든 콘텐츠 비활성화
    content.classList.remove("active");
  })

  activeContents.forEach(function(active){ //해당 클래스의 콘텐츠만 활성화
    active.classList.add("active");
  });
}

//버튼 스타일 변경

function changeColor(){ //모든 버튼의 활성화 상태 제거
  btns.forEach(function (btn) {
    btn.classList.remove("active");
  })

  //클릭한 버튼만 활성화 상태 추가
  this.classList.add("active");
}
