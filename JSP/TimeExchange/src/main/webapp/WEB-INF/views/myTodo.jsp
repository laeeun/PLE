<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>My TODO List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- (선택) Spring Security CSRF 메타 -->
    <!--
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    -->

    <style>
        /* 기존 스타일 유지 + 필요한 부분만 추가/수정 */
        .tab-btn {
            padding: 8px 16px;
            font-size: 14px;
            margin-right: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            background-color: #f8fafc;
            cursor: pointer;
            transition: 0.2s;
        }
        .tab-btn.active {
            background-color: #e0f2fe;
            color: #0ea5e9;
            border-color: #38bdf8;
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

        /* 네비 */
        .nav-links { display:flex; gap:10px; margin:12px 0; }
        .nav-links a { padding:6px 10px; border:1px solid #e5e7eb; border-radius:8px; background:#fff; text-decoration:none; color:#111827; }

        /* ✅ 도넛 그래프 */
        .donut-group { display:grid; gap:14px; margin:14px 0; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); }
        .donut-card { padding:8px 10px; border:1px solid #e5e7eb; border-radius:10px; background:#fff; }
        .donut-wrap { display:flex; align-items:center; gap:12px; }
        .donut { position:relative; width:72px; height:72px; }
        .donut svg { transform: rotate(-90deg); } /* 위쪽(12시)부터 그리기 */
        .donut .bg   { stroke:#e5e7eb; stroke-width:10; fill:none; }
        .donut .ring { stroke:var(--ring-color, #38bdf8); stroke-width:10; fill:none; stroke-linecap:round; transition:stroke-dashoffset .45s ease; }
        .donut .center { position:absolute; inset:0; display:flex; align-items:center; justify-content:center; font-weight:700; font-size:14px; }
    </style>

    <!-- URL 매핑 -->
    <c:url var="addTodoUrl" value="/todo/add" />
    <c:url var="deleteTodoUrl" value="/todo/delete" />
    <c:url var="completeTodoUrl" value="/todo/complete" />
    <c:url var="updateTodoUrl" value="/todo/update" />
    <c:url var="filterTodoUrl" value="/todo/filter" />
    <c:url var="todayStatsUrl" value="/todo/stats/today" />
    <c:url var="todayStatsByTypeUrl" value="/todo/stats/today/by-type" />
    <c:url var="calendarStatsUrl" value="/todo/stats/calendar" />
</head>
<body>

<h2>My TODO List</h2>

<!-- ✅ 오늘 달성률: 도넛 그래프 3종 -->
<div id="today-progress-group" class="donut-group">
  <!-- 전체 -->
  <div class="donut-card" style="--ring-color:#38bdf8;">
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 전체</div>
    <div class="donut-wrap">
      <div class="donut" data-id="all">
        <svg width="72" height="72">
          <circle class="bg"   cx="36" cy="36" r="30"></circle>
          <circle class="ring" id="ring-all" cx="36" cy="36" r="30"
                  stroke-dasharray="188.5" stroke-dashoffset="188.5"></circle>
        </svg>
        <div class="center" id="pct-all">0%</div>
      </div>
      <div class="meta" id="text-all" style="font-size:12px;color:#6b7280;">0% (0/0)</div>
    </div>
  </div>

  <!-- 받은 숙제 -->
  <div class="donut-card" style="--ring-color:#22c55e;">
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 받은 숙제</div>
    <div class="donut-wrap">
      <div class="donut" data-id="assigned">
        <svg width="72" height="72">
          <circle class="bg"   cx="36" cy="36" r="30"></circle>
          <circle class="ring" id="ring-assigned" cx="36" cy="36" r="30"
                  stroke-dasharray="188.5" stroke-dashoffset="188.5"></circle>
        </svg>
        <div class="center" id="pct-assigned">0%</div>
      </div>
      <div class="meta" id="text-assigned" style="font-size:12px;color:#6b7280;">0% (0/0)</div>
    </div>
  </div>

  <!-- 내가 생성 -->
  <div class="donut-card" style="--ring-color:#a78bfa;">
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 내가 생성</div>
    <div class="donut-wrap">
      <div class="donut" data-id="created">
        <svg width="72" height="72">
          <circle class="bg"   cx="36" cy="36" r="30"></circle>
          <circle class="ring" id="ring-created" cx="36" cy="36" r="30"
                  stroke-dasharray="188.5" stroke-dashoffset="188.5"></circle>
        </svg>
        <div class="center" id="pct-created">0%</div>
      </div>
      <div class="meta" id="text-created" style="font-size:12px;color:#6b7280;">0% (0/0)</div>
    </div>
  </div>
</div>

<div class="container">
    <!-- ✅ 탭 버튼 -->
    <div style="margin-bottom: 20px;">
        <button class="tab-btn active" data-type="all">전체</button>
        <button class="tab-btn" data-type="received">받은 숙제</button>
        <button class="tab-btn" data-type="created">내가 생성한 할일</button>
    </div>

    <!-- ✅ 완료 여부 라디오 -->
    <div>
        <label><input type="radio" name="completed" value="" checked> 전체</label>
        <label><input type="radio" name="completed" value="false"> 미완료</label>
        <label><input type="radio" name="completed" value="true"> 완료</label>
    </div>

    <!-- ✅ 할일 추가 -->
    <div class="inline-input">
        <input type="text" id="newTodoInput" placeholder="할 일을 입력하세요" required />
        <input type="text" id="newContentInput" placeholder="상세 내용을 입력하세요" />
        <button onclick="addTodo()">추가</button>
    </div>

    <!-- ✅ 목록 테이블 -->
    <table id="todoListContainer">
        <thead>
            <tr>
                <th>완료</th>
                <th>제목</th>
                <th>상세내용</th>
                <th>상태</th>
                <th>관리</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="todo" items="${todolist}">
                <tr id="todo-${todo.todoId}">
                    <td>
                        <input type="checkbox"
                               onclick="toggleComplete(${todo.todoId})"
                               <c:if test="${todo.completed}">checked</c:if> />
                    </td>
                    <td class="title ${todo.completed ? 'completed' : ''}">
                        ${fn:escapeXml(todo.title)}
                    </td>
                    <td><small>${fn:escapeXml(todo.content)}</small></td>
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
            </c:forEach>
        </tbody>
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

    <div class="nav-links">
        <a href="<c:url value='/mypage' />">마이페이지로</a>
        <a href="<c:url value='/' />">홈으로</a>
    </div>
</div>
<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- ✅ 스크립트 -->
<script>
    /* -----------------------------
       URL 변수
    ------------------------------*/
    const addTodoUrl = '${addTodoUrl}';
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

    /* -----------------------------
       탭/필터 상태
    ------------------------------*/
    let currentType = 'all';
    let currentCompleted = '';

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

    /* -----------------------------
       목록 필터/갱신
    ------------------------------*/
    function applyFilters() {
        $.get(filterTodoUrl, {
            type: currentType,
            completed: currentCompleted
        }, function (data) {
            const tbody = $('#todoListContainer tbody').empty();
            (data || []).forEach(function (todo) {
                const statusText = todo.completed ? '완료됨' : '미완료';
                const statusClass = todo.completed ? 'completed' : 'not-completed';
                const checked = todo.completed ? 'checked' : '';

                // XSS-safe escape
                const escapedTitle = $('<div>').text(todo.title || '').html();
                const escapedContent = $('<div>').text(todo.content || '').html();

                const row = `
                    <tr id="todo-\${todo.todoId}">
                        <td><input type="checkbox" onclick="toggleComplete(\${todo.todoId})" \${checked} /></td>
                        <td class="title \${statusClass}">\${escapedTitle}</td>
                        <td><small>\${escapedContent}</small></td>
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
                tbody.append(row);
            });
        });
    }

    /* -----------------------------
       추가/완료/삭제/수정
    ------------------------------*/
    function addTodo() {
        const title = $('#newTodoInput').val().trim();
        const content = $('#newContentInput').val().trim();

        if (!title) return alert("제목을 입력하세요");

        $.ajax({
            url: addTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ title, content }),
            success: function (data) {
                if (data && data.success) {
                    applyFilters();
                    loadTodayProgressByType();
                    refreshCalendarIfNeeded();
                    // 입력창 초기화
                    $('#newTodoInput').val('');
                    $('#newContentInput').val('');
                    $('#newTodoInput').focus();
                } else {
                    alert("추가 실패");
                }
            },
            error: function () { alert("추가 실패"); }
        });
    }

    function toggleComplete(todoId) {
        $.post(completeTodoUrl, { todoId }, function (res) {
            if (res && res.success) {
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
                    <input type="text" id="edit-title-\${todoId}" value="\${title}" placeholder="제목" />
                    <input type="text" id="edit-content-\${todoId}" value="\${content}" placeholder="내용" />
                    <button onclick="submitEdit(\${todoId})">저장</button>
                </td>
            </tr>`);
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
       ✅ 도넛 그래프 유틸 + 오늘 달성률 로딩
    ------------------------------*/
    const DONUT_R = 30;                               // r=30 (HTML과 동일)
    const DONUT_C = 2 * Math.PI * DONUT_R;            // 둘레 ≈ 188.5

    function setDonut(key, rate) {
        const r = Math.max(0, Math.min(Number(rate) || 0, 100)); // 0~100 보정
        const off = DONUT_C * (1 - r / 100);

        const ring = document.getElementById(`ring-\${key}`);
        const pct  = document.getElementById(`pct-\${key}`);
        if (!ring || !pct) return;

        ring.setAttribute('stroke-dasharray', DONUT_C.toFixed(1));
        ring.style.strokeDashoffset = off.toFixed(1);
        pct.textContent = `\${r}%`;
    }

    function loadTodayProgressByType() {
        // cache-busting으로 항상 최신값
        $.get(todayStatsByTypeUrl, { _: Date.now() }, function (res) {
            const sets = [
                { key: 'all',      text: '#text-all' },
                { key: 'assigned', text: '#text-assigned' },
                { key: 'created',  text: '#text-created' }
            ];

            sets.forEach(function (s) {
                const data = (res && res[s.key]) ? res[s.key] : {};
                const total = Number(data.total) || 0;
                const done  = Number(data.completed) || 0;
                const rate  = (data.rate !== undefined && data.rate !== null)
                    ? Number(data.rate)
                    : (total === 0 ? 0 : Math.floor(done * 100 / total));

                setDonut(s.key, rate);
                $(s.text).text(rate + '% (' + done + '/' + total + ')');
            });
        }).fail(function () {
            ['all','assigned','created'].forEach(function (k) { setDonut(k, 0); });
            ['#text-all', '#text-assigned', '#text-created'].forEach(function (id) {
                $(id).text('0% (0/0)');
            });
        });
    }

    /* -----------------------------
       캘린더 (월간 달성률 뷰)
    ------------------------------*/
    function rateColor(rate) {
      if (rate >= 80) return '#16a34a';
      if (rate >= 60) return '#22c55e';
      if (rate >= 40) return '#84cc16';
      if (rate >= 20) return '#f59e0b';
      if (rate > 0)  return '#f97316';
      return '#94a3b8';
    }

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
      $('#cal-title').text(calYear + '년 ' + (calMonth<10 ? '0'+calMonth : calMonth) + '월');

      var $grid = $('#cal-grid');
      $grid.empty();

      appendWeekdayHeaders($grid);

      var first = new Date(calYear, calMonth - 1, 1);
      var firstDow = first.getDay(); // 0~6
      var daysInMonth = new Date(calYear, calMonth, 0).getDate();

      for (var i=0; i<firstDow; i++) {
        $grid.append($('<div>').addClass('cal-cell cal-muted'));
      }

      $.get(calendarStatsUrl, { year: calYear, month: calMonth }, function(res) {
        var map = {};
        if (res && res.days) {
          for (var j=0; j<res.days.length; j++) {
            var it = res.days[j];
            map[it.date] = it; // yyyy-MM-dd
          }
        }

        for (var d=1; d<=daysInMonth; d++) {
          var ymd = calYear + '-' + (calMonth<10 ? '0'+calMonth : calMonth) + '-' + (d<10 ? '0'+d : d);
          var it = map[ymd] || { total:0, completed:0, rate:0, isFuture:false };

          var rate = it.rate || 0;
          var label = (it.total > 0) ? (rate + '% (' + it.completed + '/' + it.total + ')')
                                     : (it.isFuture ? '--' : '0% (0/0)');
          var color = rateColor(rate);

          var $cell = $('<div>').addClass('cal-cell' + (it.isFuture ? ' cal-future' : ''));
          $cell.append($('<div>').addClass('cal-day').text(d));
          $cell.append($('<div>').addClass('cal-rate').css('color', color)
                                 .text((it.total>0) ? (rate + '%') : (it.isFuture ? '--' : '0%')));
          var $barOuter = $('<div>').addClass('cal-bar-outer');
          var $barInner = $('<div>').addClass('cal-bar-inner').css({ width: rate + '%', background: color });
          $barOuter.append($barInner);
          $cell.append($barOuter);
          $cell.append($('<div>').css({fontSize:'11px', color:'#64748b', marginTop:'4px'}).text(label));

          $grid.append($cell);
        }

        var totalCells = 7 /*weekday*/ + firstDow + daysInMonth;
        var tail = (7 - (totalCells % 7)) % 7;
        for (var k=0; k<tail; k++) {
          $grid.append($('<div>').addClass('cal-cell cal-muted'));
        }
      }).fail(function() {
        for (var d=1; d<=daysInMonth; d++) {
          var $cell = $('<div>').addClass('cal-cell');
          $cell.append($('<div>').addClass('cal-day').text(d));
          $grid.append($cell);
        }
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

    // 외부에서 필요 시 호출
    window.refreshCalendarIfNeeded = function() { renderCalendar(); };

    /* -----------------------------
       초기 로딩
    ------------------------------*/
    $(function () {
        loadTodayProgressByType(); // 오늘 달성률 도넛
        applyFilters();            // 첫 목록
        renderCalendar();          // 캘린더
    });
</script>

</body>
</html>
