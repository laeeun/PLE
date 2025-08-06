<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 40px;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .signup-box {
            width: 100%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(12px);
            border-radius: 20px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            padding: 40px 35px;
        }

        .signup-box h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #7e22ce;
            font-weight: bold;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            font-weight: 500;
            margin-bottom: 6px;
            display: block;
        }

        input, select {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 12px;
            background: rgba(255, 255, 255, 0.6);
            backdrop-filter: blur(6px);
            font-size: 14px;
            color: #4c1d95;
            box-shadow: inset 0 1px 2px rgba(0,0,0,0.1);
        }

        input[type="checkbox"] {
            width: auto;
            padding: 0;
            border-radius: 0;
            height: auto;
            margin-left: 5px;
            vertical-align: middle;
            box-shadow: none;
            background: none;
        }

        .btn-purple {
            width: 100%;
            padding: 14px;
            background: linear-gradient(to right, #a855f7, #ec4899);
            border: none;
            color: white;
            font-weight: bold;
            font-size: 16px;
            border-radius: 12px;
            box-shadow: 0 0 14px rgba(168, 85, 247, 0.4);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .btn-purple:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 18px rgba(168, 85, 247, 0.5);
        }

        .btn-purple::after {
            content: "";
            position: absolute;
            top: 0;
            left: -75%;
            width: 50%;
            height: 100%;
            background: linear-gradient(to right, rgba(255,255,255,0.3), rgba(255,255,255,0));
            transform: skewX(-20deg);
            animation: shine 2.5s infinite;
        }

        @keyframes shine {
            0% { left: -75%; }
            100% { left: 125%; }
        }

        .row-flex {
            display: flex;
            gap: 10px;
        }
        
        .btn-purple-small {
		    background: linear-gradient(to right, #a855f7, #ec4899);
		    color: white;
		    font-weight: bold;
		    font-size: 13px;
		    border: none;
		    border-radius: 8px;
		    padding: 6px 12px;
		    box-shadow: 0 0 6px rgba(168, 85, 247, 0.4);
		    transition: all 0.2s ease;
		    white-space: nowrap;
		}
		
		.btn-purple-small:hover {
		    background: linear-gradient(to right, #9333ea, #f43f5e);
		    box-shadow: 0 4px 10px rgba(168, 85, 247, 0.5);
		}
		
		#usernameCheckMsg {
    min-height: 20px; /* or 1.2em 정도 */
    transition: all 0.2s ease;
    display: block;
}
    </style>
</head>
<body>
<c:if test="${not empty errorMessage}">
	<script>
	    alert('${errorMessage}');
	</script>
</c:if>

<div class="signup-box">
    <h2>회원가입</h2>

    <form:form modelAttribute="signUp" action="${pageContext.request.contextPath}/signUp" method="post" enctype="multipart/form-data">

        <div class="form-group">
            <label for="profileImageFile">프로필 이미지</label>
            <form:input path="profileImageFile" id="profileImageFile" type="file" accept="image/*" />
        </div>

        <div class="form-group">
		    <label for="member_id">아이디</label>
		    <div class="row-flex align-items-center" style="gap: 6px;">
		        <form:input path="member_id" id="member_id" placeholder="아이디 입력" style="flex: 1;" />
		        <button type="button" class="btn-purple-small" onclick="checkId()">중복 확인</button>
		    </div>
		    <small id="idCheckMsg" style="margin-top: 5px; display: block;"></small>
		</div>



        <div class="form-group">
		    <label for="userName">닉네임</label>
		    <form:input path="userName" id="userName" placeholder="닉네임" />
		
		    <c:if test="${not empty errorMessage and errorMessage.contains('닉네임')}">
		        <small class="text-danger">${errorMessage}</small>
		    </c:if>
		    <small id="usernameCheckMsg" style="margin-top: 5px; display: block;"></small>
		</div>

        <div class="form-group">
            <label for="pw">비밀번호</label>
            <form:password path="pw" id="pw" placeholder="비밀번호" />
        </div>

        <div class="form-group">
		    <label for="pwConfirm">비밀번호 확인</label>
		    <form:password path="pwConfirm" id="pwConfirm" placeholder="비밀번호 확인" />
		    <c:if test="${not empty errorMessage && errorMessage.contains('비밀번호')}">
		        <small class="text-danger">${errorMessage}</small>
		    </c:if>
		</div>

        <div class="form-group">
            <label for="name">이름</label>
            <form:input path="name" id="name" placeholder="이름" />
        </div>

        <div class="form-group">
            <label for="birthDate">생년월일</label>
            <form:input path="birthDate" id="birthDate" readonly="true" />
        </div>

        <div class="form-group">
            <label for="gender">성별</label>
            <form:select path="gender" id="gender">
                <form:option value="">선택</form:option>
                <form:option value="M">남성</form:option>
                <form:option value="F">여성</form:option>
            </form:select>
        </div>

        <div class="form-group">
            <label>전화번호</label>
            <div class="row-flex">
                <form:select path="phone1" id="phone1" required="true">
                    <form:option value="">선택</form:option>
                    <form:option value="010">010</form:option>
                    <form:option value="011">011</form:option>
                    <form:option value="016">016</form:option>
                    <form:option value="017">017</form:option>
                    <form:option value="018">018</form:option>
                    <form:option value="019">019</form:option>
                </form:select>
                <form:input path="phone2" id="phone2" placeholder="1234" pattern="\d{3,4}" minlength="3" maxlength="4" required="true" />
                <form:input path="phone3" id="phone3" placeholder="5678" pattern="\d{4}" minlength="4" maxlength="4" required="true" />
            </div>
        </div>

        <div class="form-group">
		    <label>주소</label>
		    <div class="row-flex align-items-center" style="gap: 6px;">
		        <form:input path="addr" id="addr" readonly="true" placeholder="주소를 검색하세요" style="flex: 1;" />
		        <button type="button" class="btn-purple-small" onclick="execDaumPostcode()">검색</button>
		    </div>
		    <form:input path="addrDetail" id="addrDetail" placeholder="상세주소 입력 (예: 301호)" style="margin-top: 10px;" />
		</div>



        <!-- 이메일 입력 영역 -->
		<p>이메일</p>
		<div style="display: flex; align-items: center; gap: 8px; margin-bottom: 10px;">
		    <form:input path="emailId" id="emailId" placeholder="아이디 입력" style="flex: 1;" required="true" />
		    <span>@</span>
		    <form:select path="emailDomain" id="emailDomain" style="flex: 1;" onchange="toggleCustomDomain()" required="true">
		        <form:option value="">선택</form:option>
		        <form:option value="naver.com">naver.com</form:option>
		        <form:option value="gmail.com">gmail.com</form:option>
		        <form:option value="daum.net">daum.net</form:option>
		        <form:option value="hanmail.net">hanmail.net</form:option>
		        <form:option value="nate.com">nate.com</form:option>
		        <form:option value="etc">직접입력</form:option>
		    </form:select>
		</div>
		
		<!-- 직접입력 도메인 -->
		<div id="customDomainWrap" style="display: none; margin-bottom: 10px;">
		    <input type="text" id="customDomain" class="form-control" placeholder="예: example.com" />
		</div>
		
		<!-- 📌 이메일 중복 에러 메시지 출력 -->
		<c:if test="${not empty errorMessage and errorMessage.contains('이메일')}">
		    <small class="text-danger">${errorMessage}</small>
		</c:if>
		



        <div class="form-group" style="margin-top: 15px;">
            <label for="expert">전문가 여부</label>
            <form:checkbox path="expert" id="expert" />
        </div>

        <button type="submit" class="btn-purple">가입하기</button>
        <a href="${pageContext.request.contextPath}/" class="btn-purple" style="margin-top: 10px; text-align: center; display: block; text-decoration: none;">
            홈으로
        </a>
    </form:form>
</div>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.getElementById("addr").value = data.address;
        }
    }).open();
}
</script>

