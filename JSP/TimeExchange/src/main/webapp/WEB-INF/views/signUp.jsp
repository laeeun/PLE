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
        body {
            background: var(--surface);
            font-family: 'Pretendard', sans-serif;
            color: var(--text-main);
        }
        h2 {
            text-align: center;
            font-weight: 900;
            font-size: 32px;
            color: var(--primary);
            margin-bottom: 30px;
        }
        .signup-box {
            max-width: 1000px;
            margin: 0 auto;
            margin-top:20px;
            background: var(--surface-alt);
            padding: 30px 40px;
            border-radius: 20px;
            box-shadow: 0 6px 24px rgba(0, 0, 0, 0.05);
            
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--surface-alt);
            font-size: 15px;
        }
        th {
            background: var(--accent-100);
            color: var(--text-main);
            padding: 14px;
            text-align: left;
            width: 160px;
            vertical-align: middle;
        }
        td {
            padding: 14px;
        }
        input[type="text"], input[type="password"], select {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid var(--border);
            border-radius: 6px;
        }
        .btn-submit {
            background: var(--accent);
            color: white;
            font-weight: 700;
            padding: 12px 30px;
            border-radius: 12px;
            border: none;
            margin-right: 10px;
        }
        .btn-submit:hover {
            background: #ff5a1f;
        }
        .btn-secondary {
            background: var(--surface);
            color: var(--primary);
            border: 1px solid var(--border);
            padding: 12px 30px;
            font-weight: 600;
            border-radius: 12px;
        }
        small {
            display: block;
            margin-top: 4px;
        }
        .btn-check-id {
		  background-color: transparent;
		  color: var(--accent);
		  border: 2px solid var(--accent);
		  padding: 6px 14px;
		  font-size: 14px;
		  font-weight: 700;
		  border-radius: 8px;
		  transition: background-color 0.2s, color 0.2s;
		  white-space: nowrap; /* ✅ 줄바꿈 방지 */
  		  display: inline-flex; /* ✅ 텍스트 정렬을 위한 flex */
		}
		
		.btn-check-id:hover {
		  background-color: var(--accent);
		  color: white;
		}
		.btn-search-addr {
		  background-color: var(--accent);
		  color: white;
		  font-size: 14px;
		  font-weight: 600;
		  height: 38px;
		  padding: 0 16px;
		  border: none;
		  border-radius: 8px;
		  white-space: nowrap;            /* ✅ 줄바꿈 방지 */
		  display: inline-flex;
		  align-items: center;
		  justify-content: center;
		  cursor: pointer;
		  box-shadow: 0 4px 12px rgba(255, 107, 53, 0.15);
		  transition: background-color 0.2s, transform 0.2s;
		}
		
		.btn-search-addr:hover {
		  background-color: #ff5a1f;
		  transform: translateY(-1px);
		}
		.email-row {
		  display: flex;
		  align-items: center;         /* ✅ 수직 정렬 */
		  gap: 8px;
		}
		
		.email-row input,
		.email-row select {
		  height: 38px;
		  padding: 6px 12px;
		  font-size: 14px;
		  border: 1px solid var(--border);
		  border-radius: 6px;
		  flex: 1;
		}
		
		.email-row span {
		  font-size: 16px;
		  line-height: 1;
		  margin-top: 2px;             /* 살짝 내림 (시각적 정렬) */
		  color: var(--text-main);
		}
		.btn-upload {
		  display: inline-block;
		  padding: 8px 18px;
		  background-color: var(--accent);
		  color: #fff;
		  font-size: 14px;
		  font-weight: 600;
		  border-radius: 8px;
		  cursor: pointer;
		  transition: background-color 0.2s, transform 0.2s;
		  box-shadow: 0 4px 12px rgba(255, 107, 53, 0.15);
		}
		
		.btn-upload:hover {
		  background-color: #ff5a1f;
		  transform: translateY(-1px);
		}
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
                        <div style="display: flex; gap: 10px;">
                            <form:input path="member_id" id="member_id" placeholder="아이디 입력" />
                            <button type="button" class="btn-check-id" onclick="checkId()">중복 확인</button>
                        </div>
                        <small id="idCheckMsg"></small>
                    </td>
                </tr>
                <tr>
                    <th>닉네임</th>
                    <td>
                        <form:input path="userName" id="userName" placeholder="닉네임 입력" />
                        <small id="usernameCheckMsg"></small>
                        <c:if test="${not empty errorMessage and errorMessage.contains('닉네임')}">
                            <small class="text-danger">${errorMessage}</small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><form:password path="pw" id="pw" placeholder="비밀번호" /></td>
                </tr>
                <tr>
                    <th>비밀번호 확인</th>
                    <td>
                        <form:password path="pwConfirm" id="pwConfirm" placeholder="비밀번호 확인" />
                        <c:if test="${not empty errorMessage && errorMessage.contains('비밀번호')}">
                            <small class="text-danger">${errorMessage}</small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><form:input path="name" id="name" placeholder="이름" /></td>
                </tr>
                <tr>
                    <th>생년월일</th>
                    <td><form:input path="birthDate" id="birthDate" readonly="true" /></td>
                </tr>
                <tr>
                    <th>성별</th>
                    <td>
                        <form:select path="gender" id="gender">
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
                            <form:select path="phone1" id="phone1">
                                <form:option value="">선택</form:option>
                                <form:option value="010">010</form:option>
                                <form:option value="011">011</form:option>
                                <form:option value="016">016</form:option>
                                <form:option value="017">017</form:option>
                                <form:option value="018">018</form:option>
                                <form:option value="019">019</form:option>
                            </form:select>
                            <form:input path="phone2" id="phone2" placeholder="1234" maxlength="4" />
                            <form:input path="phone3" id="phone3" placeholder="5678" maxlength="4" />
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
						    <form:input path="emailId" id="emailId" placeholder="아이디 입력" />
						    <span>@</span>
						    <form:select path="emailDomain" id="emailDomain" onchange="toggleCustomDomain()">
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

