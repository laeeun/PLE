<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>AS-Mate 사용자 페이지</title>
    
    <!-- ✅ Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
    <style>
        body {
            background-color: #f8f9fa;
        }
        .nav-link {
            color: white !important;
        }
        .card {
            margin-bottom: 30px;
        }
        footer {
            text-align: center;
            padding: 20px;
            background-color: #f1f1f1;
            margin-top: 50px;
            font-size: 14px;
        }
    </style>
</head>
<body>

<!-- ✅ Header / Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">AS-Mate 사용자</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">홈</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/request/form">요청 등록</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/request/list">내 요청 목록</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- ✅ Main Container -->
<div class="container mt-5">

    <!-- 요청 등록 -->
    <div class="card">
        <div class="card-header bg-primary text-white">
            요청 등록
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/request/form" method="post" enctype="multipart/form-data">
                <div class="mb-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" name="title" id="title" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="content" class="form-label">내용</label>
                    <textarea name="content" id="content" class="form-control" rows="4" required></textarea>
                </div>

                <div class="mb-3">
                    <label for="photo" class="form-label">사진 첨부</label>
                    <input type="file" name="file" id="photo" class="form-control">
                </div>

                <div class="mb-3">
                    <label for="location" class="form-label">위치</label>
                    <input type="text" name="location" id="location" class="form-control">
                </div>

                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
                    <input type="email" name="email" id="email" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary">등록하기</button>
            </form>
        </div>
    </div>

    <!-- 요청 목록 -->
    <div class="card">
        <div class="card-header bg-success text-white">
            내 요청 목록
        </div>
        <div class="card-body">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>제목</th>
                        <th>날짜</th>
                        <th>상태</th>
                        <th>상세</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="request" items="${requestList}">
                        <tr>
                            <td>${request.title}</td>
                            <td>${request.createdAt}</td>
                            <td>${request.status}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/request/detail/${request.id}" class="btn btn-sm btn-outline-primary">보기</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- ✅ Footer -->
<footer>
    <p>© 2025 AS-Mate. All rights reserved.</p>
</footer>

</body>
</html>