<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    flatpickr("#birthDate", {
        locale: "ko",
        dateFormat: "Y-m-d",
        maxDate: "today",
        altInput: true,
        altFormat: "Y년 m월 d일"
    });
});
</script>
<script>
function checkId() {
    const member_id = document.getElementById("member_id").value;
    const msg = document.getElementById("idCheckMsg");

    msg.textContent = "";
    msg.style.color = "";

    if (!member_id) {
        msg.textContent = "아이디를 입력하세요.";
        msg.style.color = "#ef4444";
        isIdChecked = false;  // 확인 안됨 처리
        return;
    }

    fetch('${pageContext.request.contextPath}/signUp/checkId?member_id=' + member_id)
        .then(res => res.text())
        .then(result => {
            isIdChecked = true;  // ✅ 중복 확인은 누름
            if (result === "duplicated") {
                msg.textContent = "이미 사용 중인 아이디입니다.";
                msg.style.color = "#ef4444";
                isIdDuplicated = true;  // ❌ 중복됨
            } else {
                msg.textContent = "사용 가능한 아이디입니다.";
                msg.style.color = "#10b981";
                isIdDuplicated = false;  // ✅ 사용 가능
            }
        });
}


let usernameTimer;  // 타이머 저장용 전역 변수

function checkUsername() {
    const username = document.getElementById("userName").value;
    const msg = document.getElementById("usernameCheckMsg");

    msg.textContent = "";
    msg.style.color = "";

    if (!username) {
        msg.textContent = "닉네임을 입력하세요.";
        msg.style.color = "#ef4444";
        return;
    }

    // 타이머 초기화 (입력 중엔 fetch 안 날림)
    clearTimeout(usernameTimer);

    // 디바운스: 입력 후 500ms 뒤에 실행
    usernameTimer = setTimeout(() => {
        fetch('${pageContext.request.contextPath}/signUp/checkUsername?userName=' + username)
            .then(res => res.text())
            .then(result => {
                if (result === "duplicated") {
                    msg.textContent = "이미 사용 중인 닉네임입니다.";
                    msg.style.color = "#ef4444";
                } else {
                    msg.textContent = "사용 가능한 닉네임입니다.";
                    msg.style.color = "#10b981";
                }
            });
    }, 500); // 0.5초 후 실행
}
</script>

