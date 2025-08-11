<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입 - TimeFair</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" />

    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-100: #FFEEEA;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }
        body { background: var(--surface); font-family: 'Pretendard', sans-serif; color: var(--text-main); }
        h2 { text-align: center; font-weight: 900; font-size: 32px; color: var(--primary); margin-bottom: 30px; }
        .signup-box {
            max-width: 1000px; margin: 0 auto; margin-top:20px;
            background: var(--surface-alt); padding: 30px 40px; border-radius: 20px;
            box-shadow: 0 6px 24px rgba(0, 0, 0, 0.05);
        }
        table { width: 100%; border-collapse: collapse; background-color: var(--surface-alt); font-size: 15px; }
        th {
            background: var(--accent-100); color: var(--text-main); padding: 14px; text-align: left;
            width: 160px; vertical-align: middle;
        }
        td { padding: 14px; }
        input[type="text"], input[type="password"], select {
            width: 100%; padding: 8px 12px; border: 1px solid var(--border); border-radius: 6px;
        }
        .btn-submit {
            background: var(--accent); color: white; font-weight: 700; padding: 12px 30px;
            border-radius: 12px; border: none; margin-right: 10px;
        }
        .btn-submit:hover { background: #ff5a1f; }
        .btn-secondary {
            background: var(--surface); color: var(--primary); border: 1px solid var(--border);
            padding: 12px 30px; font-weight: 600; border-radius: 12px;
        }
        small { display: block; margin-top: 4px; }
        .btn-search-addr{
            background-color: var(--accent); color: #fff; font-size: 14px; font-weight: 600;
            height: 38px; padding: 0 16px; border: none; border-radius: 8px; white-space: nowrap;
            display: inline-flex; align-items: center; justify-content: center; cursor: pointer;
            box-shadow: 0 4px 12px rgba(255,107,53,.15); transition: background-color .2s, transform .2s;
        }
        .btn-search-addr:hover { background-color: #ff5a1f; transform: translateY(-1px); }
        .email-row{ display: flex; align-items: center; gap: 8px; }
        .email-row input, .email-row select{
            height: 38px; padding: 6px 12px; font-size: 14px; border: 1px solid var(--border); border-radius: 6px; flex: 1;
        }
        .email-row span{ font-size: 16px; line-height: 1; margin-top: 2px; color: var(--text-main); }
        .btn-upload{
            display: inline-block; padding: 8px 18px; background-color: var(--accent); color: #fff;
            font-size: 14px; font-weight: 600; border-radius: 8px; cursor: pointer;
            transition: background-color .2s, transform .2s; box-shadow: 0 4px 12px rgba(255,107,53,.15);
        }
        .btn-upload:hover{ background-color: #ff5a1f; transform: translateY(-1px); }
    </style>
</head>
<body>

<c:if test="${not empty errorMessage}">
    <script>alert('${errorMessage}');</script>
</c:if>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="signup-box">
    <h2>회원가입</h2>

    <form:form modelAttribute="signUp" action="${pageContext.request.contextPath}/signUp" method="post" enctype="multipart/form-data">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <table class="table table-bordered align-middle">
            <tbody>
                <tr>
                    <th>프로필 이미지</th>
                    <td>
                        <label for="profileImageFile" class="btn-upload">파일 선택</label>
                        <form:input path="profileImageFile" id="profileImageFile" type="file" accept="image/*" style="display:none;" />
                        <span id="fileName" style="margin-left:10px; color: var(--text-sub); font-size: 14px;">선택된 파일 없음</span>
                    </td>
                </tr>
                <tr>
                    <th>아이디</th>
                    <td>
                        <form:input path="member_id" id="member_id" placeholder="아이디 입력"
                                    required="required" minlength="5" maxlength="20"
                                    pattern="^(?=.*[a-z])[a-z0-9_-]{5,20}$" />
                        <small id="idCheckMsg" aria-live="polite"></small>
                    </td>
                </tr>
                <tr>
                    <th>닉네임</th>
                    <td>
                        <form:input path="userName" id="userName" placeholder="닉네임 입력" required="required" maxlength="30" />
                        <small id="usernameCheckMsg" aria-live="polite"></small>
                        <c:if test="${not empty errorMessage and errorMessage.contains('닉네임')}">
                            <small class="text-danger">${errorMessage}</small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <form:password path="pw" id="pw" placeholder="비밀번호" required="required" minlength="8" maxlength="64" />
                        <small id="pwRuleMsg" aria-live="polite"></small>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호 확인</th>
                    <td>
                        <form:password path="pwConfirm" id="pwConfirm" placeholder="비밀번호 확인" required="required" />
                        <small id="pwMatchMsg" aria-live="polite"></small>
                        <c:if test="${not empty errorMessage && errorMessage.contains('비밀번호')}">
                            <small class="text-danger">${errorMessage}</small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <form:input path="name" id="name" placeholder="이름" required="required"
                                    minlength="2" maxlength="20" pattern="^[A-Za-z가-힣]{2,20}$" />
                        <small id="nameMsg" aria-live="polite"></small>
                    </td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td><form:input path="birthDate" id="birthDate" readonly="true" required="required" /></td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>
                        <form:select path="gender" id="gender" required="required">
                            <form:option value="">선택</form:option>
                            <form:option value="M">남성</form:option>
                            <form:option value="F">여성</form:option>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <th>전화번호</th>
                    <td>
                        <div style="display: flex; gap: 8px;">
                            <form:select path="phone1" id="phone1" required="required">
                                <form:option value="">선택</form:option>
                                <form:option value="010">010</form:option>
                                <form:option value="011">011</form:option>
                                <form:option value="016">016</form:option>
                                <form:option value="017">017</form:option>
                                <form:option value="018">018</form:option>
                                <form:option value="019">019</form:option>
                            </form:select>
                            <form:input path="phone2" id="phone2" placeholder="1234" maxlength="4"
                                        required="required" inputmode="numeric" pattern="[0-9]{3,4}" />
                            <form:input path="phone3" id="phone3" placeholder="5678" maxlength="4"
                                        required="required" inputmode="numeric" pattern="[0-9]{4}" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>주소</th>
                    <td>
                        <div style="display: flex; gap: 10px; margin-bottom: 8px;">
                            <form:input path="addr" id="addr" readonly="true" placeholder="주소 검색" />
                            <button type="button" class="btn-search-addr" onclick="execDaumPostcode()">검색</button>
                        </div>
                        <form:input path="addrDetail" id="addrDetail" placeholder="상세주소 입력 (예: 301호)" />
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>
                        <div class="email-row">
                            <form:input path="emailId" id="emailId" placeholder="아이디 입력"
                                        required="required" maxlength="64"
                                        pattern="^[A-Za-z0-9._%+-]{1,64}$" />
                            <span>@</span>
                            <form:select path="emailDomain" id="emailDomain" onchange="toggleCustomDomain()" required="required">
                                <form:option value="">선택</form:option>
                                <form:option value="naver.com">naver.com</form:option>
                                <form:option value="gmail.com">gmail.com</form:option>
                                <form:option value="daum.net">daum.net</form:option>
                                <form:option value="hanmail.net">hanmail.net</form:option>
                                <form:option value="nate.com">nate.com</form:option>
                                <form:option value="etc">직접입력</form:option>
                            </form:select>
                        </div>
                        <div id="customDomainWrap" style="display:none; margin-top: 8px;">
                            <input type="text" id="customDomain" class="form-control" placeholder="예: example.com" />
                        </div>
                        <c:if test="${not empty errorMessage and errorMessage.contains('이메일')}">
                            <small class="text-danger">${errorMessage}</small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>전문가 여부</th>
                    <td><form:checkbox path="expert" id="expert" /></td>
                </tr>
            </tbody>
        </table>

        <div class="text-center mt-4">
            <button type="submit" class="btn-submit">가입하기</button>
            <a href="${pageContext.request.contextPath}/" class="btn-secondary">홈으로</a>
        </div>
    </form:form>
</div>

<!-- 주소 API -->
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

<!-- 생년월일 -->
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr/dist/l10n/ko.js"></script>
<script>
    flatpickr("#birthDate", {
        locale: "ko",
        dateFormat: "Y-m-d",
        maxDate: "today",
        altInput: true,
        altFormat: "Y년 m월 d일"
    });
</script>

<!-- 프로필 파일명 표시 -->
<script>
document.getElementById('profileImageFile').addEventListener('change', function () {
    const fileName = this.files.length > 0 ? this.files[0].name : '선택된 파일 없음';
    document.getElementById('fileName').textContent = fileName;
});
</script>

<!-- 아이디/닉네임/비밀번호/이름 실시간 체크 -->
<script>
/* ===== 공통 ===== */
const ctx = "${pageContext.request.contextPath}";

/* ===== 아이디 실시간 검사 ===== */
const idInput = document.getElementById("member_id");
const idMsg   = document.getElementById("idCheckMsg");
const idRegex = /^(?=.*[a-z])[a-z0-9_-]{5,20}$/;let idTimer;
let isIdValid = false;
let isIdDuplicated = false;

function liveCheckId() {
  const v = idInput.value.trim();
  isIdValid = false;
  isIdDuplicated = false;

  if (!v) { idMsg.textContent = "아이디를 입력하세요."; idMsg.style.color = "#ef4444"; return; }
  if (!idRegex.test(v)) { idMsg.textContent = "5~20자, 영소문자/숫자, '-', '_' 사용 가능"; idMsg.style.color = "#ef4444"; return; }

  idMsg.textContent = "확인 중...";
  idMsg.style.color = "#6A737D";

  fetch(ctx + "/signUp/checkId?member_id=" + encodeURIComponent(v))
    .then(r => r.text())
    .then(result => {
      if (result === "duplicated") {
        idMsg.textContent = "이미 사용 중인 아이디입니다.";
        idMsg.style.color = "#ef4444";
        isIdDuplicated = true; isIdValid = false;
      } else {
        idMsg.textContent = "사용 가능한 아이디입니다.";
        idMsg.style.color = "#10b981";
        isIdDuplicated = false; isIdValid = true;
      }
    })
    .catch(() => {
      idMsg.textContent = "확인 실패. 잠시 후 다시 시도해주세요.";
      idMsg.style.color = "#ef4444"; isIdValid = false;
    });
}
idInput.addEventListener("input", () => { clearTimeout(idTimer); idTimer = setTimeout(liveCheckId, 400); });
idInput.addEventListener("blur", liveCheckId);

/* ===== 닉네임 실시간 검사 ===== */
let usernameTimer;
function checkUsername() {
  const username = document.getElementById("userName").value.trim();
  const msg = document.getElementById("usernameCheckMsg");
  msg.textContent = "";

  clearTimeout(usernameTimer);
  usernameTimer = setTimeout(() => {
    fetch(ctx + "/signUp/checkUsername?userName=" + encodeURIComponent(username))
      .then(res => res.text())
      .then(result => {
        msg.textContent = (result === "duplicated")
          ? "이미 사용 중인 닉네임입니다."
          : "사용 가능한 닉네임입니다.";
        msg.style.color = (result === "duplicated") ? "#ef4444" : "#10b981";
      });
  }, 500);
}
document.getElementById("userName").addEventListener("input", checkUsername);

/* ===== 이름 실시간 검사 ===== */
const nameInput = document.getElementById("name");
const nameMsg   = document.getElementById("nameMsg");
const nameRegex = /^[A-Za-z가-힣]{2,20}$/; // 2~20자, 한글/영문만 (공백/숫자/특수 X)
let isNameValid = false;

function liveCheckName() {
  const v = nameInput.value;
  if (!v) { nameMsg.textContent = ""; isNameValid = false; return false; }

  if (/\s/.test(v)) {
    nameMsg.textContent = "공백 없이 입력해주세요.";
    nameMsg.style.color = "#ef4444";
    isNameValid = false;
    return false;
  }
  if (!nameRegex.test(v)) {
    nameMsg.textContent = "2~20자, 한글/영문만 사용 가능합니다.";
    nameMsg.style.color = "#ef4444";
    isNameValid = false;
    return false;
  }
  nameMsg.textContent = "좋은 이름이에요.";
  nameMsg.style.color = "#10b981";
  isNameValid = true;
  return true;
}
nameInput.addEventListener("input", liveCheckName);
nameInput.addEventListener("blur", liveCheckName);

/* ===== 비밀번호 규칙 & 일치 검사 ===== */
const pwInput = document.getElementById("pw");
const pw2Input = document.getElementById("pwConfirm");
const pwRuleMsg = document.getElementById("pwRuleMsg");
const pwMatchMsg = document.getElementById("pwMatchMsg");

function validatePasswordRules() {
  const pw = pwInput.value;
  const uid = idInput.value.trim().toLowerCase();
  const uname = (document.getElementById("userName").value || "").trim().toLowerCase();

  const hasLen = pw.length >= 8 && pw.length <= 64;
  const hasLetter = /[A-Za-z]/.test(pw);
  const hasNumber = /\d/.test(pw);
  const hasSpecial = /[~`!@#$%^&*()_\-+={[}\]|\\:;"'<,>.?/]/.test(pw);
  const noSpace = !/\s/.test(pw);
  const noTriple = !/(.)\1\1/.test(pw);

  let notContainIdName = true;
  const lowerPw = pw.toLowerCase();
  if (uid && uid.length >= 3 && lowerPw.includes(uid)) notContainIdName = false;
  if (uname && uname.length >= 3 && lowerPw.includes(uname)) notContainIdName = false;

  const ok = hasLen && hasLetter && hasNumber && hasSpecial && noSpace && noTriple && notContainIdName;

  if (!pw) {
    pwRuleMsg.textContent = "";
  } else if (!ok) {
    pwRuleMsg.textContent = "8~64자, 영문/숫자/특수문자 포함, 공백·연속3자 금지, 아이디/닉네임 포함 금지";
    pwRuleMsg.style.color = "#ef4444";
  } else {
    pwRuleMsg.textContent = "안전한 비밀번호예요.";
    pwRuleMsg.style.color = "#10b981";
  }
  return ok;
}

function validatePasswordMatch() {
  const pw = pwInput.value;
  const pw2 = pw2Input.value;
  if (!pw2) { pwMatchMsg.textContent = ""; return false; }
  if (pw && pw === pw2) {
    pwMatchMsg.textContent = "비밀번호가 일치합니다.";
    pwMatchMsg.style.color = "#10b981";
    return true;
  } else {
    pwMatchMsg.textContent = "비밀번호가 일치하지 않습니다.";
    pwMatchMsg.style.color = "#ef4444";
    return false;
  }
}

pwInput.addEventListener("input", () => { validatePasswordRules(); validatePasswordMatch(); });
pw2Input.addEventListener("input", validatePasswordMatch);
pwInput.addEventListener("blur", () => { validatePasswordRules(); });
pw2Input.addEventListener("blur", validatePasswordMatch);
</script>

<!-- 이메일 도메인 직접입력 토글 + required 전환 -->
<script>
  const sel = document.getElementById("emailDomain");
  const customWrap = document.getElementById("customDomainWrap");
  const custom = document.getElementById("customDomain");

  function applyEmailDomainRequired() {
    if (sel.value === "etc") {
      customWrap.style.display = "block";
      custom.setAttribute("required", "required");
      custom.setAttribute("pattern", "^[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    } else {
      customWrap.style.display = "none";
      custom.removeAttribute("required");
      custom.removeAttribute("pattern");
      custom.value = "";
    }
  }
  function toggleCustomDomain(){ applyEmailDomainRequired(); }
  sel.addEventListener("change", applyEmailDomainRequired);
  applyEmailDomainRequired();
</script>

<!-- 제출 전 최종 체크 -->
<script>
  document.querySelector("form").addEventListener("submit", function (e) {
      // 1) 아이디 최종 확인
      if (!isIdValid || isIdDuplicated) {
          liveCheckId();
          e.preventDefault();
          alert(isIdDuplicated ? "이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요." : "아이디를 확인해주세요. (형식/중복 검사)");
          return;
      }
      // 2) 이름 형식 확인
      if (!liveCheckName()) {
          e.preventDefault();
          nameInput.focus();
          alert("이름 형식을 확인해주세요. (2~20자, 한글/영문만)");
          return;
      }
      // 3) 비밀번호 규칙/일치 확인
      const rulesOK = validatePasswordRules();
      const matchOK = validatePasswordMatch();
      if (!rulesOK || !matchOK) {
          e.preventDefault();
          if (!rulesOK) pwInput.focus(); else pw2Input.focus();
          alert(!rulesOK ? "비밀번호 규칙을 확인해주세요." : "비밀번호가 서로 일치하지 않습니다.");
          return;
      }
  });
</script>

</body>
</html>
