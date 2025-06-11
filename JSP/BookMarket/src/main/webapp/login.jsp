<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <div class="container py-4">
    <%@ include file="menu.jsp" %>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">로그인</h1>
        <p class="col-md-8 fs-4">Login</p>
      </div>
    </div>

    <div class="row align-items-md-stretch text-center">
      <div class="row justify-content-center align-items-center">
        <div class="h-100 p-5 col-md-6">
          <h3>Please sign in</h3>

          <%
            String error = request.getParameter("error");
            if (error != null) {
          %>
            <div class="alert alert-danger" role="alert">
              아이디와 비밀번호를 확인해 주세요
            </div>
          <%
            }
          %>

          <form class="form-signin" action="j_security_check" method="post">
            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="j_username" name="j_username" required autofocus>
              <label for="j_username">ID</label>
            </div>
            <div class="form-floating mb-3">
              <input type="password" class="form-control" id="j_password" name="j_password" required>
              <label for="j_password">Password</label>
            </div>
            <button class="btn btn-lg btn-success" type="submit">로그인</button>
          </form>
        </div>
      </div>
    </div>

    <%@ include file="footer.jsp" %>
  </div>
</body>
</html>
