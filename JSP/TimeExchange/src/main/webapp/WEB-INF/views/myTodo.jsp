<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>My TODO List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        /* [중략] 기존 스타일 그대로 유지 — 너가 준 걸 그대로 적용함 */
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
    </style>

    <c:url var="addTodoUrl" value="/todo/add" />
    <c:url var="deleteTodoUrl" value="/todo/delete" />
    <c:url var="completeTodoUrl" value="/todo/complete" />
    <c:url var="updateTodoUrl" value="/todo/update" />
    <c:url var="filterTodoUrl" value="/todo/filter" />
    <c:url var="todayStatsUrl" value="/todo/stats/today" />
    <c:url var="todayStatsByTypeUrl" value="/todo/stats/today/by-type" />
</head>
<body>

<h2>My TODO List</h2>

<div id="today-progress-group" style="display:grid;gap:10px;margin:14px 0;">
  <div>
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 전체</div>
    <div style="height:10px;background:#e5e7eb;border-radius:6px;overflow:hidden;">
      <div id="bar-all" style="height:10px;width:0%;background:#38bdf8;transition:width .3s"></div>
    </div>
    <div id="text-all" style="font-size:12px;color:#6b7280;margin-top:6px;">0% (0/0)</div>
  </div>

  <div>
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 받은 숙제</div>
    <div style="height:10px;background:#e5e7eb;border-radius:6px;overflow:hidden;">
      <div id="bar-assigned" style="height:10px;width:0%;background:#22c55e;transition:width .3s"></div>
    </div>
    <div id="text-assigned" style="font-size:12px;color:#6b7280;margin-top:6px;">0% (0/0)</div>
  </div>

  <div>
    <div style="font-size:14px;margin-bottom:6px;">오늘 달성률 - 내가 생성</div>
    <div style="height:10px;background:#e5e7eb;border-radius:6px;overflow:hidden;">
      <div id="bar-created" style="height:10px;width:0%;background:#a78bfa;transition:width .3s"></div>
    </div>
    <div id="text-created" style="font-size:12px;color:#6b7280;margin-top:6px;">0% (0/0)</div>
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
        <input type="text" id="newTodoInput" placeholder="할 일을 입력하세요" />
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
                    <td><input type="checkbox" onclick="toggleComplete(${todo.todoId})" <c:if test="${todo.completed}">checked</c:if> /></td>
                    <td class="title <c:if test='${todo.completed}'>completed</c:if>'">${fn:escapeXml(todo.title)}</td>
                    <td><small>${fn:escapeXml(todo.content)}</small></td>
                    <td class="todo-status <c:choose>
                        <c:when test='${todo.completed}'>completed</c:when>
                        <c:otherwise>not-completed</c:otherwise>
                    </c:choose>">
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