<!-- 아이디/닉네임 체크 -->
<script>
let isIdChecked = false;
let isIdDuplicated = false;
document.getElementById('profileImageFile').addEventListener('change', function () {
    const fileName = this.files.length > 0 ? this.files[0].name : '선택된 파일 없음';
    document.getElementById('fileName').textContent = fileName;
});
function checkId() {
    const member_id = document.getElementById("member_id").value;
    const msg = document.getElementById("idCheckMsg");
    msg.textContent = "";

    if (!member_id) {
        msg.textContent = "아이디를 입력하세요.";
        msg.style.color = "#ef4444";
        isIdChecked = false;
        return;
    }

    fetch('${pageContext.request.contextPath}/signUp/checkId?member_id=' + member_id)
        .then(res => res.text())
        .then(result => {
            isIdChecked = true;
            if (result === "duplicated") {
                msg.textContent = "이미 사용 중인 아이디입니다.";
                msg.style.color = "#ef4444";
                isIdDuplicated = true;
            } else {
                msg.textContent = "사용 가능한 아이디입니다.";
                msg.style.color = "#10b981";
                isIdDuplicated = false;
            }
        });
}

let usernameTimer;
function checkUsername() {
    const username = document.getElementById("userName").value;
    const msg = document.getElementById("usernameCheckMsg");
    msg.textContent = "";

    clearTimeout(usernameTimer);
    usernameTimer = setTimeout(() => {
        fetch('${pageContext.request.contextPath}/signUp/checkUsername?userName=' + username)
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
</script>

<script>
    document.getElementById("emailDomain").addEventListener("change", function () {
        const wrap = document.getElementById("customDomainWrap");
        wrap.style.display = this.value === "etc" ? "block" : "none";
    });

    document.getElementById("pwConfirm").addEventListener("input", () => {
        const pw = document.getElementById("pw").value;
        const confirm = document.getElementById("pwConfirm").value;
        const msg = document.getElementById("pwConfirm").nextElementSibling || document.createElement("small");
        msg.style.marginTop = "5px";
        if (pw && confirm) {
            msg.textContent = pw === confirm ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.";
            msg.style.color = pw === confirm ? "#10b981" : "#ef4444";
            document.getElementById("pwConfirm").after(msg);
        }
    });

    document.querySelector("form").addEventListener("submit", function (e) {
        if (!isIdChecked) {
            e.preventDefault();
            alert("아이디 중복 확인을 해주세요.");
            return;
        }
        if (isIdDuplicated) {
            e.preventDefault();
            alert("이미 사용 중인 아이디입니다. 다른 아이디를 입력해주세요.");
            return;
        }
    });
</script>

</body>
</html>
