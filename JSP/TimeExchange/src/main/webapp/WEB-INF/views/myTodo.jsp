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
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f9f9f9;
            padding: 30px;
        }

        h2 {
            margin-bottom: 25px;
            color: #2c3e50;
            font-size: 24px;
            font-weight: bold;
        }

        .container {
            max-width: 960px;
            margin: 0 auto;
            background: #fff;
            padding: 20px 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }

        .inline-input {
            margin-bottom: 20px;
        }

        .inline-input input[type="text"] {
            padding: 9px 12px;
            width: 220px;
            margin-right: 12px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .inline-input button {
            padding: 9px 16px;
            font-size: 14px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .inline-input button:hover {
            background-color: #27ae60;
        }

        #todoListContainer {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        #todoListContainer thead {
            background-color: #f1f5f9;
        }

        #todoListContainer th, #todoListContainer td {
            border: 1px solid #ddd;
            padding: 12px 10px;
            text-align: center;
            vertical-align: middle;
        }

        #todoListContainer th {
            font-size: 15px;
            color: #444;
            font-weight: bold;
        }

        .title {
            font-weight: 600;
            font-size: 15px;
            color: #222;
        }

        .todo-status.completed {
            color: #22c55e;
            font-weight: bold;
        }

        .todo-status.not-completed {
            color: #f97316;
            font-weight: bold;
        }

        #todoListContainer button {
            padding: 5px 10px;
            font-size: 13px;
            background-color: #38bdf8;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin-right: 5px;
        }

        #todoListContainer button:hover {
            background-color: #0ea5e9;
        }

        .container > div:first-child label {
            margin-right: 15px;
            font-size: 14px;
            color: #555;
        }

        a {
            display: inline-block;
            margin-top: 30px;
            color: #3498db;
            font-weight: bold;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        input[type="checkbox"] {
            transform: scale(1.2);
            cursor: pointer;
        }

        input[type="text"] {
            width: 180px;
            padding: 6px 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
            background: #f0f9ff;
        }

        input[type="text"]:focus {
            outline: none;
            border-color: #38bdf8;
            background: #e0f7ff;
        }
        .nav-links {
		    margin-top: 30px;
		    display: flex;
		    gap: 20px;
		}
		
		.nav-links a {
		    display: inline-block;
		    padding: 10px 18px;
		    background-color: #f3f4f6;
		    color: #333;
		    font-weight: 500;
		    text-decoration: none;
		    border: 1px solid #ddd;
		    border-radius: 6px;
		    transition: background-color 0.2s, color 0.2s, border-color 0.2s;
		}
		
		.nav-links a:hover {
		    background-color: #e0f2fe;
		    border-color: #38bdf8;
		    color: #0ea5e9;
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

<div class="container mt-5 mb-5">

    <!-- ✅ 필터 -->
    <div>
        <label><input type="radio" name="completed" value="" checked> 전체</label>
        <label><input type="radio" name="completed" value="false"> 미완료</label>
        <label><input type="radio" name="completed" value="true"> 완료</label>
    </div>

    <!-- ✅ 추가 -->
    <div class="inline-input">
        <input type="text" id="newTodoInput" placeholder="할 일을 입력하세요" />
        <input type="text" id="newContentInput" placeholder="상세 내용을 입력하세요" />
        <button onclick="addTodo()">추가</button>
    </div>

    <!-- ✅ 테이블 구조 -->
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
                    <td class="title <c:if test='${todo.completed}'>completed</c:if>">
                        <c:out value="${todo.title}" />
                    </td>
                    <td><small><c:out value="${todo.content}" /></small></td>
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
                            data-content="${fn:escapeXml(todo.content)}"
                            data-priority="${todo.priority}">
                            수정
                        </button>
                        <button onclick="deleteTodo(${todo.todoId})">삭제</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	
    <div class="nav-links">
	    <a href="<c:url value='/mypage' />">마이페이지로 돌아가기</a>
	    <a href="<c:url value='/' />">홈으로 돌아가기</a>
	</div>
</div>

<!-- ✅ JavaScript (모든 $ → \$() 처리됨) -->
<script>
    const addTodoUrl = '<c:out value="${addTodoUrl}" />';
    const deleteTodoUrl = '<c:out value="${deleteTodoUrl}" />';
    const completeTodoUrl = '<c:out value="${completeTodoUrl}" />';
    const updateTodoUrl = '<c:out value="${updateTodoUrl}" />';
    const filterTodoUrl = '<c:out value="${filterTodoUrl}" />';

    \$('input[name="completed"]').on('change', function () {
        const val = \$(this).val();
        \$.get(filterTodoUrl, { completed: val }, function (data) {
            const tbody = \$('#todoListContainer tbody').empty();
            data.forEach(todo => {
                const cls = todo.completed ? 'completed' : '';
                const statusText = todo.completed ? '완료됨' : '미완료';
                const statusClass = todo.completed ? 'completed' : 'not-completed';

                const escapedTitle = $('<div>').text(todo.title).html();
                const escapedContent = $('<div>').text(todo.content).html();

                const row =
                    '<tr id="todo-' + todo.todoId + '">' +
                    '<td><input type="checkbox" onclick="toggleComplete(' + todo.todoId + ')" ' + (todo.completed ? 'checked' : '') + ' /></td>' +
                    '<td class="title ' + cls + '">' + escapedTitle + '</td>' +
                    '<td><small>' + escapedContent + '</small></td>' +
                    '<td class="todo-status ' + statusClass + '">' + statusText + '</td>' +
                    '<td>' +
                    '<button class="edit-btn" ' +
                    'data-id="' + todo.todoId + '" ' +
                    'data-title="' + todo.title + '" ' +
                    'data-content="' + todo.content + '">수정</button> ' +
                    '<button onclick="deleteTodo(' + todo.todoId + ')">삭제</button>' +
                    '</td>' +
                    '</tr>';

                tbody.append(row);
            });
        });
    });

    function addTodo() {
        const title = \$('#newTodoInput').val().trim();
        const content = \$('#newContentInput').val().trim();
        if (!title) return alert("제목을 입력해주세요");
        if (!content) return alert("내용을 입력해주세요");

        \$.ajax({
            url: addTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ title: title, content: content }),
            success: function (data) {
                if (data.success) location.reload();
            }
        });
    }

    function toggleComplete(todoId) {
        \$.post(completeTodoUrl, { todoId: todoId }, function (res) {
            if (res.success) \$('input[name="completed"]:checked').trigger('change');
        });
    }

    function deleteTodo(todoId) {
        if (!confirm("삭제하시겠습니까?")) return;
        \$.post(deleteTodoUrl, { todoId: todoId }, function (res) {
            if (res.success) location.reload();
            else alert("삭제 실패: " + res.error);
        });
    }

    \$(document).on('click', '.edit-btn', function () {
        const todoId = \$(this).data('id');
        const title = \$(this).data('title');
        const content = \$(this).data('content');
        showEdit(todoId, title, content);
    });

    function showEdit(todoId, currentTitle, currentContent) {
        const cleanTitle = \$('<div>').html(currentTitle).text();
        const cleanContent = \$('<div>').html(currentContent).text();

        const \$inputTitle = \$('<input>', {
            type: 'text',
            id: 'edit-title-' + todoId,
            value: cleanTitle,
            placeholder: '제목'
        });

        const \$inputContent = \$('<input>', {
            type: 'text',
            id: 'edit-content-' + todoId,
            value: cleanContent,
            placeholder: '내용'
        });

        const \$button = \$('<button>')
            .text('저장')
            .attr('onclick', 'submitEdit(' + todoId + ')');

        const \$td = \$('<td colspan="5">').append(\$inputTitle, \$inputContent, \$button);
        const \$tr = \$('<tr id="todo-' + todoId + '">').append(\$td);

        \$('#todo-' + todoId).replaceWith(\$tr);
    }

    function submitEdit(todoId) {
        const newTitle = \$(`#edit-title-\${todoId}`).val();
        const newContent = \$(`#edit-content-\${todoId}`).val();

        \$.ajax({
            url: updateTodoUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                todoId: todoId,
                title: newTitle,
                content: newContent
            }),
            success: function (data) {
                if (data.success) {
                    \$('input[name="completed"]:checked').trigger('change');
                } else {
                    alert("수정 실패");
                }
            }
        });
    }
</script>

</body>
</html>
