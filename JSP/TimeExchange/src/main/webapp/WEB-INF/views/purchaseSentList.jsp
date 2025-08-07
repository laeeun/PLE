<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>내 구매 요청 목록</title>

  <!-- Pretendard 폰트 & FontAwesome -->
  <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
  <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">

  <style>
    :root {
      --primary: #1F2C40;
      --accent: #FF6B35;
      --surface: #F9F9F9;
      --surface-alt: #FFFFFF;
      --border: #E8E8E8;
      --text-main: #1F2C40;
      --text-sub: #6A737D;
    }

    body {
      font-family: 'Pretendard', sans-serif;
      background: var(--surface);
      color: var(--text-main);
      margin: 0;
      padding: 0;
    }

    .container {
      max-width: 900px;
      margin: 60px auto;
      padding: 30px;
      background: var(--surface-alt);
      border: 1px solid var(--border);
      border-radius: 16px;
      box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
    }

    h2 {
      font-size: 24px;
      font-weight: 800;
      margin-bottom: 20px;
      padding-bottom: 10px;
      border-bottom: 2px solid var(--accent);
      color: var(--primary);
      display: flex;
      align-items: center;
      gap: 10px;
    }

    h2 i {
      color: var(--accent);
    }

    table {
      width: 100%;
      border-collapse: collapse;
      background: var(--surface-alt);
      border: 1px solid var(--border);
      border-radius: 12px;
      overflow: hidden;
      box-shadow: 0 2px 6px rgba(0,0,0,0.04);
    }

    th, td {
      padding: 14px;
      border-bottom: 1px solid var(--border);
      text-align: left;
    }

    thead {
      background: #f1f5f9;
      color: var(--primary);
    }

    tbody tr:hover {
      background: #f9fafb;
    }

    .alert {
      display: flex;
      align-items: center;
      gap: 12px;
      background: linear-gradient(180deg, #eef3ff, #ffffff);
      padding: 16px;
      border: 1px solid var(--border);
      border-radius: 12px;
      box-shadow: 0 8px 20px rgba(0,0,0,0.06);
      color: var(--text-sub);
      font-size: 15px;
      margin-top: 20px;
    }

    .alert i {
      color: var(--accent);
      font-size: 18px;
    }

    @media (max-width: 768px) {
      .container { padding: 20px; }
      table { font-size: 14px; }
      h2 { font-size: 20px; }
    }
    .status-icon {
	  margin-right: 6px;
	  font-size: 14px;
	  vertical-align: middle;
	}
	
	.status-approved {
	  color: #10b981; /* 녹색 */
	}
	
	.status-pending {
	  color: #f59e0b; /* 주황 */
	}
	
	.status-rejected {
	  color: #ef4444; /* 빨강 */
	}
	tbody tr {
	  transition: background 0.2s ease;
	}
	tbody tr:hover {
	  background: rgba(255, 107, 53, 0.08);
	  transform: scale(1.005);
	}
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container">
  <h2><i class="fas fa-list-check"></i> 내가 구매 요청한 재능 목록</h2>

  <c:choose>
    <c:when test="${not empty sentRequests}">
      <table>
        <thead>
          <tr>
            
            <th>판매자</th>
            <th>요청 상태</th>
            <th>요청 시간</th>
            <th>요청 처리완료 시간</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="req" items="${sentRequests}">
            <tr>
              
              <td>${req.seller_id}</td>
              <td>
				  <c:choose>
				    <c:when test="${req.status == 'APPROVED'}">
				      <i class="fa-solid fa-circle-check status-icon status-approved"></i> 승인됨
				    </c:when>
				    <c:when test="${req.status == 'PENDING'}">
				      <i class="fa-solid fa-clock status-icon status-pending"></i> 대기중
				    </c:when>
				    <c:when test="${req.status == 'REJECTED'}">
				      <i class="fa-solid fa-circle-xmark status-icon status-rejected"></i> 거절됨
				    </c:when>
				    <c:otherwise>
				      <i class="fa-regular fa-circle-question"></i> 알 수 없음
				    </c:otherwise>
				  </c:choose>
				</td>
               <td>${req.formattedRequestedAt}</td>

			  <!-- (선택) 포맷된 승인 시간 -->
			  <td>
			    <c:if test="${not empty req.approved_at}">
			      ${req.formattedApprovedAt}
			    </c:if>
			  </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:when>
    <c:otherwise>
      <div class="alert">
        <i class="fas fa-inbox"></i>
        <span>📭 아직 구매 요청한 재능이 없습니다.</span>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>
