<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>📬 알림 목록</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
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
		    font-family: 'Pretendard', sans-serif;
		    background: var(--surface);
		    padding: 40px 20px;
		    color: var(--text-main);
		}
		
		.page-title {
		    text-align: center;
		    font-size: 24px;
		    font-weight: 700;
		    color: var(--primary);
		    margin-bottom: 40px;
		    position: relative;
		}
		.page-title::after {
		    content: '';
		    display: block;
		    width: 60px;
		    height: 3px;
		    background-color: var(--accent);
		    margin: 12px auto 0;
		    border-radius: 2px;
		}
		
		.noti-table {
		    width: 100%;
		    border-collapse: collapse;
		    background: var(--surface-alt);
		    box-shadow: 0 4px 12px rgba(0,0,0,0.05);
		    border-radius: 12px;
		    overflow: hidden;
		}
		
		.noti-table th, .noti-table td {
		    padding: 14px 12px;
		    border-bottom: 1px solid var(--border);
		    text-align: left;
		    font-size: 14px;
		    color: var(--text-main);
		}
		.noti-table th {
		    background-color: #f8f8f8;
		    font-weight: 600;
		}
		
		.noti-table tr.unread {
		    background-color: var(--accent-100);
		}
		
		.type {
		    font-weight: bold;
		    color: var(--accent);
		}
		
		.pagination {
		    text-align: center;
		    margin-top: 30px;
		}
		.pagination a {
		    margin: 0 6px;
		    text-decoration: none;
		    color: var(--text-sub);
		}
		.pagination a[style*="bold"] {
		    color: var(--accent);
		    font-weight: 700;
		    text-decoration: underline;
		}
		
		.delete-btn {
		    margin-top: 20px;
		    padding: 10px 20px;
		    background-color: var(--accent);
		    color: white;
		    border: none;
		    border-radius: 8px;
		    cursor: pointer;
		    font-size: 14px;
		    transition: background-color 0.2s ease;
		}
		.delete-btn:hover {
		    background-color: #e85c26;
		}
		
		form select {
		    padding: 6px 12px;
		    font-size: 14px;
		    border-radius: 6px;
		    border: 1px solid var(--border);
		    background-color: white;
		    color: var(--text-main);
		}
		.page-title i {
		    margin-right: 8px;
		    color: var(--accent);
		}
		a {
		  text-decoration: none;
		  color: inherit; /* 기본 텍스트 색상 유지 */
		}
		a:hover {
		    color: var(--accent); /* 강조 색상으로 변경 */
		    font-weight: 600;
		}
    </style>
</head>
<body>
    <h2 class="page-title">
	    <i class="fas fa-envelope-open-text"></i> 내 알림
	</h2>
	<div style="margin-bottom: 20px;">
	    <form method="get" action="<c:url value='/notification/popup' />">
	        <label for="typeSelect">
		        <i class="fas fa-filter"></i> 유형 필터:
		    </label>
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
