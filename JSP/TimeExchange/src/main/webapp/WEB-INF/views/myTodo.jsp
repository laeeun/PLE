<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>My TODO List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@600;700;900&display=swap" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet" />
    <style>
        /* 기존 스타일 유지 + 필요한 부분만 추가/수정 */
        /* ✅ 탭 버튼 */
		.tab-btn {
		  padding: 10px 20px;
		  font-size: 14px;
		  border: 1px solid #cbd5e1;
		  border-radius: 9999px;
		  background-color: #f1f5f9;
		  cursor: pointer;
		  transition: all 0.2s ease;
		  font-weight: 500;
		}
		.tab-btn:hover {
		  background-color: #e2e8f0;
		}
		.tab-btn.active {
		  background-color: #3b82f6;
		  color: white;
		  border-color: #3b82f6;
		}
		
		/* ✅ 라디오 버튼 그룹 */
		input[type="radio"] {
		  accent-color: #3b82f6;
		  margin-right: 6px;
		}
		label {
		  margin-right: 14px;
		  font-size: 14px;
		  color: #334155;
		  cursor: pointer;
		}
		
		/* ✅ 입력 줄 */
		.inline-input {
		  display: flex;
		  flex-wrap: wrap;
		  gap: 10px;
		  align-items: center;
		  margin: 20px 0;
		}
		.inline-input input[type="text"],
		.inline-input input[type="date"],
		.inline-input select {
		  padding: 8px 12px;
		  border: 1px solid #d1d5db;
		  border-radius: 8px;
		  font-size: 14px;
		  background-color: white;
		  min-width: 180px;
		}
		.inline-input button {
		  background-color: #3b82f6;
		  color: #2563eb;
		  border: none;
		  padding: 8px 16px;
		  border-radius: 8px;
		  cursor: pointer;
		  transition: background-color 0.2s ease;
		}
		.inline-input button:hover {
		  background-color: #2563eb;
		  color: white;
		}
		
		/* ✅ 테이블 */
		table {
		  width: 100%;
		  border-collapse: collapse;
		  background-color: white;
		  border-radius: 8px;
		  overflow: hidden;
		}
		thead th {
		  background-color: #f1f5f9;
		  color: #334155;
		  font-weight: 600;
		  font-size: 13px;
		  padding: 10px;
		  text-align: left;
		  border-bottom: 1px solid #e2e8f0;
		}
		tbody td {
		  padding: 10px;
		  font-size: 13px;
		  border-bottom: 1px solid #f1f5f9;
		  color: #475569;
		}
		tbody tr:hover {
		  background-color: #f9fafb;
		}
		.todo-status.completed {
		  color: #16a34a;
		  font-weight: 600;
		}
		.todo-status.not-completed {
		  color: #dc2626;
		  font-weight: 600;
		}
		.title.completed {
		  text-decoration: line-through;
		  color: #94a3b8;
		}		
        /* 캘린더 */
        #todo-calendar { margin:16px 0; border:1px solid #eee; border-radius:10px; overflow:hidden; background:#fff; }
        #cal-header { display:flex; align-items:center; justify-content:space-between; padding:10px 12px; background:#f8fafc; border-bottom:1px solid #eee; }
        #cal-title { font-weight:600; }
        .cal-nav-btn { padding:6px 10px; border:1px solid #ddd; background:#fff; border-radius:6px; cursor:pointer; }
        .cal-grid { display:grid; grid-template-columns:repeat(7,1fr); gap:1px; background:#e5e7eb; }
        .cal-cell { background:#fff; min-height:86px; padding:6px; position:relative; }
        .cal-day { font-size:12px; color:#64748b; }
        .cal-rate { font-size:13px; font-weight:600; margin-top:4px; }
        .cal-bar-outer { height:6px; background:#e5e7eb; border-radius:4px; overflow:hidden; margin-top:6px; }
        .cal-bar-inner { height:6px; width:0%; background:#38bdf8; }
        .cal-future { opacity:.55; }
        .cal-muted { background:#f8fafc; }
		.cal-grid {
					  gap: 0; /* 또는 1px 이하로 최소화 */
					}
					.cal-cell {
					  padding: 0; /* 내부 여백 제거 */
					}
        /* 입력 줄 */
        .inline-input { display:flex; gap:8px; align-items:center; margin:12px 0; }
        .inline-input input[type="text"] {
            padding:8px 10px; border:1px solid #e5e7eb; border-radius:8px; outline:none;
        }
        .inline-input button {
            padding:8px 12px; border:1px solid #e5e7eb; background:#fff; border-radius:8px; cursor:pointer;
        }

        /* 테이블 */
        table { width:100%; border-collapse:collapse; background:#fff; border:1px solid #eee; }
        thead th { background:#f8fafc; border-bottom:1px solid #eee; padding:8px; text-align:left; }
        tbody td { border-bottom:1px solid #f1f5f9; padding:8px; vertical-align:top; }
        .title.completed { text-decoration: line-through; color:#94a3b8; }
        .todo-status.completed { color:#16a34a; font-weight:600; }
        .todo-status.not-completed { color:#ef4444; font-weight:600; }
	
		/* 달력에서 일정 div가 더 자연스럽게 보이도록 */
		.todo-tag {
		  display: block;
		  height: 16px;
		  background-color: #c7d2fe;
		  color: #1e293b;
		  font-size: 11px;
		  overflow: hidden;
		  white-space: nowrap;
		  text-overflow: ellipsis;
		  box-sizing: border-box;
		  margin: 2px 0;
		  padding: 0 6px;
		  width: 100%;
		  border-radius: 0; /* 기본: 사각형 */
		}
		.todo-tag.start {
		  border-top-left-radius: 12px;
		  border-bottom-left-radius: 12px;
		}
		.todo-tag.end {
		  border-top-right-radius: 12px;
		  border-bottom-right-radius: 12px;
		}
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

        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Pretendard', sans-serif;
            color: var(--text-main);
            background: var(--surface);
            padding: 0px 20px;
        }
        h3 {
            font-family: 'Montserrat', sans-serif;
            font-size: 22px;
            font-weight: 700;
            color: var(--primary);
            margin-top: 40px;
            margin-bottom: 10px;
        }
        .container {
            max-width: 1180px;
            margin: 0 auto;
            margin-top: 20px;
            background: var(--surface-alt);
            padding: 32px;
            border-radius: 16px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.05);
        }
        .tab-btn {
            padding: 10px 20px;
            font-size: 14px;
            border: 1px solid var(--border);
            border-radius: 9999px;
            background-color: var(--surface);
            cursor: pointer;
            transition: all 0.2s ease;
            font-weight: 500;
            color: var(--text-main);
        }
        .tab-btn:hover {
            background-color: var(--accent-100);
            color: var(--primary);
            border-color: var(--accent);
        }
        .tab-btn.active {
            background-color: var(--accent);
            color: #fff;
            border-color: var(--accent);
        }
        .inline-input input,
        .inline-input select {
            padding: 10px;
            font-size: 14px;
            border-radius: 10px;
            border: 1px solid var(--border);
            background: #fff;
        }
        .inline-input button {
            background-color: var(--accent);
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 10px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s ease;
        }
        .inline-input button:hover {
            background-color: #e85c26;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 16px;
            background-color: #fff;
            border: 1px solid var(--border);
            border-radius: 12px;
            overflow: hidden;
        }
        thead th {
            background-color: var(--surface);
            color: var(--text-main);
            padding: 12px;
            border-bottom: 1px solid var(--border);
            text-align: left;
            font-weight: 600;
            font-size: 14px;
        }
        tbody td {
            padding: 12px;
            font-size: 14px;
            color: var(--text-sub);
            border-bottom: 1px solid var(--surface);
        }
        .todo-status.completed { color: #22c55e; font-weight: bold; }
        .todo-status.not-completed { color: #ef4444; font-weight: bold; }
        .title.completed { text-decoration: line-through; color: #9ca3af; }
       
        .cal-cell .todo-tag {
            display: block;
            height: 16px;
            background-color: #c7d2fe;
            color: #1e293b;
            font-size: 11px;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            box-sizing: border-box;
            margin: 2px 0;
            padding: 0 6px;
            width: 100%;
            border-radius: 0;
        }
          .cal-cell .todo-tag.start {
            border-top-left-radius: 12px;
            border-bottom-left-radius: 12px;
        }
        .cal-cell .todo-tag.end {
            border-top-right-radius: 12px;
            border-bottom-right-radius: 12px;
        }
      /* ✅ 수정/삭제 버튼 */
  		.edit-btn, .delete-btn {
		    padding: 6px 12px;
		    font-size: 13px;
		    font-weight: 500;
		    border: 1px solid var(--border);
		    border-radius: 8px;
		    background: #fff;
		    cursor: pointer;
		    transition: all 0.2s ease;
		    margin-right: 4px;
		  }
		  .edit-btn:hover {
		    background: var(--accent-100);
		    border-color: var(--accent);
		    color: var(--primary);
		  }
		  .delete-btn {
		    border-color: #ef4444;
		    color: #ef4444;
		  }
		  .delete-btn:hover {
		    background: #fee2e2;
		    color: #991b1b;
		    border-color: #991b1b;
		  }
		  .filter-container {
			  display: flex;
			  flex-direction: column;
			  gap: 20px;
			  margin-bottom: 30px;
			}
			
			.filter-row {
			  display: flex;
			  justify-content: space-between;
			  flex-wrap: wrap;
			  gap: 16px;
			}
			
			.filter-group {
			  display: flex;
			  align-items: center;
			  gap: 12px;
			  font-size: 14px;
			  color: var(--text-main);
			}
			
			.filter-group label {
			  display: flex;
			  align-items: center;
			  gap: 6px;
			  font-weight: 500;
			  color: var(--text-sub);
			}
			
			.filter-group input[type="radio"] {
			  accent-color: var(--accent);
			  width: 16px;
			  height: 16px;
			  cursor: pointer;
			}
			
			.filter-group select {
			  padding: 6px 12px;
			  font-size: 14px;
			  border-radius: 8px;
			  border: 1px solid var(--border);
			  background: white;
			  cursor: pointer;
			}
			
			.inline-input {
			  display: flex;
			  flex-wrap: wrap;
			  gap: 12px;
			  align-items: center;
			}
			
			.inline-input input,
			.inline-input select {
			  padding: 10px;
			  font-size: 14px;
			  border-radius: 10px;
			  border: 1px solid var(--border);
			  background: #fff;
			  flex: 1 1 160px;
			  min-width: 140px;
			}
			
			.inline-input button {
			  background-color: var(--accent);
			  color: #fff;
			  border: none;
			  padding: 10px 20px;
			  border-radius: 10px;
			  font-weight: 600;
			  cursor: pointer;
			  transition: background-color 0.2s ease;
			}
			
			.inline-input button:hover {
			  background-color: #e85c26;
			}
			
    </style>

    <!-- URL 매핑 -->
    <c:url var="addTodoUrl" value="/todo/recurring" />
    <c:url var="addRecurringTodoUrl" value="/todo/add" />
    <c:url var="deleteTodoUrl" value="/todo/delete" />
    <c:url var="completeTodoUrl" value="/todo/complete" />
    <c:url var="updateTodoUrl" value="/todo/update" />
    <c:url var="filterTodoUrl" value="/todo/filter" />
    <c:url var="todayStatsUrl" value="/todo/stats/today" />
    <c:url var="todayStatsByTypeUrl" value="/todo/stats/today/by-type" />
    <c:url var="calendarStatsUrl" value="/todo/stats/calendar" />
    <c:url var="backupStatusUrl" value="/todo/stats/backup/status" />
</head>
<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<div class="container">

    <!-- ✅ 탭 버튼 -->
    <div style="margin-bottom: 20px;">
        <button class="tab-btn active" data-type="all">전체</button>
        <button class="tab-btn" data-type="received">받은 숙제</button>
        <button class="tab-btn" data-type="created">내가 생성한 할일</button>
        <button class="tab-btn" onclick="showWeeklyMonthlyGoals()">주간/월별 목표 보기</button>
    </div>

    <!-- ✅ 완료 여부 라디오 -->
    <div class="filter-container">
  <div class="filter-row">
    <div class="filter-group">
      <label><input type="radio" name="completed" value="" checked> 전체</label>
      <label><input type="radio" name="completed" value="false"> 미완료</label>
      <label><input type="radio" name="completed" value="true"> 완료</label>
    </div>

    <div class="filter-group">
      <label for="freqFilter">반복 주기: </label>
      <select id="freqFilter">
          <option value="">전체</option>
          <option value="NONE">반복 없음</option>
          <option value="DAILY">매일</option>
          <option value="WEEKLY">매주</option>
          <option value="MONTHLY">매월</option>
      </select>
    </div>
  </div>

  <div class="inline-input">
    <input type="text" id="newTodoTitle" placeholder="할 일을 입력하세요" required />
    <input type="text" id="newTodoContent" placeholder="상세 내용을 입력하세요" />
    
    <select id="newTodoFreq">
        <option value="NONE">반복 없음</option>
        <option value="DAILY">매일</option>
        <option value="WEEKLY">매주</option>
        <option value="MONTHLY">매월</option>
    </select>

    <input type="date" id="newStartDate" />
    <input type="date" id="newEndDate" />
    
    <button onclick="addTodo()">추가</button>
  </div>
</div>

    <!-- ✅ 목록 테이블 -->
    <table id="todoListContainer">
        <!-- ✅ 비반복 할일 목록 -->
<h3 style="margin-top:20px;">비반복 할일</h3>
<table id="nonRecurringTable">
    <thead>
        <tr>
            <th>완료</th>
            <th>제목</th>
            <th>상세내용</th>
            <th>시작일</th>
            <th>마감일</th>
            <th>상태</th>
            <th>관리</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="todo" items="${todolist}">
            <c:if test="${todo.freq eq 'NONE'}">
                <tr id="todo-${todo.todoId}">
                    <td>
                        <input type="checkbox"
                               onclick="toggleComplete(${todo.todoId}, ${todo.occurrence == true})"
                               <c:if test="${todo.completed}">checked</c:if> />
                    </td>
                    <td class="title ${todo.completed ? 'completed' : ''}">${fn:escapeXml(todo.title)}</td>
                    <td><small>${fn:escapeXml(todo.content)}</small></td>
                    <td>${todo.startDate}</td>
                    <td>${todo.endDate}</td>
                    <td class="todo-status ${todo.completed ? 'completed' : 'not-completed'}">
                        <c:choose>
                            <c:when test="${todo.completed}">완료됨</c:when>
                            <c:otherwise>미완료</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button class="edit-btn"
                                data-id="${todo.todoId}"
                                data-title="${fn:escapeXml(todo.title)}"
                                data-content="${fn:escapeXml(todo.content)}">
                            수정
                        </button>
                        <button class="delete-btn" onclick="deleteTodo(${todo.todoId})">삭제</button>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>

<!-- ✅ 반복 할일 목록 -->
<h3 style="margin-top:30px;"> 반복 할일</h3>
<table id="recurringTable">
    <thead>
        <tr>
            <th>완료</th>
            <th>제목</th>
            <th>상세내용</th>
            <th>시작일</th>
            <th>마감일</th>
            <th>반복</th>
            <th>상태</th>
            <th>관리</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="todo" items="${todolist}">
            <c:if test="${todo.freq ne 'NONE'}">
                <tr id="todo-${todo.todoId}">
                    <td>
                        <input type="checkbox"
                               onclick="toggleComplete(${todo.todoId}, ${todo.occurrence == true})"
                               <c:if test="${todo.completed}">checked</c:if> />
                    </td>
                    <td class="title ${todo.completed ? 'completed' : ''}">${fn:escapeXml(todo.title)}</td>
                    <td><small>${fn:escapeXml(todo.content)}</small></td>
                    <td>${todo.startDate}</td>
                    <td>${todo.endDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${todo.freq == 'DAILY'}">매일</c:when>
                            <c:when test="${todo.freq == 'WEEKLY'}">매주</c:when>
                            <c:when test="${todo.freq == 'MONTHLY'}">매월</c:when>
                            <c:otherwise>반복</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="todo-status ${todo.completed ? 'completed' : 'not-completed'}">
                        <c:choose>
                            <c:when test="${todo.completed}">완료됨</c:when>
                            <c:otherwise>미완료</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button class="edit-btn"
                                data-id="${todo.todoId}"
                                data-title="${fn:escapeXml(todo.title)}"
                                data-content="${fn:escapeXml(todo.content)}">
                            수정
                        </button>
                        <button onclick="deleteTodo(${todo.todoId})">삭제</button>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </tbody>
</table>
    </table>
    <!-- ✅ 달력 -->
    <div id="todo-calendar">
        <div id="cal-header">
            <button type="button" id="cal-prev" class="cal-nav-btn">◀</button>
            <div id="cal-title"></div>
            <button type="button" id="cal-next" class="cal-nav-btn">▶</button>
        </div>
        <div id="cal-grid" class="cal-grid"><!-- 요일+날짜 셀은 JS로 렌더 --></div>
    </div>
	<div id="calendar"></div>
    <div class="nav-links">
        <a href="<c:url value='/mypage' />">마이페이지로</a>
        <a href="<c:url value='/' />">홈으로</a>
    </div>    
</div>
<jsp:include page="/WEB-INF/views/footer.jsp" />
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<!-- ✅ 스크립트 -->
<script>
    /* -----------------------------
       URL 변수
    ------------------------------*/
    const addTodoUrl = '${addTodoUrl}';
    const addRecurringTodoUrl = '${addRecurringTodoUrl}';
    const deleteTodoUrl = '${deleteTodoUrl}';
    const completeTodoUrl = '${completeTodoUrl}';
    const updateTodoUrl = '${updateTodoUrl}';
    const filterTodoUrl = '${filterTodoUrl}';
    const todayStatsByTypeUrl = '${todayStatsByTypeUrl}';
    const calendarStatsUrl = '${calendarStatsUrl}';
	
    /* -----------------------------
       (선택) CSRF 헤더 자동 포함
    ------------------------------*/
    (function setupCsrf() {
      const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
      const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content') || 'X-CSRF-TOKEN';
      if (!token) return;
      $(document).ajaxSend(function (_evt, xhr) {
        xhr.setRequestHeader(header, token);
      });
    })();
	
    const tagColors = [
    	  '#f87171', // red-400
    	  '#fb923c', // orange-400
    	  '#facc15', // yellow-400
    	  '#4ade80', // green-400
    	  '#38bdf8', // sky-400
    	  '#a78bfa', // purple-400
    	  '#f472b6', // pink-400
    	  '#60a5fa', // blue-400
    	  '#34d399', // emerald-400
    	  '#fcd34d'  // amber-400
    	];

    	// 📌 ID 기반 고정 색상 매핑 함수
   	function getColorForId(id) {
   	  const str = String(id);
   	  let hash = 0;
   	  for (let i = 0; i < str.length; i++) {
   	    hash = (hash << 5) - hash + str.charCodeAt(i);
   	    hash |= 0;
   	  }
   	  return tagColors[Math.abs(hash) % tagColors.length];
   	}
    
    /* -----------------------------
       탭/필터 상태
    ------------------------------*/
    let currentType = 'all';
    let currentCompleted = '';
    let currentFreq = '';

    $('.tab-btn').on('click', function () {
        $('.tab-btn').removeClass('active');
        $(this).addClass('active');
        currentType = $(this).data('type');
        applyFilters();
        loadTodayProgressByType();
        refreshCalendarIfNeeded();
    });

    $('input[name="completed"]').on('change', function () {
        currentCompleted = $(this).val();
        applyFilters();
        loadTodayProgressByType();
        refreshCalendarIfNeeded();
    });
    
    $('#freqFilter').on('change', function () {
        currentFreq = $(this).val();
        applyFilters();
        loadTodayProgressByType();
        refreshCalendarIfNeeded();
    });
    function showWeeklyMonthlyGoals() {
        window.location.href = '<c:url value="/goals" />'; // 또는 모달 열기 등 다른 동작
      }
    /* -----------------------------
       목록 필터/갱신
    ------------------------------*/
    function applyFilters() {
        $.get(filterTodoUrl, {
            type: currentType,
            completed: currentCompleted,
            freq: currentFreq
        }, function (data) {
            const nonRecurringBody = $('#nonRecurringTable tbody').empty();
            const recurringBody = $('#recurringTable tbody').empty();

            (data || []).forEach(function (todo) {
                const statusText = todo.completed ? '완료됨' : '미완료';
                const statusClass = todo.completed ? 'completed' : 'not-completed';
                const checked = todo.completed ? 'checked' : '';

                const escapedTitle = $('<div>').text(todo.title || '').html();
                const escapedContent = $('<div>').text(todo.content || '').html();
                const freqText = getFreqText(todo.freq);

                const row = `
                    <tr id="todo-\${todo.todoId}">
                        <td><input type="checkbox" onclick="toggleComplete(\${todo.todoId}, \${todo.occurrence == true})" \${checked} /></td>
                        <td class="title \${statusClass}">\${escapedTitle}</td>
                        <td><small>\${escapedContent}</small></td>
                        <td>\${formatDate(todo.startDate)}</td>
                        <td>\${formatDate(todo.endDate)}</td>
                        \${todo.freq === 'NONE' ? '' : `<td>\${freqText}</td>`}
                        <td class="todo-status \${statusClass}">\${statusText}</td>
                        <td>
                            <button class="edit-btn"
                                data-id="\${todo.todoId}"
                                data-title="\${escapedTitle}"
                                data-content="\${escapedContent}">
                                수정
                            </button>
                            <button onclick="deleteTodo(\${todo.todoId})">삭제</button>
                        </td>
                    </tr>`;

                // ✅ 조건 분기
                if (todo.freq === 'NONE') {
                    nonRecurringBody.append(row);
                } else {
                    recurringBody.append(row);
                }
            });
        });
    }
    function formatDate(dateStr) {
        const date = new Date(dateStr);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `\${year}-\${month}-\${day}`;
    }
    
    function loadTodayProgressByType() {
        // cache-busting으로 항상 최신값
        $.get(todayStatsByTypeUrl, { _: Date.now() }, function (res) {
            // 기본 통계 (전체, 받은 숙제, 내가 생성)
            const basicSets = [
                { key: 'all',      text: '#text-all' },
                { key: 'assigned', text: '#text-assigned' },
                { key: 'created',  text: '#text-created' }
            ];

            basicSets.forEach(function (s) {
                const data = (res && res[s.key]) ? res[s.key] : {};
                const total = Number(data.total) || 0;
                const done  = Number(data.completed) || 0;
                const rate  = (data.rate !== undefined && data.rate !== null)
                    ? Number(data.rate)
                    : (total === 0 ? 0 : Math.floor(done * 100 / total));

                $(s.text).text(rate + '% (' + done + '/' + total + ')');
            });
        }).fail(function () {
            // 기본 통계 실패 시
            ['#text-all', '#text-assigned', '#text-created'].forEach(function (id) {
                $(id).text('0% (0/0)');
            });
        });
    }
    function getFreqText(freq) {
        switch(freq) {
            case 'NONE': return '반복 없음';
            case 'DAILY': return '매일';
            case 'WEEKLY': return '매주';
            case 'MONTHLY': return '매월';
            default: return '반복 없음';
        }
    }
    /* -----------------------------
       추가/완료/삭제/수정
    ------------------------------*/
    function addTodo() {
        const title = $('#newTodoTitle').val().trim();
        const content = $('#newTodoContent').val().trim();
        const freq = $('#newTodoFreq').val(); // NONE, DAILY, WEEKLY 등
        const startDate = $('#newStartDate').val();
        const endDate = $('#newEndDate').val();
        
	    
        
        if (!title) return alert("제목을 입력하세요");

        const data = {
            title,
            content,
            freq,
            startDate: startDate || null,
            endDate: endDate || null
        };
		
        // ✅ 조건 분기
        const url = (freq === 'NONE') ? addTodoUrl : addRecurringTodoUrl;
        
        $.ajax({
            url: url,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
            	
                if (res && res.success) {
                    alert("추가 성공");
                    applyFilters();
                    renderCalendar();
                    loadTodayProgressByType();
                 	
                } else {
                	console.log(data);
                    alert("추가 실패: " + res.error);
                }
            },
            error: function () {
                alert("서버 오류 발생");
            }
        });
    }

    function toggleComplete(todoId, isOccurrence = false) {
        $.post(completeTodoUrl, { todoId, isOccurrence }, function (res) {
            if (res && res.success) {
            	 const $target = $(`[data-id="\${todoId}"]`);
                 if (res.completed) {
                     $target.addClass("completed"); // 회색 처리 등                    
                     $target.remove(); 
                 } else {
                     $target.removeClass("completed");
                     $target.css("opacity", 1);
                 }
                applyFilters();
                loadTodayProgressByType();
                refreshCalendarIfNeeded();
            }
        });
    }
    
    function deleteTodo(todoId) {
        if (!confirm("정말 삭제할까요?")) return;
        $.post(deleteTodoUrl, { todoId }, function (res) {
            if (res && res.success) {
                applyFilters();
                loadTodayProgressByType();
                refreshCalendarIfNeeded();
            } else {
                alert("삭제 실패: " + (res && res.error ? res.error : '서버 오류'));
            }
        }).fail(function () {
            alert("삭제 요청 실패");
        });
    }

    $(document).on('click', '.edit-btn', function () {
        const todoId = $(this).data('id');
        const title = $(this).data('title');
        const content = $(this).data('content');
        showEdit(todoId, title, content);
    });

    function showEdit(todoId, title, content) {
        const $tr = $(`
            <tr id="todo-\${todoId}">
                <td colspan="5">
                    <div style="display: flex; flex-direction: column; gap: 8px; padding: 10px;">
                        <input type="text" id="edit-title-\${todoId}" value="\${title}" placeholder="제목"
                            style="padding: 8px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; width: 100%;" />
                        <input type="text" id="edit-content-\${todoId}" value="\${content}" placeholder="내용"
                            style="padding: 8px; border: 1px solid #ccc; border-radius: 6px; font-size: 14px; width: 100%;" />
                        <button onclick="submitEdit(\${todoId})"
                            style="align-self: flex-end; background-color: #FF6B35; color: white; border: none; border-radius: 6px; padding: 6px 14px; font-size: 14px; cursor: pointer;">
                            저장
                        </button>
                    </div>
                </td>
            </tr>
        `);
        $(`#todo-\${todoId}`).replaceWith($tr);
    }

    function submitEdit(todoId) {
        const title = $(`#edit-title-\${todoId}`).val();
        const content = $(`#edit-content-\${todoId}`).val();

        $.ajax({
            url: updateTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ todoId, title, content }),
            success: function (data) {
                if (data && data.success) {
                    applyFilters();
                    loadTodayProgressByType();
                    refreshCalendarIfNeeded();
                } else {
                    alert("수정 실패");
                }
            },
            error: function () { alert("수정 요청 실패"); }
        });
    }
    /* -----------------------------
                캘린더 
    ------------------------------*/
    

    var calYear = (new Date()).getFullYear();
    var calMonth = (new Date()).getMonth() + 1; // 1~12

    function appendWeekdayHeaders($grid) {
      var names = ['일','월','화','수','목','금','토'];
      for (var i=0; i<7; i++) {
        var h = $('<div>').addClass('cal-cell').css({
          background:'#fff', minHeight:'auto', padding:'8px', textAlign:'center', fontWeight:'600'
        }).text(names[i]);
        $grid.append(h);
      }
    }

    function renderCalendar() {
   	  $('#cal-title').text(calYear + '년 ' + (calMonth < 10 ? '0' + calMonth : calMonth) + '월');

   	  var $grid = $('#cal-grid');
   	  $grid.empty();

   	  appendWeekdayHeaders($grid);

   	  var first = new Date(calYear, calMonth - 1, 1);
   	  var firstDow = first.getDay();
   	  var daysInMonth = new Date(calYear, calMonth, 0).getDate();

   	  for (var i = 0; i < firstDow; i++) {
   	    $grid.append($('<div>').addClass('cal-cell cal-muted'));
   	  }

   	  for (var d = 1; d <= daysInMonth; d++) {
   	    var ymd = calYear + '-' +
   	      (calMonth < 10 ? '0' + calMonth : calMonth) + '-' +
   	      (d < 10 ? '0' + d : d);

   	    var $cell = $('<div>').addClass('cal-cell');
   	    $cell.attr('data-date', ymd); // ✅ 날짜 식별자 부여
   	    $cell.append($('<div>').addClass('cal-day').text(d));
   	    $grid.append($cell);
   	  }

   	  var totalCells = 7 + firstDow + daysInMonth;
   	  var tail = (7 - (totalCells % 7)) % 7;
   	  for (var k = 0; k < tail; k++) {
   	    $grid.append($('<div>').addClass('cal-cell cal-muted'));
   	  }

   	  // ✅ 캘린더 할일 불러오기
   	  fetchCalendarTodos(calYear, calMonth);
   	}	
    
   const todoEventsUrl = '<c:url value="/todo/events" />';
   function fetchCalendarTodos(calYear, calMonth) {
   	  const startStr = `\${calYear}-\${String(calMonth).padStart(2, '0')}-01`;
   	  const endDate = new Date(calYear, calMonth+1, 0);
   	  console.log(endDate);
   	  const endStr = `\${calYear}-\${String(calMonth).padStart(2, '0')}-\${String(endDate.getDate()).padStart(2, '0')}`;
		
   	  $.get(todoEventsUrl, { start: startStr, end: endStr }, function (todoEvents) {
   		 console.log("🔥 todoEvents:", todoEvents);
   	    renderTodosOnCalendar(todoEvents);
   	  });
   	}

    $(document).on('click', '#cal-prev', function() {
      calMonth--;
      if (calMonth < 1) { calMonth = 12; calYear--; }
      renderCalendar();
    });
    $(document).on('click', '#cal-next', function() {
      calMonth++;
      if (calMonth > 12) { calMonth = 1; calYear++; }
      renderCalendar();
    });
   
    function renderTodosOnCalendar(events) {
    	  events.forEach(event => {
    		  console.log(event);
    		if (event.completed) return;  
    	    const startDate = new Date(event.start);
    	    const endDate = event.end ? new Date(event.end) : new Date(event.start);
    	    const startDateStr = event.start;
    	    const endDateStr = event.end ?? event.start;

    	    const datesToRender = [];

    	    for (
    	      let date = new Date(startDate);
    	      date <= endDate;
    	      date.setDate(date.getDate() + 1)
    	    ) {
    	      const dateStr = date.toISOString().split('T')[0];
    	      datesToRender.push(dateStr);
    	    }
			
    	    // 막대 색상 재사용
    	    const backgroundColor = event.completed ? '#e2e8f0' : getColorForId(event.id);

    	    datesToRender.forEach((dateStr, idx) => {
    	      const isStart = dateStr === startDateStr;
    	      const isEnd = dateStr === endDateStr;

    	      const $cell = $(`[data-date="\${dateStr}"]`);
    	      if ($cell.length === 0 || event.hidden) return;

    	      const $todoDiv = $('<div>')
	    	      .addClass('todo-tag')
	    	      .attr('data-id', event.id)
	    	      .addClass(isStart ? 'start' : '')   // 좌측 둥근 처리
	    	      .addClass(isEnd ? 'end' : '')       // 우측 둥근 처리
	    	      .text(isStart ? event.title + (event.recurring ? ' 🔁' : '') : '')
	    	      .css({
	    	        height: '16px',
	    	        padding: '0 6px',
	    	        backgroundColor: backgroundColor,
	    	        color: '#1e293b',
	    	        textDecoration: event.completed ? 'line-through' : 'none',
	    	        fontSize: '11px',
	    	        width: '100%',
	    	        margin: '2px 0',
	    	        boxSizing: 'border-box',
	    	        opacity: event.completed ? 0.3 : 1
	    	      });

    	      // 툴팁
    	      $todoDiv.attr('title', event.title);

    	      $cell.append($todoDiv);
    	    });
    	  });
    	}


    
    
    function shouldShowTodoOnDate(todo, targetDate) {
        const start = new Date(todo.start);
        const end = new Date(todo.end);
		
        if (targetDate < start || targetDate > end) return false;

        const freq = todo.freq;
        if (freq === 'DAILY') return true;

        if (freq === 'WEEKLY') return targetDate.getDay() === start.getDay();

        if (freq === 'MONTHLY') return targetDate.getDate() === start.getDate();

        if (freq === 'NONE') {
          
            return true;
        }

        return false;
    }

    function formatDateLocal(date) {
        return date.getFullYear() + '-' +
               String(date.getMonth() + 1).padStart(2, '0') + '-' +
               String(date.getDate()).padStart(2, '0');
    }

    // 외부에서 필요 시 호출
    window.refreshCalendarIfNeeded = function() { renderCalendar(); };
    
    

    // 목표 관련 URL
    const goalCreateUrl = '<c:url value="/goals/create" />';
    const goalProgressUrl = '<c:url value="/goals/progress" />';
    const goalProgressWeeklyUrl = '<c:url value="/goals/progress/weekly" />';
    const goalProgressmonthlyUrl = '<c:url value="/goals/progress/monthly" />';
    
    // 목표 추가 버튼 클릭
    $('#add-goal-btn').on('click', function() {
        $('#goalModal').modal('show');
    });
    
    // 목표 저장 버튼 클릭
    $('#saveGoalBtn').on('click', function() {
        const goalData = {
            title: $('#goalTitle').val(),
            description: $('#goalDescription').val(),
            goalType: $('#goalType').val(),
            startDate: $('#goalStartDate').val(),
            endDate: $('#goalEndDate').val(),
            targetCount: parseInt($('#goalTargetCount').val())
        };
        
        $.ajax({
            url: goalCreateUrl,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(goalData),
            success: function(response) {
                if (response.success) {
                    alert('목표가 생성되었습니다!');
                    $('#goalModal').modal('hide');
                    $('#goalForm')[0].reset();
                    loadGoalProgress();
                } else {
                    alert('목표 생성에 실패했습니다: ' + response.message);
                }
            },
            error: function() {
                alert('목표 생성 중 오류가 발생했습니다.');
            }
        });
    });
    
    function loadGoalProgress() {
        const today = new Date();
        const year = today.getFullYear();
        const month = today.getMonth() + 1;
        
        // 월간 목표 진행률
        $.get(goalProgressmonthlyUrl, { year: year, month: month }, function(response) {
            if (response.success && response.data) {
                const data = response.data;
                const total = Number(data.total_goals) || 0;
                const achieved = Number(data.achieved_goals) || 0;
                const rate = total === 0 ? 0 : Math.floor(achieved * 100 / total);
                
               
                $('#text-monthly').text(rate + '% (' + achieved + '/' + total + ')');
                $('#pct-monthly').text(rate + '%');
            }
        });
        
        // 주간 목표 진행률 (이번 주 시작일 계산)
        const weekStart = new Date(today);
        weekStart.setDate(today.getDate() - today.getDay());
        const weekStartStr = weekStart.toISOString().split('T')[0];
        
        $.get(goalProgressWeeklyUrl, { weekStart: weekStartStr }, function(response) {
            if (response.success && response.data) {
                const data = response.data;
                const total = Number(data.total_goals) || 0;
                const achieved = Number(data.achieved_goals) || 0;
                const rate = total === 0 ? 0 : Math.floor(achieved * 100 / total);
                
                
                $('#text-weekly').text(rate + '% (' + achieved + '/' + total + ')');
                $('#pct-weekly').text(rate + '%');
            }
        });
    }
    
    
    /* -----------------------------
       초기 로딩
    ------------------------------*/
    $(function () {
        loadTodayProgressByType(); // 오늘 달성률 도넛
        loadGoalProgress();        // 목표 진행률
        applyFilters();            // 첫 목록
        renderCalendar();          // 캘린더     
    });
</script>

</body>
</html>
