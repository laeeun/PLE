<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>목표 관리</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
    body {
        font-family: 'Pretendard', sans-serif;
        background-color: #f8fafc;
        margin: 20px;
        color: #1e293b;
    }

    h2, h3 {
        margin-top: 30px;
        color: #0f172a;
    }

    form label {
        display: block;
        margin: 8px 0;
    }

    input[type="text"], select {
        padding: 6px 12px;
        border: 1px solid #cbd5e1;
        border-radius: 6px;
        font-size: 14px;
        width: 300px;
        background-color: #fff;
    }

    button {
        padding: 8px 16px;
        margin-top: 10px;
        margin-right: 8px;
        border: none;
        border-radius: 8px;
        background-color: #3b82f6;
        color: white;
        font-weight: 500;
        cursor: pointer;
        transition: background-color 0.2s ease;
    }

    button:hover {
        background-color: #2563eb;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 12px;
        background-color: white;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 0 4px rgba(0,0,0,0.05);
    }

    table th, table td {
        border: 1px solid #e2e8f0;
        padding: 12px;
        text-align: center;
    }

    table th {
        background-color: #f1f5f9;
        color: #1e293b;
    }

    .goal-row span {
        font-weight: bold;
    }

    .goal-row span[style*="green"] {
        color: #16a34a !important;
    }

    .goal-row span[style*="gray"] {
        color: #64748b !important;
    }

    select {
        margin-right: 10px;
    }

    #historyResult {
        margin-top: 16px;
        background-color: white;
        padding: 16px;
        border-radius: 12px;
        box-shadow: 0 0 4px rgba(0,0,0,0.05);
    }

    #editModal {
	    display: none;
	    position: fixed;
	    top: 100%; 
	    left: 50%;
	    transform: translate(-50%, -50%);
	    background: #ffffff;
	    padding: 20px;
	    border: 1px solid #cbd5e1;
	    border-radius: 12px;
	    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
	    min-width: 400px;
	    z-index: 9999;
	}

    #editModal h3 {
        margin-bottom: 10px;
        color: #1e293b;
    }

    #editModal button {
        margin-top: 14px;
    }

    canvas {
        margin: 0 auto;
        display: block;
    }
    /* ✅ 전체 컨테이너 가운데 정렬 및 최대 너비 지정 */
	.container {
	    max-width: 900px;
	    margin: 0 auto;
	    background-color: white;
	    padding: 30px;
	    border-radius: 16px;
	    box-shadow: 0 0 8px rgba(0,0,0,0.05);
	}
	
	/* ✅ 왼쪽 정렬 텍스트 및 섹션 간 여백 */
	h2, h3 {
	    text-align: left;
	    margin-top: 40px;
	    margin-bottom: 20px;
	}
	
	/* ✅ 버튼 그룹 가운데 정렬 */
	form button,
	#editGoalForm button {
	    display: inline-block;
	}
	
	/* ✅ 도넛 차트 컨테이너 정렬 */
	#goalProgressSection {
	    display: flex;
	    justify-content: center;
	    gap: 40px;
	    margin-top: 30px;
	}
	
	/* ✅ 진행률 텍스트 */
	#goalProgressSection p {
	    text-align: center;
	    margin-top: 10px;
	    font-weight: 500;
	    color: #334155;
	}
	
	/* ✅ 히스토리 및 셀렉트 정렬 */
	#historyResult,
	#monthSelect,
	#weekSelect {
	    display: block;
	    margin: 10px auto;
	    text-align: center;
	}
	#goalForm {
	    display: flex;
	    flex-direction: column;
	    gap: 12px;
	    margin-top: 20px;
	    align-items: flex-start; /* 왼쪽 정렬 */
	}
	
	.goal-form-group {
	    display: flex;
	    align-items: center;
	    gap: 10px;
	}
	
	.goal-form-group label {
	    width: 80px; /* 라벨 너비 고정 */
	    font-weight: 500;
	}
	
	.goal-form-group input,
	.goal-form-group select {
	    flex: 1;
	    min-width: 200px;
	}
	.filter-section {
	    margin-top: 20px;
	    margin-bottom: 10px;
	    padding: 12px 16px;
	    background-color: #f1f5f9;
	    border-radius: 8px;
	    box-shadow: 0 0 4px rgba(0, 0, 0, 0.03);
	    max-width: 300px;
	}
	
	.filter-section h4 {
	    margin: 0 0 8px;
	    font-size: 15px;
	    color: #1e293b;
	}