<script>
document.addEventListener("DOMContentLoaded", function () {
    const pw = document.getElementById("pw");
    const pwConfirm = document.getElementById("pwConfirm");
    const msg = document.createElement("small");
    msg.style.display = "block";
    msg.style.marginTop = "5px";
    pwConfirm.parentNode.appendChild(msg);

    function validatePwMatch() {
        if (pw.value && pwConfirm.value) {
            if (pw.value === pwConfirm.value) {
                msg.textContent = "비밀번호가 일치합니다.";
                msg.style.color = "#10b981";
            } else {
                msg.textContent = "비밀번호가 일치하지 않습니다.";
                msg.style.color = "#ef4444";
            }
        } else {
            msg.textContent = "";
        }
    }

    pw.addEventListener("input", validatePwMatch);
    pwConfirm.addEventListener("input", validatePwMatch);
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.getElementById("userName");

    usernameInput.addEventListener("input", function () {
        checkUsername();  // 👉 닉네임 자동 중복 체크 실행
    });
});
</script>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");  // form:form은 실제로 <form> 태그로 렌더됨

    form.addEventListener("submit", function (e) {
        if (!isIdChecked) {
            e.preventDefault();  // 전송 막기
            alert("아이디 중복 확인을 해주세요.");
            return;
        }

        if (isIdDuplicated) {
            e.preventDefault();
            alert("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
            return;
        }
    });
});
</script>
</body>
</html>

<script>
function sendEmailCode() {
    const email = document.getElementById("emailId").value + "@" + document.getElementById("emailDomain").value;

    fetch(`/mail/sendCode?email=${email}`)
        .then(res => res.text())
        .then(result => {
            alert(result);
        });
}

function verifyEmailCode() {
    const email = document.getElementById("emailId").value + "@" + document.getElementById("emailDomain").value;
    const code = document.getElementById("emailCode").value;

    fetch(`/mail/verifyCode?email=${email}&code=${code}`)
        .then(res => res.text())
        .then(result => {
            document.getElementById("emailCodeMsg").textContent = result;
        });
}
</script>


</body>
</html>