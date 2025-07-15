<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>재능 게시판</title>

    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(-45deg, #fce7f3, #f3e8ff, #e0e7ff, #fbcfe8);
            background-size: 400% 400%;
            animation: gradientBG 15s ease infinite;
            min-height: 100vh;
        }

        @keyframes gradientBG {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }


        h5, h4 {
            color: #6b21a8;
            font-weight: bold;
            animation: fadeSlide 1s ease forwards;
        }

        @keyframes fadeSlide {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .card {
            border: none;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(10px);
            box-shadow: 0 4px 18px rgba(0, 0, 0, 0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            animation: fadeInUp 0.6s ease both;
        }

        @keyframes fadeInUp {
            0% { opacity: 0; transform: translateY(20px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 28px rgba(0, 0, 0, 0.15);
        }

        .btn-primary {
            background: linear-gradient(to right, #a855f7, #ec4899);
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 10px;
            letter-spacing: 0.5px;
            position: relative;
            overflow: hidden;
            box-shadow: 0 0 12px rgba(168, 85, 247, 0.4);
        }

        .btn-primary:focus {
            outline: none;
            box-shadow: 0 0 0 0.2rem rgba(168, 85, 247, 0.3);
        }

        .btn-primary::after {
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

        .btn-outline-primary {
            color: #9333ea;
            border-color: #9333ea;
            border-radius: 10px;
            font-weight: 600;
        }

        .btn-outline-primary:hover {
            background-color: #9333ea;
            color: #fff;
        }

      .list-group-item,
      .list-group-item-action {
          background-color: rgba(255, 255, 255, 0.5);
          border: none !important;  /* ✅ Bootstrap 테두리 완전 제거 */
          font-weight: 500;
          border-radius: 12px;
          margin-bottom: 8px;
          color: #4c1d95;
          transition: transform 0.25s ease, box-shadow 0.25s ease;
          backdrop-filter: blur(6px);
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
          position: relative;
          overflow: hidden;
      }
      
      .list-group-item:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
          background-color: #f3e8ff;
      }
      
      .list-group-item.active {
          background: linear-gradient(to right, #d946ef, #a855f7);
          color: white;
          font-weight: bold;
          box-shadow: 0 6px 14px rgba(168, 85, 247, 0.4);
      }
      
      /* ✅ 샤인 효과 */
      .list-group-item::after {
          content: "";
          position: absolute;
          top: 0;
          left: -100%;
          width: 60%;
          height: 100%;
          background: linear-gradient(to right, rgba(255,255,255,0.4), rgba(255,255,255,0));
          transform: skewX(-25deg);
          transition: all 0.5s ease;
          pointer-events: none;
      }
      
      .list-group-item:hover::after {
          left: 130%;
      }


        .card-title a {
            color: #7e22ce;
            text-decoration: none;
        }

        .card-title a:hover {
            color: #6b21a8;
            text-decoration: underline;
        }

        .pagination .page-item.active .page-link {
            background-color: #a855f7;
            border-color: #a855f7;
            color: white;
        }

        .page-link {
            color: #7e22ce;
            border: 1px solid #f3d1fb;
        }

        .page-link:hover {
            background-color: #f3e8ff;
            color: #6b21a8;
            border-color: #c084fc;
        }

        .fs-4.text-muted {
            font-size: 1.25rem;
            color: #888;
        }

      .input-group {
          display: flex;
          width: 100%;
          border-radius: 14px;
          overflow: hidden;
          box-shadow: 0 6px 20px rgba(245, 183, 255, 0.15);
          background: rgba(255, 255, 255, 0.35);
          backdrop-filter: blur(10px);
      }
      
      .input-group input {
          flex: 1;
          border: none;
          padding: 12px 16px;
          font-size: 15px;
          color: #6b21a8;
          background: transparent;
          outline: none;
      }
      
      .input-group input::placeholder {
          color: #b48fdb;
          font-weight: 400;
      }
      
      .search-btn {
          border: none;
          padding: 12px 20px;
          font-size: 15px;
          font-weight: 600;
          color: #6b21a8;
          background: linear-gradient(to right, #d8b4fe, #fbcfe8);
          transition: all 0.3s ease;
          cursor: pointer;
      }
      
      .search-btn:hover {
          background: linear-gradient(to right, #c084fc, #f9a8d4);
      }

        
        .pastel-register-btn {
          background: linear-gradient(135deg, #f3e8ff, #fbcfe8);
          color: #6b21a8;
          font-weight: 600;
          border: 1.5px solid rgba(255, 255, 255, 0.5);
          border-radius: 12px;
          padding: 14px 16px; 
          width: 100%;
          font-size: 16px;  
          text-align: center;
          display: block;
          backdrop-filter: blur(10px);
          box-shadow: 0 4px 12px rgba(174, 182, 255, 0.3);
          position: relative;
          overflow: hidden;
          transition: all 0.35s ease;
          margin-top: 10px;  
      }
      
      .pastel-register-btn:hover {
          transform: translateY(-3px);
          box-shadow: 0 6px 20px rgba(245, 183, 255, 0.5);
      }
      
      .pastel-register-btn::after {
          content: "";
          position: absolute;
          top: 0;
          left: -70%;
          width: 50%;
          height: 100%;
          background: linear-gradient(120deg, rgba(255,255,255,0.3), rgba(255,255,255,0));
          transform: skewX(-20deg);
          animation: pastelShine 3s infinite;
      }
      
      @keyframes pastelShine {
          0% { left: -70%; }
          100% { left: 130%; }
      }
      
      .search-btn {
          background: linear-gradient(to right, #d8b4fe, #fbcfe8); /* 연보라+핑크 */
          color: #6b21a8;
          font-weight: 600;
          border: none !important;
          border-radius: 12px;
          padding: 10px 20px;
          font-size: 15px;
          backdrop-filter: blur(6px);
          box-shadow: 0 4px 10px rgba(245, 183, 255, 0.2);
          transition: all 0.3s ease;
          position: relative;
          overflow: hidden;
      }
      
      .search-btn:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 16px rgba(245, 183, 255, 0.4);
      }
      
      /* ✨ 샤인 효과 */
      .search-btn::after {
          content: "";
          position: absolute;
          top: 0;
          left: -70%;
          width: 60%;
          height: 100%;
          background: linear-gradient(to right, rgba(255,255,255,0.4), rgba(255,255,255,0));
          transform: skewX(-25deg);
          transition: all 0.4s ease;
          animation: searchShine 2.8s infinite;
          pointer-events: none;
      }
      
      @keyframes searchShine {
          0% { left: -70%; }
          100% { left: 130%; }
      }
      
      .expert-badge {
	    position: absolute;
	    top: 12px;
	    right: 12px;
	    background: linear-gradient(to right, #d8b4fe, #fbcfe8);
	    color: #6b21a8;
	    font-size: 0.75rem;
	    font-weight: 700;
	    padding: 6px 10px;
	    border-radius: 12px;
	    box-shadow: 0 2px 8px rgba(245, 183, 255, 0.25);
	    backdrop-filter: blur(4px);
	    border: 1px solid rgba(255, 255, 255, 0.5);
	    z-index: 10;
		}
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />


<div class="container">
    <div class="row align-items-start">
        <!-- 좌측 카테고리 -->
        <div class="col-md-3">
            <h5 class="mb-3">카테고리</h5>
            <div class="list-group mb-3">
                <a href="<c:url value='/talent/전체' />" class="list-group-item list-group-item-action ${category == '전체' ? 'active' : ''}">전체</a>
                <a href="<c:url value='/talent/디자인' />" class="list-group-item list-group-item-action ${category == '디자인' ? 'active' : ''}">디자인</a>
                <a href="<c:url value='/talent/프로그래밍' />" class="list-group-item list-group-item-action ${category == '프로그래밍' ? 'active' : ''}">프로그래밍</a>
                <a href="<c:url value='/talent/번역' />" class="list-group-item list-group-item-action ${category == '번역' ? 'active' : ''}">번역</a>
                <a href="<c:url value='/talent/음악' />" class="list-group-item list-group-item-action ${category == '음악' ? 'active' : ''}">음악</a>
                <a href="<c:url value='/talent/영상편집' />" class="list-group-item list-group-item-action ${category == '영상편집' ? 'active' : ''}">영상편집</a>
                <a href="<c:url value='/talent/글쓰기' />" class="list-group-item list-group-item-action ${category == '글쓰기' ? 'active' : ''}">글쓰기</a>
                <a href="<c:url value='/talent/과외' />" class="list-group-item list-group-item-action ${category == '과외' ? 'active' : ''}">과외</a>
                <a href="<c:url value='/talent/생활서비스' />" class="list-group-item list-group-item-action ${category == '생활서비스' ? 'active' : ''}">생활서비스</a>
                <a href="<c:url value='/talent/기획창작' />" class="list-group-item list-group-item-action ${category == '기획창작' ? 'active' : ''}">기획/창작</a>
            </div>
            <c:if test="${not empty sessionScope.loggedInUser}">
                <a href="<c:url value='/addtalent' />" class="pastel-register-btn">등록</a>
            </c:if>
        </div>

        <!-- 우측 콘텐츠 -->
        <div class="col-md-9">
            <form class="mb-4" action="<c:url value='/talent/search' />" method="get">
                <div class="input-group">
                    <input type="text" name="keyword" class="form-control" placeholder="검색어를 입력하세요" value="${keyword}" required />
               <button class="search-btn" type="submit">검색</button>
                </div>
            </form>

            <div class="row">
                <c:choose>
                    <c:when test="${not empty talentList}">
                        <c:forEach var="dto" items="${talentList}">
                            <div class="col-md-6 mb-4">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">
                                            <a href="<c:url value='/talent/view?id=${dto.talent_id}' />">${dto.title}</a>
                                        </h5>
                                        <c:if test="${dto.expert}">
                                            <div class="expert-badge">전문가</div>
                                        </c:if>
                                        <p>카테고리: ${dto.category}</p>
                                        <p>작성자: ${dto.username}</p>
                                        <p>판매 시간: ${dto.timeSlotDisplay}</p>
                                        <p>등록일: ${dto.formattedCreatedAt}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12 d-flex justify-content-center align-items-center" style="height: 200px;">
                            <p class="fs-4 text-muted">표시할 재능이 없습니다.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${totalPages > 1}">
                <div class="d-flex justify-content-center mt-4">
                    <nav>
                        <ul class="pagination">
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&keyword=${keyword}&category=${category}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>