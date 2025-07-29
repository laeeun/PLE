<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${talent.title} - 상세보기</title>
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background: linear-gradient(to right, #ffe6f0, #fff0f5);
        }
        h3 { color: #ff69b4; }
        .card {
            border: 1px solid #ffcce0;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(255, 105, 180, 0.1);
        }
        .btn-primary { background-color: #ff99cc; border-color: #ff99cc; }
        .btn-primary:hover { background-color: #ff66b2; border-color: #ff66b2; }
        .btn-outline-secondary:hover { background-color: #ffcce0; color: black; }
        .comment-item {
            border: 1px solid #ffd6e6;
            border-radius: 8px;
            background-color: #fff;
            padding: 12px 16px 16px;
            margin-bottom: 10px;
            box-shadow: 0 2px 6px rgba(255, 105, 180, 0.1);
            position: relative;
        }
        .comment-item .comment-content {
            margin: 6px 0;
            padding-right: 100px;
        }
        .edit-btn, .delete-btn {
            position: absolute;
            top: 12px;
            font-size: 0.85rem;
            padding: 3px 8px;
            height: 30px;
            line-height: 1;
        }
        .edit-btn { right: 60px; }
        .delete-btn { right: 10px; color: red; border-color: red; }
    </style>
</head>

<body>
<jsp:include page="/WEB-INF/views/nav.jsp" />
<div class="container mt-5">
    <div class="card">
        <div class="card-header" style="background-color: #ff99cc;">
            <h3 class="text-white">${talent.title}</h3>
        </div>
        <div class="card-body">
            <!--  <p><strong>🧑‍💻 작성자:</strong>
			    <c:choose>
			        <c:when test="${talent.member_id == sessionScope.loggedInUser.member_id}">
			            <a href="<c:url value='/mypage' />">${talent.username}</a>
			        </c:when>
			        <c:otherwise>
			            <a href="<c:url value='/profile/${talent.member_id}' />">${talent.username}</a>
			        </c:otherwise>
			    </c:choose>
			</p> -->
			<p><strong>🧑‍💻 작성자:</strong>
                <a href="<c:url value='/profile/${talent.member_id}' />">${talent.username}</a>
            <p><strong>📂 카테고리:</strong> ${talent.category}</p>
            <p><strong>🕒 판매 시간:</strong> ${talent.timeSlotDisplay}</p>
            <p><strong>🗓️ 등록일:</strong>
                <fmt:formatDate value="${createdDate}" pattern="yyyy-MM-dd HH:mm" />
            </p>
			<c:if test="${not empty talent.filename}">
			    <div class="mb-3">
			        <label class="form-label">첨부 파일</label><br/>
			        <a href="<c:url value='/resources/uploads/${talent.filename}' />" download>
			            ${talent.filename}
			        </a>
			    </div>
			</c:if>
            <hr>
            <p><strong>📌 설명:</strong></p>
            <p>${talent.description}</p>

            <!-- ⭐ 리뷰 정보 추가 -->
            <hr>
            <p><strong>⭐ 리뷰 개수:</strong> ${reviewCount}개</p>
            <p>
             <strong>⭐ 평균 평점:</strong>
             <span>
                 <c:set var="intPart" value="${fn:substringBefore(averageRating, '.')}" />
                 <c:set var="floatPart" value="${fn:substringAfter(averageRating, '.')}" />
         
                 <!-- 정수 부분: 꽉 찬 별 출력 -->
                 <c:forEach var="i" begin="1" end="${intPart}">
                     ⭐
                 </c:forEach>
         
                 <!-- 소수점이 5 이상이면 반 별 하나 추가 -->
                 <c:if test="${floatPart >= 5}">
                     🌗
                 </c:if>
         
                 <!-- 빈 별 채우기 -->
                 <c:forEach var="j" begin="1" end="${5 - intPart - (floatPart >= 5 ? 1 : 0)}">
                     ☆
                 </c:forEach>
             </span>
         
             (<fmt:formatNumber value="${averageRating}" pattern="#.0" />점)
         </p>
            <a href="<c:url value='/review/myreviews'/>?id=${dto.review_id}' />"
               class="btn btn-outline-secondary mt-2">📝 리뷰 전체 보기</a>
        </div>

        <div class="card-footer d-flex justify-content-between align-items-center">
            <div>
                <a href="<c:url value='/talent' />" class="btn btn-outline-secondary">← 목록으로</a>
            </div>
            <div>
                <c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id != talent.member_id}">
                    <c:choose>
                        <c:when test="${isFavorite}">
                            <button id="favoriteBtn" class="btn btn-danger">
                                <i id="heartIcon" class="fa-solid fa-heart" style="color: red;"></i> 
                                <span id="favoriteText">찜 완료</span>
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button id="favoriteBtn" class="btn btn-outline-danger">
                                <i id="heartIcon" class="fa-regular fa-heart"></i> 
                                <span id="favoriteText">찜하기</span>
                            </button>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id == talent.member_id}">
                    <a href="<c:url value='/talent/update?id=${talent.talent_id}' />" class="btn btn-warning">✏️ 수정</a>
                    <a href="<c:url value='/talent/delete?id=${talent.talent_id}' />"
                       class="btn btn-danger"
                       onclick="return confirm('정말 삭제하시겠습니까?')">🗑️ 삭제</a>
                </c:if>
				<c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id != talent.member_id}">
				    <button class="btn btn-danger" 
				            onclick="openReportPopup('talent', '${talent.talent_id}', '${talent.member_id}')">
				        🚨 신고하기
				    </button>
				</c:if>
				
				<!-- 테스트용 버튼 -->
				<button class="btn btn-danger" 
			            onclick="openReportPopup('talent', '${talent.talent_id}', '${talent.member_id}')">
			        🚨 신고하기
			    </button>
				
                <c:if test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id != talent.member_id}">
                    <a href="<c:url value='/purchase?id=${talent.talent_id}' />" class="btn btn-primary">💰 구매하기</a>
                </c:if>
                <c:if test="${sessionScope.loggedInUser == null}">
                    <a href="javascript:void(0);" class="btn btn-primary"
                       onclick="alert('로그인 후 사용 가능한 기능입니다.'); location.href='<c:url value="/login" />';">💰 구매하기</a>
                </c:if>
            </div>
        </div>
    </div>

    <!-- 💬 댓글 영역 -->
    <div class="container mt-4">
        <h4>💬 댓글</h4>
        <div id="commentList" class="mb-3"></div>
        
        <c:if test="${sessionScope.loggedInUser != null}">
            <div class="input-group mb-4">
                <input type="text" id="commentInput" class="form-control" placeholder="댓글을 입력하세요">
                <button id="addCommentBtn" class="btn btn-primary">등록</button>
            </div>
        </c:if>
        <c:if test="${sessionScope.loggedInUser == null}">
            <p class="text-muted">✋ 댓글을 작성하려면 <a href="<c:url value='/login' />">로그인</a>이 필요합니다.</p>
        </c:if>
    </div>
