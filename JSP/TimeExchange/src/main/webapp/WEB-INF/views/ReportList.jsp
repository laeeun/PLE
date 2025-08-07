<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고 목록</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
		  background: linear-gradient(to bottom right, #f9f9f9, #ffffff);
		  font-family: 'Pretendard', sans-serif;
		  color: var(--text-main);
		}
		
		.container {
		  margin-top: 50px;
		  max-width: 1000px;
		}
		
		h2 {
		  font-size: 26px;
		  font-weight: 800;
		  margin-bottom: 25px;
		  color: var(--primary);
		  display: flex;
		  align-items: center;
		  gap: 10px;
		}
		
		
		h2 i {
		  margin-right: 8px;
		  font-size: 1.2em;
		}
		/* ✅ 테이블 */
		table {
		  background: var(--surface-alt);
		  border-radius: 12px;
		  box-shadow: 0 8px 24px rgba(0,0,0,0.05);
		  overflow: hidden;
		  border-collapse: separate;
		  border-spacing: 0;
		}
		
		thead th {
		  background: #f1f1f9;
		  color: var(--text-main);
		  font-weight: bold;
		  padding: 12px;
		  border-bottom: 1px solid var(--border);
		}
		
		tbody td {
		  padding: 12px;
		  border-bottom: 1px solid var(--border);
		  color: var(--text-sub);
		  vertical-align: middle;
		}
		
		tbody tr:hover {
		  background: rgba(255,107,53,0.08);
		}
		
		.btn-outline-primary {
		  border-color: var(--accent);
		  color: var(--accent);
		}
		.btn-outline-primary:hover {
		  background-color: var(--accent);
		  color: white;
		}
		
		/* ✅ 상태 텍스트 색상 */
		.report-status {
		  font-weight: bold;
		}
		.report-status:has(:contains('대기')) {
		  color: #f59e0b;
		}
		.report-status:has(:contains('완료')) {
		  color: #10b981;
		}
		.report-status:has(:contains('거절')) {
		  color: #ef4444;
		}
		
		/* ✅ 페이징 스타일 */
		a {
		  text-decoration: none;
		  padding: 6px 12px;
		  margin: 0 2px;
		  border-radius: 8px;
		  background-color: #f1f5f9;
		  color: var(--primary);
		  transition: background 0.2s ease;
		}
		a.active {
		  background-color: var(--accent);
		  color: #fff;
		  font-weight: bold;
		}
		a:hover {
		  background-color: #ffe3d2;
		}
		
		/* ✅ 검색 영역 */
		form {
		  display: flex;
		  gap: 12px;
		  align-items: center;
		  margin-bottom: 20px;
		}
		select, button {
		  padding: 6px 10px;
		  border-radius: 6px;
		  border: 1px solid var(--border);
		  font-size: 14px;
		}
		button[type="submit"] {
		  background-color: var(--accent);
		  color: white;
		  border: none;
		  transition: 0.2s;
		}
		button[type="submit"]:hover {
		  background-color: #e85c24;
		}
		
		/* ✅ 버튼 스타일 통일 */
		.btn-sm {
		  font-size: 13px;
		  padding: 4px 8px;
		}
	
	</style>
</head>