</style>
    
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<c:url var="goalCreateUrl" value="/goals/create" />
<c:url var="goalUpdateUrl" value="/goals/update" />
<c:url var="goalDeleteUrl" value="/goals/delete" />
<c:url var="toggleGoalUrl" value="/goals/toggle" />
<c:url var="GoalprogressUrl" value="/goals/progress" />
<c:url var="GoalsHistoryUrl" value="/goals/history" />
<div class="container">    
    <h2> 나의 목표 추가</h2>

    <!-- ✅ 목표 생성 폼 -->
    <form id="goalForm">
	    <input type="hidden" name="goalId" id="goalId" />

	    <div class="goal-form-group">
	        <label for="title">제목:</label>
	        <input type="text" name="title" id="title" required />
	    </div>
	
	    <div class="goal-form-group">
	        <label for="description">설명:</label>
	        <input type="text" name="description" id="description" />
	    </div>
	
	    <div class="goal-form-group">
	        <label for="goalType">유형:</label>
	        <select name="goalType" id="goalType" required>
	            <option value="WEEKLY">주간 목표</option>
	            <option value="MONTHLY">월간 목표</option>
	        </select>
	    </div>
	
	    <div style="margin-top: 10px;">
	        <button type="submit" id="submitBtn">목표 생성</button>
	        <button type="button" onclick="resetForm()">취소</button>
	    </div>
	</form>

    <hr/>
	<h3>📆 지난 목표 조회</h3>

	<div class="filter-section">
	    <h4>월별 선택</h4>
	    <select id="monthSelect" onchange="loadHistoryGoals('MONTHLY')"></select>
	</div>
	
	<!-- 📆 주차 선택 영역 -->
	<div class="filter-section">
	    <h4>주차 선택</h4>
	    <select id="weekSelect" onchange="loadHistoryGoals('WEEKLY')"></select>
	</div>
	
	<div id="historyResult">
	    <!-- AJAX로 목표 리스트가 이 영역에 표시됨 -->
	</div>
   <h3>📅 이번 주 목표</h3>
	<c:choose>
	    <c:when test="${not empty weeklyGoals}">
	        <table border="1">
	            <tr>
	                <th>제목</th>
	                <th>기간</th>
	                <th>상태</th>
	                <th>관리</th>
	            </tr>
	            <c:forEach var="g" items="${weeklyGoals}">
	                <tr class="goal-row" data-end-date="${g.endDate}">
	                    <td>${g.title}</td>
	                    <td>${g.startDate} ~ ${g.endDate}</td>
	                    <td>
	                        <c:choose>
	                            <c:when test="${g.completed}">
	                                <span style="color: green;">✔ 완료됨</span>
	                            </c:when>
	                            <c:otherwise>
	                                <span style="color: gray;">미완료</span>
	                            </c:otherwise>
	                        </c:choose>
	                    </td>
	                    <td>
	                        <button onclick="toggleComplete(${g.goalId}, ${not g.completed})">
	                            <c:choose>
	                                <c:when test="${g.completed}">↩ 되돌리기</c:when>
	                                <c:otherwise>✔ 완료하기</c:otherwise>
	                            </c:choose>
	                        </button>
	                        <button onclick="editGoal('${g.goalId}', '${fn:escapeXml(g.title)}', '${fn:escapeXml(g.description)}', '${g.goalType}')">
							    ✏ 수정
							</button>
							 <!-- ❌ 삭제 버튼 -->
						    <button onclick="deleteGoal(${g.goalId})">
						        ❌ 삭제
						    </button>
	                        
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	    </c:when>
	    <c:otherwise>이번 주 목표가 없습니다.</c:otherwise>
	</c:choose>
	
	<br/><hr><br/>
	
	<h3>🗓️ 이번 달 목표</h3>
	<c:choose>
	    <c:when test="${not empty monthlyGoals}">
	        <table border="1">
	            <tr>
	                <th>제목</th>
	                <th>기간</th>
	                <th>상태</th>
	                <th>관리</th>
	            </tr>
	            <c:forEach var="g" items="${monthlyGoals}">
	                <tr class="goal-row" data-end-date="${g.endDate}">
	                    <td>${g.title}</td>
	                    <td>${g.startDate} ~ ${g.endDate}</td>
	                    <td>
	                        <c:choose>
	                            <c:when test="${g.completed}">
	                                <span style="color: green;">✔ 완료됨</span>
	                            </c:when>
	                            <c:otherwise>
	                                <span style="color: gray;">미완료</span>
	                            </c:otherwise>
	                        </c:choose>
	                    </td>
	                    <td>
	                        <button onclick="toggleComplete(${g.goalId}, ${not g.completed})">
	                            <c:choose>
	                                <c:when test="${g.completed}">↩ 되돌리기</c:when>
	                                <c:otherwise>✔ 완료하기</c:otherwise>
	                            </c:choose>
	                        </button>
	                        <button onclick="editGoal('${g.goalId}', '${fn:escapeXml(g.title)}', '${fn:escapeXml(g.description)}', '${g.goalType}')">
							    ✏ 수정
							</button>
							 <!-- ❌ 삭제 버튼 -->
						    <button onclick="deleteGoal(${g.goalId})">
						        ❌ 삭제
						    </button>
	                    </td>
	                </tr>
	            </c:forEach>
	        </table>
	    </c:when>
	    <c:otherwise>이번 달 목표가 없습니다.</c:otherwise>
	</c:choose>

    <hr/>

    <!-- ✅ 진행률 조회 -->
    <h3>📅 목표 진행률</h3>
	<div id="goalProgressSection">
	    <div>
	        <canvas id="weeklyProgressChart" width="200" height="200"></canvas>
	        <p>주간 목표</p>
	    </div>
	    <div>
	        <canvas id="monthlyProgressChart" width="200" height="200"></canvas>
	        <p>월간 목표</p>
	    </div>
	</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />
