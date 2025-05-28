//필요한 변수 선언 배열을 통하여 컬러 4가지를 담고있음
let colors = ["green", "red", "rgba(133,122,200,0.8)", "#f15025"];

//Step 1 : 셀렉트
//이벤트가 발생할 요소
//버튼을 선택
let btn = document.querySelector("#btn");
console.log(btn);

//Step 2 : 이벤트 할당
btn.addEventListener('click',changeColor);

//Step 3 : 함수선언

//배경색상을 컬러배열에서 랜덤으로 골라서 바꾼다.
function changeColor(){
    //Step 4 : 함수내용 구현하기기
    /*
    최종 목적
    색깔 배열에 랜덤하게 접근해서
    1. body의 배경색깔을 바꾼다      (style)
    2. 색깔값 텍스트내용 업데이트     (text)
    3. 색깔값에 대한 글자색 업데이트  (style)  
    */
    //랜덤 함수를 통하여 랜덤한 값을 가져옴 (랜덤한 수 0,1,2,3)
    // let randomresult = getRandomNumber();
    let randomresult = Math.floor(Math.random() * colors.length); //0,1,2,3
    //0~4사이의 실수 -> 0 ~ 3 정수
    console.log(randomresult); //0,1,2,3
    
    // 배경색을 컬러즈 배열변수의[] 랜덤값 순서로 지정
    document.body.style.backgroundColor = colors[randomresult];
    //span를 선택함 : 색깔값 컨텐츠
    let color = document.querySelector(".color");
    //span의 텍스트를 배열에서 선택하여 넣음
    color.textContent = colors[randomresult];
    color.style.color = colors[randomresult];
}

function getRandomNumber(){
    //   Math.random() * 3 --> 0,1,2 중하나가 반환됨
    // Math.floor() --> 소수점을 버러고 정수만을 취득함
    // Math.random() --> 1.3 램덤으로 발생하면 --> Math.floor(1.3) --> 1이 반환됨
    // var RandomNumber = Math.floor(Math.random() * colors.length);
    let RandomNumber = Math.floor(Math.random()*colors.length);
    
    return RandomNumber;
}