<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
	<div class="container">
    <!-- ✅ 성공/오류 메시지 -->
	<c:if test="${not empty successMessage}">
	    <div class="alert alert-success">${successMessage}</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
	    <div class="alert alert-danger">${errorMessage}</div>
	</c:if>

    <h2><i class="fa-shield-halved"></i> 신고 목록</h2>
	<form method="get" action="${pageContext.request.contextPath}/report">
	  <input type="hidden" name="size" value="${size}" />
	  <select name="targetType">
	    <option value="">전체</option>
	    <option value="talent"  ${targetType == 'talent'  ? 'selected' : ''}>재능</option>
	    <option value="comment" ${targetType == 'comment' ? 'selected' : ''}>댓글</option>
	  </select>
	
	  <select name="status">
	    <option value="">전체</option>
	    <option value="pending"  ${status == 'pending'  ? 'selected' : ''}>대기</option>
	    <option value="approved" ${status == 'approved' ? 'selected' : ''}>승인</option>
	    <option value="rejected" ${status == 'rejected' ? 'selected' : ''}>거절</option>
	  </select>
	  <button type="submit">검색</button>
	</form>
    <table class="table table-bordered mt-4">
        <thead class="table-light">
            <tr>
                
                <th>신고자</th>
                <th>대상자</th>
                <th>대상 타입</th> 
                <th>상태</th>
                <th>신고일</th>
                <th>자세히</th>
                <th>신고 처리</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="r" items="${reportlist}">
			    <tr id="report-${r.report_id}">			        
			        <td>${r.reporter_id}</td>
			        <td>${r.target_id}</td>
			        <td>${r.target_type}</td>
			        
			        <td class="report-status">
					    <c:choose>
					        <c:when test="${r.status == 'pending'}">
					            <i class="fas fa-hourglass-half text-warning"></i> 대기 중
					        </c:when>
					        <c:when test="${r.status == 'resolved'}">
					            <i class="fas fa-check-circle text-success"></i> 처리 완료
					        </c:when>
					        <c:when test="${r.status == 'rejected'}">
					            <i class="fas fa-times-circle text-danger"></i> 처리 거절
					        </c:when>
					    </c:choose>
					</td>
			        <td>${r.getFormattedCreatedAt()}</td>
			        <td>
			            <a class="btn btn-sm btn-outline-primary"
						   href="<c:url value='/report/view?id=${r.report_id}' />">
						   <i class="fas fa-eye"></i> 보기
						</a>
			        </td>
			        <td>			            
			            <c:if test="${r.status == 'pending'}">
						    <button type="button"
						            class="btn btn-success btn-sm update-status"
						            data-id="${r.report_id}"
						            data-status="resolved">
						        <i class="fas fa-check-circle"></i> 처리 완료
						    </button>
						    
						    <button type="button"
						            class="btn btn-danger btn-sm update-status"
						            data-id="${r.report_id}"
						            data-status="rejected">
						        <i class="fas fa-times-circle"></i> 처리 거절
						    </button>
						</c:if>
			        </td>
			    </tr>
			</c:forEach>
        </tbody>
    </table>

    <!-- ✅ 페이징 -->
    <c:forEach begin="1" end="${totalPages}" var="p">
	  <c:url var="pageUrl" value="/report">
	    <c:param name="page" value="${p}"/>
	    <c:param name="size" value="${size}"/>
	    <c:if test="${not empty targetType}">
	      <c:param name="targetType" value="${targetType}"/>
	    </c:if>
	    <c:if test="${not empty status}">
	      <c:param name="status" value="${status}"/>
	    </c:if>
	  </c:url>
	  <a href="${pageUrl}" class="${p == currentPage ? 'active' : ''}">${p}</a>
	</c:forEach>
</div>	
 <jsp:include page="/WEB-INF/views/footer.jsp" />
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />	
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function() {
  $(".update-status").click(function() {
    const reportId = $(this).data("id");
    const status = $(this).data("status");
    const row = $("#report-" + reportId);
    const statusCell = row.find(".report-status");

    $.ajax({
      url: "<c:url value='/report/updatestatus/ajax' />",
      method: "POST",
      data: {
        reportId: reportId,
        status: status
      },
      success: function(data) {
        if (data.success) {
          statusCell.html(
            status === "resolved" ? "✅ 처리 완료" :
            status === "rejected" ? "❌ 처리 거절" : "🕓 대기 중"
          );

          // 버튼 숨기기
          row.find(".update-status").hide();

          // 알림
          alert(data.message);
        } else {
          alert("🚨 실패: " + data.message);
        }
      },
      error: function() {
        alert("서버 오류가 발생했습니다.");
      }
    });
  });
});
</script>
</body>

</html>
