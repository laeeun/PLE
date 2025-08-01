<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
  <!-- ✅ Lucide 아이콘 사용 -->
  <script src="https://unpkg.com/lucide@latest"></script>

  <style>
    * {
      box-sizing: border-box;
    }

    body {
      margin: 0;
      padding: 0;
      font-family: 'Pretendard', sans-serif;
      height: 100vh;
      background: linear-gradient(135deg, #a6c1ee, #fbc2eb);
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .login-box {
      width: 360px;
      padding: 40px 30px 30px;
      border-radius: 24px;
      background: rgba(255, 255, 255, 0.08);
      backdrop-filter: blur(18px);
      border: 2px solid rgba(211, 153, 255, 0.25);
      outline: 2px solid rgba(211, 153, 255, 0.5);
      outline-offset: -6px;
      box-shadow:
        0 0 20px rgba(211, 153, 255, 0.4),
        0 0 60px rgba(211, 153, 255, 0.3),
        0 0 100px rgba(211, 153, 255, 0.2);
      text-align: center;
      color: white;
      position: relative;
    }

    .logo-circle {
      width: 70px;
      height: 70px;
      background: rgba(255, 255, 255, 0.15);
      border-radius: 50%;
      backdrop-filter: blur(8px);
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 0 auto 30px;
      box-shadow: 0 0 20px rgba(211, 153, 255, 0.4);
    }

    .logo-circle i {
      width: 36px;
      height: 36px;
      color: white;
    }

    .input-group {
      position: relative;
      margin-bottom: 20px;
    }

    .input-group input {
      width: 100%;
      padding: 12px 14px;
      border: none;
      border-radius: 14px;
      background: rgba(255, 255, 255, 0.2);
      color: white;
      font-size: 15px;
      outline: none;
      backdrop-filter: blur(6px);
    }

    .input-group input::placeholder {
      color: rgba(255, 255, 255, 0.7);
    }

    .login-btn {
      width: 100%;
      padding: 12px;
      background: linear-gradient(to right, #d399ff, #c084fc);
      border: none;
      border-radius: 14px;
      font-size: 16px;
      font-weight: bold;
      color: #fff;
      cursor: pointer;
      transition: all 0.3s ease;
      box-shadow:
        0 0 12px rgba(211, 153, 255, 0.6),
        0 0 24px rgba(211, 153, 255, 0.4),
        0 0 36px rgba(211, 153, 255, 0.2);
    }

    .login-btn:hover {
      transform: scale(1.03);
      box-shadow:
        0 0 16px rgba(211, 153, 255, 0.6),
        0 0 30px rgba(211, 153, 255, 0.4),
        0 0 48px rgba(211, 153, 255, 0.3);
    }

    .links {
      margin-top: 30px;
      font-size: 13px;
    }

    .links a {
      margin: 0 6px;
      color: rgba(255, 255, 255, 0.9);
      text-decoration: none;
    }

    .links a:hover {
      text-decoration: underline;
    }

    .error {
      color: #ff8080;
      font-size: 0.9em;
      margin-top: 12px;
    }
  </style>
</head>
<body>

  <div class="login-box">
    <div class="logo-circle">
      <i data-lucide="user"></i>
    </div>

    <form action="${pageContext.request.contextPath}/login" method="post">
       <div class="input-group">
         <input type="text" name="member_id" placeholder="아이디" required />
       </div>

       <div class="input-group">
         <input type="password" name="pw" placeholder="비밀번호" required />
       </div>

	    <c:if test="${showCaptcha}">
	    <div class="input-group">
	      <p style="color:white; font-size: 13px;">보안문자를 입력해주세요: <strong>${captcha}</strong></p>
	      <input type="text" name="captchaInput" placeholder="보안문자 입력" required />
	    </div>
 	</c:if>
 	
 	<c:if test="${pwResetSuccess}">
	  <script>
	    alert("임시 비밀번호가 이메일로 발송되었습니다. 메일을 확인해주세요.");
	  </script>
	</c:if>

      <button type="submit" class="login-btn">LOGIN</button>

      <c:if test="${not empty error}">
        <div class="error">${error}</div>
      </c:if>
    </form>

    <div class="links">
      <a href="${pageContext.request.contextPath}/findId">아이디 찾기</a> |
      <a href="${pageContext.request.contextPath}/findPw">비밀번호 찾기</a> |
      <a href="${pageContext.request.contextPath}/signUp/signUp">회원가입</a> |
      <a href="${pageContext.request.contextPath}/">홈</a>
    </div>
  </div>

  <!-- ✅ Lucide 아이콘 실행 -->
  <script>
    lucide.createIcons();
  </script>
  
  <jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
  
</body>
</html>
