<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>📬 알림 목록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: #fff0f5;
            padding: 20px;
        }
        table.noti-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        table.noti-table th, table.noti-table td {
            padding: 12px;
            border-bottom: 1px solid #eee;
            text-align: left;
        }
        table.noti-table tr.unread {
            background-color: #fef4f8;
        }
        .type {
            font-weight: bold;
            color: #e91e63;
        }
        .pagination a {
            margin-right: 5px;
            text-decoration: none;
            color: #333;
        }
        .delete-btn {
            margin-top: 10px;
            padding: 8px 14px;
            border: none;
            background-color: #ff6b81;
            color: white;
            border-radius: 4px;
            cursor: pointer;
        }
        .delete-btn:hover {
            background-color: #e94163;
        }
    </style>
</head>
<body>
    <h2>📬 내 알림</h2>
	<div style="margin-bottom: 20px;">
	    <form method="get" action="<c:url value='/notification/popup' />">
	        <label for="typeSelect">🔍 유형 필터:</label>
	        <select name="type" id="typeSelect" onchange="this.form.submit()">
	            <option value="">전체 보기</option>
	            <option value="재능구매" ${type == '재능구매' ? 'selected' : ''}>재능구매</option>
	            <option value="재능등록" ${type == '재능등록' ? 'selected' : ''}>재능등록</option>
	            <option value="댓글" ${type == '댓글' ? 'selected' : ''}>댓글</option>
	            <option value="팔로우" ${type == '팔로우' ? 'selected' : ''}>팔로우</option>
	            <option value="리뷰" ${type == '리뷰' ? 'selected' : ''}>리뷰</option>
	        	<option value="숙제" ${type == '숙제' ? 'selected' : ''}>숙제</option>
	        </select>
	        <input type="hidden" name="size" value="${size}" />
	    </form>
	</div>
    <c:choose>
        <c:when test="${not empty notifications}">
            <form id="deleteForm">
                <table class="noti-table">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="checkAll" onclick="toggleAll(this)"></th>
                            <th>유형</th>
                            <th>내용</th>
                            <th>날짜</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="n" items="${notifications}">
                            <tr class="${!n.read ? 'unread' : ''}">
                                <td><input type="checkbox" name="ids" value="${n.notificationId}"></td>
                                <td class="type">${n.type}</td>
                                <td>
                                	<a href="#" onclick="goToContent(${n.notificationId}); return false;">${n.content}</a>
                                </td><!-- 해당 컨테츠로 이동할 수 있는 태그설정 -->
                                <td><small>${n.getFormattedCreatedAt()}</small></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" onclick="deleteSelected()" class="delete-btn">선택 삭제</button>
            </form>
        </c:when>
        <c:otherwise>
            <p>아직 알림이 없습니다.</p>
        </c:otherwise>
    </c:choose>
	<c:if test="${lastPage > 1}">
	    <div class="pagination" style="margin-top: 20px;">
	        <c:forEach var="p" begin="1" end="${lastPage}">
	            <c:url var="pageUrl" value="/notification/popup">
	                <c:param name="page" value="${p}" />
	                <c:param name="size" value="${size}" />
	                <c:if test="${not empty type}">
	                    <c:param name="type" value="${type}" />
	                </c:if>
	            </c:url>
	            <a href="${pageUrl}" style="${p == page ? 'font-weight:bold; text-decoration:underline;' : ''}">
	                ${p}
	            </a>
	        </c:forEach>
	    </div>
	</c:if>
    <script>  
        function toggleAll(source) {
            const checkboxes = document.querySelectorAll('input[name="ids"]');
            checkboxes.forEach(cb => cb.checked = source.checked);
        }

        function deleteSelected() {
            const checked = [...document.querySelectorAll('input[name="ids"]:checked')];
            if (checked.length === 0) {
                alert("삭제할 알림을 선택해주세요.");
                return;
            }

            if (!confirm("선택한 알림을 삭제하시겠습니까?")) return;

            const ids = checked.map(cb => cb.value).join(',');

            fetch('<c:url value="/api/notifications/delete-multiple" />', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'ids=' + encodeURIComponent(ids)
            })
            .then(response => {
                if (response.ok) {
                    // 성공 시 해당 행 제거
                    checked.forEach(cb => cb.closest('tr').remove());
                } else {
                    alert("삭제 실패");
                }
            });
        }
        
        function goToContent(notificationId) {
        	
        	fetch('<c:url value="/notification/go/" />' + notificationId)
            .then(res => res.text())
            .then(url => {
                if (!url || url === "/") {
                    alert("유효하지 않은 알림입니다. 이미 삭제되었거나 접근할 수 없습니다.");
                    return;
                }

                if (window.opener && !window.opener.closed) {
                    window.opener.location.href = url;
                }
                window.close();
            })
            .catch(err => {
                alert("알림 이동 중 오류가 발생했습니다.");
                console.error(err);
            });
        }
    </script>
</body>
</html>
