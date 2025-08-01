<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>신고 목록</title>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
</head>
<body class="container mt-5">
    <!-- ✅ 성공/오류 메시지 -->
	<c:if test="${not empty successMessage}">
	    <div class="alert alert-success">${successMessage}</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
	    <div class="alert alert-danger">${errorMessage}</div>
	</c:if>

    <h2>📄 신고 목록</h2>
	<form method="get" action="<c:url value='/report' />" class="mb-4" style="display: inline-block;">
	    <label for="statusFilter">상태:</label>
	    <select name="status" id="statusFilter" onchange="this.form.submit()" class="form-select d-inline w-auto">
	        
	        <option value="pending" ${status == 'pending' ? 'selected' : ''}>🕓 대기 중</option>
	        <option value="resolved" ${status == 'resolved' ? 'selected' : ''}>✅ 처리 완료</option>
	        <option value="rejected" ${status == 'rejected' ? 'selected' : ''}>❌ 처리 거절</option>
	    </select>
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
			                <c:when test="${r.status == 'pending'}">🕓 대기 중</c:when>
			                <c:when test="${r.status == 'resolved'}">✅ 처리 완료</c:when>
			                <c:when test="${r.status == 'rejected'}">❌ 처리 거절</c:when>
			            </c:choose>
			        </td>
			        <td>${r.getFormattedCreatedAt()}</td>
			        <td>
			            <c:out value="${reportCountMap[r.target_id]}" default="0" />
			        </td>
			        <td>
			            <a class="btn btn-sm btn-outline-primary"
			               href="<c:url value='/report/view?id=${r.report_id}' />">보기</a>
			            <c:if test="${r.status == 'pending'}">
				            <button type="button"
							        class="btn btn-success btn-sm update-status"
							        data-id="${r.report_id}"
							        data-status="resolved">처리 완료</button>
							
							<button type="button"
							        class="btn btn-danger btn-sm update-status"
							        data-id="${r.report_id}"
							        data-status="rejected">처리 거절</button>
						</c:if>
			        </td>
			    </tr>
			</c:forEach>
        </tbody>
    </table>

    <!-- ✅ 페이징 -->
    <div class="mt-4">
        <nav>
            <ul class="pagination">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link"
                           href="<c:url value='/report?page=${i}&size=6&target_type=${targetType}&status=${status}' />">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
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

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />

</body>

</html>