</div>

<jsp:include page="/WEB-INF/views/footer.jsp" />

<!-- 💻 JS 스크립트 -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>

function openReportPopup(targetType, targetRefId, targetId) {
	const reportPopupBaseUrl = "<c:url value='/report/popup' />";	
    const url = reportPopupBaseUrl + "?target_id=" + targetId + "&target_type=" + targetType + "&target_ref_id=" + targetRefId;
    window.open(url, "신고하기", "width=500,height=400,scrollbars=yes");
}


$(document).ready(function () {
    const talentId = ${talent.talent_id};
    const rawUserId = "${sessionScope.loggedInUser != null ? sessionScope.loggedInUser.member_id : ''}";
	
    const commentListUrl = "<c:url value='/comment/list' />";
    const addCommentUrl = "<c:url value='/comment/add' />";
    const deleteCommentUrl = "<c:url value='/comment/delete' />";
    const updateCommentUrl = "<c:url value='/comment/update' />";
	
    
    
    
    
    function loadComments() {
        $.ajax({
            url: commentListUrl,
            method: "GET",
            data: { talentId: talentId },
            success: function (data) {
            	console.log(data)
                const commentList = $("#commentList");
                commentList.empty();
                if (data.length === 0) {
                    commentList.append("<p class='text-muted'>아직 댓글이 없습니다.</p>");
                    return;
                }
                data.forEach(function (comment) {
                    console.log("commentId:", comment.commentId);  // 잘 찍힘
                    const arr = comment.createdAt;
                    const dateStr = new Date(arr[0], arr[1] - 1, arr[2], arr[3], arr[4], arr[5]).toLocaleString('ko-KR');
                    console.log("username:", comment.username);
                    console.log("content:", comment.content);
                    console.log("createdAt:", comment.createdAt);	
                    let html = `
                        <div class="comment-item" data-id="\${comment.commentId}">
                            <strong>\${comment.username}</strong>
                            <small class="text-muted">\${dateStr}</small>
                            <p class="comment-content">\${comment.content}</p>
                    `;

                    if (comment.writerId === rawUserId) {
                        html += `
                            <button class="btn btn-sm btn-outline-secondary edit-btn">수정</button>
                            <button class="btn btn-sm btn-outline-danger delete-btn">삭제</button>
                            
                        `;
                    }else {
                        // ✅ 댓글 신고 버튼 추가 (본인이 아닌 경우에만)
                        html += `
                            <button class="btn btn-sm btn-outline-warning report-btn"
                                    data-writer="\${comment.writerId}"
                                    data-id="\${comment.commentId}">
                                🚨 신고
                            </button>
                        `;
                    }
					              
                    html += "</div>";

                    console.log("🧪 HTML:", html);
                    $("#commentList").append(html);
                });
            }
        });
    }

    loadComments();

    $("#addCommentBtn").click(function () {
        const content = $("#commentInput").val().trim();
        if (!content) return alert("댓글을 입력하세요.");
        $.ajax({
            url: addCommentUrl,
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                talentId: talentId,
                writerId: rawUserId,
                content: content
            }),
            success: function (data) {
                if (data.status === "success") {
                    $("#commentInput").val("");
                    loadComments();
                } else {
                    alert(data.message);
                }
            }
        });
    });

    $("#commentList").on("click", ".delete-btn", function () {
        const commentId = $(this).closest("div[data-id]").data("id");
        if (confirm("댓글을 삭제하시겠습니까?")) {
            $.ajax({
                url: deleteCommentUrl,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ commentId: commentId }),
                success: function (data) {
                    if (data.status === "success") {
                        loadComments();
                    } else {
                        alert(data.message);
                    }
                }
            });
        }
    });
	
    $("#commentList").on("click", ".edit-btn", function () {
        const parent = $(this).closest("div[data-id]");
        const commentId = parent.data("id");
        const oldContent = parent.find(".comment-content").text();
        const newContent = prompt("댓글을 수정하세요:", oldContent);
        if (newContent !== null && newContent.trim() !== "" && newContent !== oldContent) {
            $.ajax({
                url: updateCommentUrl,
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify({ commentId: commentId, content: newContent }),
                success: function (data) {
                    if (data.status === "success") {
                        loadComments();
                    } else {
                        alert(data.message);
                    }
                }
            });
        }
    });
});

const toggleFavoriteUrl = "<c:url value='/favorite/toggle' />";
const userId = "${sessionScope.loggedInUser != null ? sessionScope.loggedInUser.member_id : ''}";
const talentId = "${talent.talent_id != null ? talent.talent_id : ''}";
$("#favoriteBtn").click(function () {
	console.log("이벤트리스너진입, talentId:", talentId);
    $.ajax({
        url: toggleFavoriteUrl,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ talentId: talentId }),
        success: function (data) {
            const heartIcon = $("#heartIcon");
            const text = $("#favoriteText");
            const btn = $("#favoriteBtn");

            if (data.status === "added") {
                heartIcon.removeClass("fa-regular").addClass("fa-solid").css("color", "red");
                text.text("찜 완료");
                btn.removeClass("btn-outline-danger").addClass("btn-danger");
            } else if (data.status === "removed") {
                heartIcon.removeClass("fa-solid").addClass("fa-regular").css("color", "");
                text.text("찜하기");
                btn.removeClass("btn-danger").addClass("btn-outline-danger");
            }
        },
        error: function () {
            alert("찜 처리 중 오류가 발생했습니다.");
        }
    });
});


</script>
</body>
</html>
