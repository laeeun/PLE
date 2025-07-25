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
    </style>

    <c:url var="addTodoUrl" value="/todo/add" />
    <c:url var="deleteTodoUrl" value="/todo/delete" />
    <c:url var="completeTodoUrl" value="/todo/complete" />
    <c:url var="updateTodoUrl" value="/todo/update" />
    <c:url var="filterTodoUrl" value="/todo/filter" />
</head>
<body>

<h2>My TODO List</h2>

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
            success: function (res) {
                if (res.success) location.reload();
            }
        });
    }

    function toggleComplete(todoId) {
        $.post(completeTodoUrl, { todoId }, function (res) {
            if (res.success) applyFilters();
        });
    }

    function deleteTodo(todoId) {
        if (!confirm("정말 삭제할까요?")) return;
        $.post(deleteTodoUrl, { todoId }, function (res) {
            if (res.success) location.reload();
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
            <tr id="todo-${todoId}">
                <td colspan="5">
                    <input type="text" id="edit-title-${todoId}" value="${title}" placeholder="제목" />
                    <input type="text" id="edit-content-${todoId}" value="${content}" placeholder="내용" />
                    <button onclick="submitEdit(${todoId})">저장</button>
                </td>
            </tr>`);
        $(`#todo-${todoId}`).replaceWith($tr);
    }

    function submitEdit(todoId) {
        const title = $(`#edit-title-${todoId}`).val();
        const content = $(`#edit-content-${todoId}`).val();

        $.ajax({
            url: updateTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ todoId, title, content }),
            success: res => {
                if (res.success) applyFilters();
                else alert("수정 실패");
            }
        });
    }
</script>

</body>
</html>
