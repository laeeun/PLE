<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>신고하기</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />">
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background-color: #fff0f5;
            padding: 20px;
        }
        .form-label { font-weight: bold; }
        textarea {
            resize: none;
            height: 150px;
        }
    </style>
</head>
<body>
    <h4>🚨 신고하기</h4>
    <form id="reportForm">
        <input type="hidden" id="target_id" name="target_id" value="${param.target_id}" />
        <input type="hidden" id="target_type" name="target_type" value="${param.target_type}" />
        <input type="hidden" id="target_ref_id" name="target_ref_id" value="${param.target_ref_id}" />

        <div class="mb-3">
            <label for="reason" class="form-label">신고 사유</label>
            <textarea id="reason" name="reason" class="form-control" placeholder="신고 사유를 구체적으로 작성해주세요."></textarea>
        </div>

        <div class="text-end">
            <button type="button" class="btn btn-danger" onclick="submitReport()">신고 제출</button>
        </div>
    </form>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    function submitReport() {
        const reason = $("#reason").val().trim();
        if (!reason) {
            alert("신고 사유를 입력해주세요.");
            return;
        }

        $.ajax({
            url: "<c:url value='/report/add' />",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                target_id: $("#target_id").val(),
                target_type: $("#target_type").val(),
                target_ref_id: $("#target_ref_id").val(),
                reason: reason
            }),
            success: function (res) {
                if (res.success) {
                    alert("신고가 정상적으로 접수되었습니다.");
                    window.close();
                } else {
                    alert(res.message);
                }
            },
            error: function () {
                alert("신고 처리 중 오류가 발생했습니다.");
            }
        });
    }
    </script>
</body>
</html>