</div>
    <script>
    const goalCreateUrl = '${goalCreateUrl}';
    const goalUpdateUrl = '${goalUpdateUrl}';
    const deleteUrl = '${goalDeleteUrl}';
    
    
    const toggleGoalUrl = '${toggleGoalUrl}';
    const GoalprogressUrl = '${GoalprogressUrl}';
    const GoalsHistoryUrl = '${GoalsHistoryUrl}';
    
    $(window).on('click', function(e) {
        if ($(e.target).is('#editModal')) {
            $('#editModal').hide();
        }
    });
        // ✅ 목표 생성 AJAX
        $('#goalForm').on('submit', function(e) {
		    e.preventDefault();
		    const data = Object.fromEntries(new FormData(this));
		 
		    //delete data.targetCount;
		    //delete data.completedCount;
		
		    $.ajax({
		        url: goalCreateUrl,
		        type: 'POST',
		        contentType: 'application/json',
		        data: JSON.stringify(data),
		        success: res => {
		            alert(res.message);
		            if (res.success) location.reload();
		        }
		    });
		});
        // ✅ 폼 리셋
        function resetForm() {
            $('#goalForm')[0].reset();
            $('#goalId').val('');
            $('#submitBtn').text('목표 생성');
        }
        
        function editGoal(goalId, title, description, goalType) {
            $('#editGoalId').val(goalId);
            $('#editTitle').val(title);
            $('#editDescription').val(description);
            $('#editGoalType').val(goalType);
            $('#editModal').show();
        }
        
        function closeEditModal() {
            $('#editModal').hide();
        }
        
        $(document).ready(function () {
            $('#editGoalForm').on('submit', function(e) {
                console.log("[DEBUG] 수정 버튼 눌림"); // ← 이거 이제 나올 거임
                e.preventDefault();

                const data = {
                    goalId: $('#editGoalId').val(),
                    title: $('#editTitle').val(),
                    description: $('#editDescription').val(),
                    goalType: $('#editGoalType').val()
                };

                $.ajax({
                    url: goalUpdateUrl,
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: data => {
                        alert(data.message);
                        if (data.success) {
                            closeEditModal();
                            location.reload();
                        }
                    },
                    error: () => alert("수정에 실패했습니다.")
                });
            });

            // 기존에 있던 chart ajax도 이 안에 있어야 좋음
            $.ajax({
                url: GoalprogressUrl,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    renderGoalChart("weeklyProgressChart", data.weekly);
                    renderGoalChart("monthlyProgressChart", data.monthly);
                },
                error: function (xhr, status, error) {
                    console.error("진행률 불러오기 오류:", error);
                }
            });
        });
        // ✅ 목표 삭제
        function deleteGoal(goalId) {
            if (!confirm("정말 삭제하시겠습니까?")) return;

            $.ajax({
                url: deleteUrl + '/' + goalId,
                type: 'POST',
                success: res => {
                    alert(res.message);
                    if (res.success) location.reload();
                }
            });
        }
        $(document).ready(function () {
            $.ajax({
                url: GoalprogressUrl,
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    renderGoalChart("weeklyProgressChart", data.weekly);
                    renderGoalChart("monthlyProgressChart", data.monthly);
                },
                error: function (xhr, status, error) {
                    console.error("진행률 불러오기 오류:", error);
                }
            });
        });

        function renderGoalChart(canvasId, progressData) {
            const total = progressData.total_goals || 0;
            const achieved = progressData.achieved_goals || 0;
            const percentage = total > 0 ? Math.round((achieved / total) * 100) : 0;
            const notAchieved = total - achieved;

            const ctx = document.getElementById(canvasId).getContext("2d");

            new Chart(ctx, {
                type: "doughnut",
                data: {
                    labels: ["달성", "미달성"],
                    datasets: [{
                        data: [achieved, notAchieved],
                        backgroundColor: ["#60a5fa", "#cbd5e1"],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    cutout: "70%",
                    plugins: {
                        legend: { position: "bottom" },
                        tooltip: {
                            callbacks: {
                                label: function (context) {
                                    return `\${context.label}: \${context.raw}건`;
                                }
                            }
                        },
                        // 중앙 텍스트 표시 플러그인
                        doughnutLabel: {
                            labels: [
                                {
                                    text: `\${percentage}%`,
                                    font: {
                                        size: '20',
                                        weight: 'bold'
                                    },
                                    color: '#1e293b'
                                }
                            ]
                        }
                    }
                },
                plugins: [{
                    // Chart.js v3 기준 사용자 플러그인 정의
                    id: 'centerText',
                    beforeDraw(chart) {
                        const { width } = chart;
                        const { height } = chart;
                        const { ctx } = chart;
                        ctx.restore();
                        const fontSize = (height / 200).toFixed(2);
                        ctx.font = `\${fontSize}em sans-serif`;
                        ctx.textBaseline = "middle";

                        const text = `\${percentage}%`;
                        const textX = Math.round((width - ctx.measureText(text).width) / 2);
                        const textY = height / 2;

                        ctx.fillText(text, textX, textY);
                        ctx.save();
                    }
                }]
            });
        }
        
        function toggleComplete(goalId, newStatus) {
            $.ajax({
                url: toggleGoalUrl,
                type: 'POST',
                data: { goalId: goalId },
                success: function(response) {
                    alert(response); // 또는 Toast 메시지
                    location.reload(); // 페이지 새로고침하여 상태 반영
                },
                error: function(xhr) {
                    if (xhr.status === 401) {
                        alert('로그인이 필요합니다.');
                    } else {
                        alert('상태 변경에 실패했습니다.');
                    }
                }
            });
        }
        
        function populateMonthDropdown() {
            const monthSelect = document.getElementById("monthSelect");
            const now = new Date();

            for (let i = 0; i < 6; i++) {
                const date = new Date(now.getFullYear(), now.getMonth() - i, 1);
                const value = `\${date.getFullYear()}-\${(date.getMonth() + 1).toString().padStart(2, '0')}`;
                const label = `\${date.getFullYear()}년 \${date.getMonth() + 1}월`;
                const option = new Option(label, value);
                monthSelect.appendChild(option);
            }
        }

        function populateWeekDropdown() {
            const weekSelect = document.getElementById("weekSelect");
            const now = new Date();
            const currentMonday = new Date(now.setDate(now.getDate() - now.getDay() + 1)); // 이번 주 월요일

            for (let i = 0; i < 12; i++) {
                const monday = new Date(currentMonday);
                monday.setDate(monday.getDate() - i * 7);
                const sunday = new Date(monday);
                sunday.setDate(monday.getDate() + 6);

                const value = `\${monday.toISOString().split('T')[0]}_\${sunday.toISOString().split('T')[0]}`;
                const label = `\${monday.getMonth() + 1}/\${monday.getDate()} ~ \${sunday.getMonth() + 1}/\${sunday.getDate()}`;
                const option = new Option(label, value);
                weekSelect.appendChild(option);
            }
        }
		
        function loadHistoryGoals(type) {
            let param = '';
            if (type === 'MONTHLY') {
                param = document.getElementById('monthSelect').value; // YYYY-MM
            } else {
                param = document.getElementById('weekSelect').value;  // YYYY-MM-DD_YYYY-MM-DD
            }

            $.ajax({
                url: GoalsHistoryUrl,
                type: 'GET',
                data: { type, param },
                success: function (html) {
                    $('#historyResult').html(html);
                },
                error: function () {
                    alert("목표 조회 실패");
                }
            });
        }
        function hideExpiredGoals() {
            const today = new Date().toISOString().split('T')[0]; // YYYY-MM-DD 형식

            document.querySelectorAll(".goal-row").forEach(row => {
                const endDate = row.getAttribute("data-end-date");
                if (endDate < today) {
                    row.style.display = "none";
                }
            });
        }

        $(document).ready(function () {
            hideExpiredGoals();
        });
        
        populateMonthDropdown();
        populateWeekDropdown();
       
    </script>
    <div id="editModal" style="display:none; position:fixed; top:30%; left:50%; transform:translate(-50%, -50%);
     background:white; padding:20px; border:1px solid #ccc; border-radius:8px; z-index:1000;">
	    <h3>✏ 목표 수정</h3>
	    <form id="editGoalForm">
	        <input type="hidden" id="editGoalId" />
	        <label>제목: <input type="text" id="editTitle" required></label><br/>
	        <label>설명: <input type="text" id="editDescription"></label><br/>
	        <label>유형:
	            <select id="editGoalType" required>
	                <option value="WEEKLY">주간</option>
	                <option value="MONTHLY">월간</option>
	            </select>
	        </label><br/><br/>
	        <button type="submit">저장</button>
	        <button type="button" onclick="closeEditModal()">취소</button>
	    </form>
	</div>
</body>
</html>