<!-- ✅ 스크립트 -->
<script>
    const addTodoUrl = '${addTodoUrl}';
    const deleteTodoUrl = '${deleteTodoUrl}';
    const completeTodoUrl = '${completeTodoUrl}';
    const updateTodoUrl = '${updateTodoUrl}';
    const filterTodoUrl = '${filterTodoUrl}';
    const todayStatsByTypeUrl = '${todayStatsByTypeUrl}';

    let currentType = 'all';
    let currentCompleted = '';

    $('.tab-btn').on('click', function () {
        $('.tab-btn').removeClass('active');
        $(this).addClass('active');
        currentType = $(this).data('type');
        applyFilters();
    });

    $('input[name="completed"]').on('change', function () {
        currentCompleted = $(this).val();
        applyFilters();
    });

    function applyFilters() {
        $.get(filterTodoUrl, {
            type: currentType,
            completed: currentCompleted
        }, function (data) {
            const tbody = $('#todoListContainer tbody').empty();
            data.forEach(todo => {
                const statusText = todo.completed ? '완료됨' : '미완료';
                const statusClass = todo.completed ? 'completed' : 'not-completed';
                const checked = todo.completed ? 'checked' : '';
                const escapedTitle = $('<div>').text(todo.title).html();
                const escapedContent = $('<div>').text(todo.content).html();

                const row = `
                    <tr id="todo-\${todo.todoId}">
                        <td><input type="checkbox" onclick="toggleComplete(\${todo.todoId})" \${checked} /></td>
                        <td class="title \${statusClass}">\${escapedTitle}</td>
                        <td><small>${escapedContent}</small></td>
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
                    console.log(row);
                tbody.append(row);
            });
        });
    }

    function addTodo() {
        const title = $('#newTodoInput').val().trim();
        const content = $('#newContentInput').val().trim();
        
        if (!title || !content) return alert("제목과 내용을 입력하세요");

        $.ajax({
            url: addTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ title, content }),
            success: function (data) {
                if (data.success) {
                	 applyFilters();
                	 loadTodayProgressByType(); 
                }
            }
        });
    }

    function toggleComplete(todoId) {
        $.post(completeTodoUrl, { todoId }, function (res) {
            if (res.success) {
            	 applyFilters();
            	 loadTodayProgressByType(); 
            }
        });
    }

    function deleteTodo(todoId) {
        if (!confirm("정말 삭제할까요?")) return;
        $.post(deleteTodoUrl, { todoId }, function (res) {
            if (res.success) {
            	 applyFilters();
            	 loadTodayProgressByType(); 
            }
            else alert("삭제 실패: " + res.error);
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
        $(`#todo-${todoId}`).replaceWith($tr);
    }

    function submitEdit(todoId) {
        const title = $(`#edit-title-\${todoId}`).val();
        const content = $(`#edit-content-\${todoId}`).val();

        $.ajax({
            url: updateTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ todoId, title, content }),
            success: res => {
                if (res.success) {
                	 applyFilters();
                	 loadTodayProgressByType(); 
                }
                else alert("수정 실패");
            }
        });
    }
    
    function loadTodayProgressByType() {
        $.get(todayStatsByTypeUrl, function (res) {
          const sets = [
            {key: 'all',      bar: '#bar-all',      text: '#text-all'},
            {key: 'assigned', bar: '#bar-assigned', text: '#text-assigned'},
            {key: 'created',  bar: '#bar-created',  text: '#text-created'}
          ];

          sets.forEach(s => {
            const data = (res && res[s.key]) ? res[s.key] : { total:0, completed:0, rate:0 };
            const total = data.total || 0;
            const done  = data.completed || 0;
            const rate  = data.rate || 0;
            \$(s.bar).css('width', rate + '%');
            \$(s.text).text(`\${rate}% (\${done}/\${total})`);
          });
        }).fail(function() {
          ['#bar-all','#bar-assigned','#bar-created'].forEach(id => \$(id).css('width', '0%'));
          ['#text-all','#text-assigned','#text-created'].forEach(id => \$(id).text('0% (0/0)'));
        });
      }
    $(function () {
    	loadTodayProgressByType();  // 최초 1회
        applyFilters();       // (선택) 첫 목록 로딩
      });
    
    const calendarStatsUrl = '${calendarStatsUrl}';
    var calYear = (new Date()).getFullYear();
    var calMonth = (new Date()).getMonth() + 1; // 1~12

    // 요일 헤더(일~토)
    function appendWeekdayHeaders($grid) {
      var names = ['일','월','화','수','목','금','토'];
      for (var i=0; i<7; i++) {
        var h = $('<div>').addClass('cal-cell').css({background:'#fff', minHeight:'auto', padding:'8px', textAlign:'center', fontWeight:'600'})
                          .text(names[i]);
        $grid.append(h);
      }
    }

    // 색상 매핑(달성률에 따라)
    function rateColor(rate) {
      if (rate >= 80) return '#16a34a';
      if (rate >= 60) return '#22c55e';
      if (rate >= 40) return '#84cc16';
      if (rate >= 20) return '#f59e0b';
      if (rate > 0)  return '#f97316';
      return '#94a3b8';
    }

    // 캘린더 렌더
    function renderCalendar() {
      $('#cal-title').text(calYear + '년 ' + (calMonth<10 ? '0'+calMonth : calMonth) + '월');

      var $grid = $('#cal-grid');
      $grid.empty();

      // 요일 헤더 7칸
      appendWeekdayHeaders($grid);

      // 1일 정보
      var first = new Date(calYear, calMonth - 1, 1);
      var firstDow = first.getDay(); // 0~6
      var daysInMonth = new Date(calYear, calMonth, 0).getDate();

      // 앞쪽 빈칸
      for (var i=0; i<firstDow; i++) {
        $grid.append($('<div>').addClass('cal-cell cal-muted'));
      }

      // 데이터 요청
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

        // 뒷쪽 빈칸으로 열 맞추기
        var totalCells = 7 /*weekday*/ + firstDow + daysInMonth;
        var tail = (7 - (totalCells % 7)) % 7;
        for (var k=0; k<tail; k++) {
          $grid.append($('<div>').addClass('cal-cell cal-muted'));
        }
      }).fail(function() {
        // 실패 시 달력 틀만 구성
        for (var d=1; d<=daysInMonth; d++) {
          var $cell = $('<div>').addClass('cal-cell');
          $cell.append($('<div>').addClass('cal-day').text(d));
          $grid.append($cell);
        }
      });
    }

    // 네비게이션
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

    // 최초 1회
    $(function() {
      renderCalendar();
    });

    // (선택) 아래 함수는 팝업/리스트 액션 후 캘린더도 최신화하고 싶을 때 호출
    window.refreshCalendarIfNeeded = function() { renderCalendar(); };
</script>

</body>
</html>
