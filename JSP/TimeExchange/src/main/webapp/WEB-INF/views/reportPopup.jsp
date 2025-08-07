<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>신고하기</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />">

    <style>
        :root {
            --primary: #1F2C40;
            --accent: #FF6B35;
            --accent-light: #FFEDE4;
            --surface: #F9F9F9;
            --surface-alt: #FFFFFF;
            --border: #E8E8E8;
            --text-main: #1F2C40;
            --text-sub: #6A737D;
        }

        body {
            font-family: 'Pretendard', sans-serif;
            background: var(--surface);
            color: var(--text-main);
            margin: 0;
            padding: 40px 20px;
        }

        .report-container {
            max-width: 600px;
            margin: 0 auto;
            background: var(--surface-alt);
            border: 1px solid var(--border);
            border-radius: 16px;
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.04);
            padding: 32px;
        }

        h4 {
            font-size: 22px;
            font-weight: bold;
            text-align: center;
            color: var(--primary);
            margin-bottom: 24px;
            position: relative;
        }

        h4::before {
            content: "\f071"; /* FontAwesome exclamation-triangle */
            font-family: "Font Awesome 6 Free";
            font-weight: 900;
            color: var(--accent);
            margin-right: 10px;
        }

        .form-label {
            font-weight: 600;
            color: var(--text-main);
        }

        textarea {
            resize: none;
            height: 150px;
            border-radius: 10px;
            border: 1px solid var(--border);
            padding: 12px;
            font-size: 14px;
            color: var(--text-main);
        }

        .btn-danger {
            background-color: var(--accent);
            border: none;
        }

        .btn-danger:hover {
            background-color: #e85c26;
        }
    </style>
</head>
<body>
    <div class="report-container">
        <h4>신고하기</h4>

        <form id="reportForm">
            <input type="hidden" id="target_id" name="target_id" value="${param.target_id}" />
            <input type="hidden" id="target_type" name="target_type" value="${param.target_type}" />
            <input type="hidden" id="target_ref_id" name="target_ref_id" value="${param.target_ref_id}" />

            <div class="mb-3">
                <label for="reason" class="form-label">신고 사유</label>
                <textarea id="reason" name="reason" class="form-control" placeholder="신고 사유를 구체적으로 작성해주세요."></textarea>
            </div>

            <div class="text-end">
                <button type="button" class="btn btn-danger" onclick="submitReport()">
                    <i class="fa-solid fa-paper-plane"></i> 신고 제출
                </button>
            </div>
        </form>
    </div>

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
