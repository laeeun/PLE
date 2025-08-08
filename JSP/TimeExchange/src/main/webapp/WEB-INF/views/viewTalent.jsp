<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>${talent.title}-상세보기</title>
<link
   href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css"
   rel="stylesheet">
<link href="<c:url value='/resources/css/bootstrap.min.css' />"
   rel="stylesheet">
<link rel="stylesheet"
   href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<style>
	:root {
	   --primary: #1F2C40;
	   --accent: #FF6B35;
	   --accent-light: #FFEDE3;
	   --surface: #F9F9F9;
	   --surface-alt: #FFFFFF;
	   --text-main: #1F2C40;
	   --text-sub: #6A737D;
	   --border: #E8E8E8;
	}
	
	body {
	   font-family: 'Pretendard', sans-serif;
	   background: var(--surface);
	   color: var(--text-main);
	}
	
	h3, h4 {
	   color: var(--primary);
	   font-weight: 700;
	}
	
	.card {
	   background-color: var(--surface-alt);
	   border: 1px solid var(--border);
	   border-radius: 16px;
	   box-shadow: 0 6px 20px rgba(0, 0, 0, 0.03);
	}
	
	.card-header {
	   background-color: var(--accent);
	   color: white;
	   border-top-left-radius: 16px;
	   border-top-right-radius: 16px;
	}
	
	.talent-header {
	   background: linear-gradient(135deg, #ffb066, #ff6b35);
	   padding: 20px 24px;
	   border-top-left-radius: 16px;
	   border-top-right-radius: 16px;
	   box-shadow: inset 0 -1px 0 rgba(255, 255, 255, 0.3);
	}
	
	.talent-header h3 {
	   font-size: 1.6rem;
	   font-weight: 700;
	   color: #fff;
	}
	
	.profile-img-box img {
	   object-fit: cover;
	   border: 2px solid var(--border);
	   width: 80px;
	   height: 80px;
	   border-radius: 50%;
	}
	
	.profile-info a {
	   font-size: 1.1rem;
	   font-weight: 600;
	   color: var(--text-main);
	   text-decoration: none;
	}
	
	.profile-info a:hover {
	   color: var(--accent);
	}
	
	.text-muted.small {
	   font-size: 0.85rem;
	}
	.profile-container {
	  display: grid;
	  grid-template-columns: auto 1fr;
	  gap: 1rem;
	  align-items: center;
	}
	.profile-card {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 2rem;
  background: linear-gradient(135deg, var(--surface-alt), #fff);
  padding: 1.5rem 2rem;
  border-radius: 20px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  border: 1px solid var(--border);
  width: 80%;
  margin: 0 auto 2rem auto;
  transition: box-shadow 0.3s ease;
}
	
	.profile-card .profile-img-box img {
	  width: 80px;
	  height: 80px;
	  align-items: center;
	  object-fit: cover;
	  border-radius: 50%;
	  border: 2px solid var(--border);
	}
	
	.profile-card .profile-meta {
	  display: flex;
	  flex-direction: column;
	  justify-content: center;
	  gap: 0.4rem;
	}
	
	.profile-card .profile-meta .writer a {
	  font-size: 1.05rem;
	  font-weight: 600;
	  color: var(--text-main);
	  text-decoration: none;
	}
	.profile-card .profile-meta .writer a:hover {
	  color: var(--accent);
	}
	
	.profile-card .profile-meta span {
	  font-size: 0.95rem;
	  color: var(--text-sub);
	}
	hr {
	  border: none;
	  height: 3px;
	  background-color: var(--primary); /* 밝은 오렌지 */
	  border-radius: 4px;
	  margin: 2rem 0;
	}
	.comment-submit-btn {
	  background-color: var(--accent);
	  color: #fff;
	  border: none;
	  padding: 8px 16px;
	  border-radius: 8px;
	  transition: background-color 0.2s ease;
	  font-weight: 500;
	}
	.comment-submit-btn:hover {
	  background-color: #e85c28;
	}
	.preview-image {
	    max-width: 100%;
	    height: auto;
	    margin-top: 10px;
	    border-radius: 10px;
	    border: 1px solid var(--border);
	    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
	}
	</style>
</head>
<body>
   <jsp:include page="/WEB-INF/views/nav.jsp" />

   <div class="container mt-5">
      <div class="card">
         <div class="card-header talent-header">
            <div
               class="d-flex align-items-center justify-content-between flex-wrap">
               <h3 class="mb-0 text-white">${talent.title}</h3>
            </div>
         </div>

         <div class="card-body">
            <!-- 🆕 메타 + 프로필 한 줄 -->
            <div class="row mb-3 g-3">
			   <div class="col-12">
			      <div class="d-flex justify-content-center">
			         <div class="profile-card">
			            <!-- 프로필 이미지 -->
			            <div class="profile-img-box">
			               <c:choose>
			                  <c:when test="${not empty author.profileImage && author.profileImage ne 'default-profile.png'}">
			                     <img src="<c:url value='/upload/profile/${author.profileImage}' />" alt="프로필 이미지" />
			                  </c:when>
			                  <c:otherwise>
			                     <img src="<c:url value='/resources/images/default-profile.png' />" alt="기본 프로필" />
			                  </c:otherwise>
			               </c:choose>
			            </div>
			
			            <!-- 프로필 정보 + 메타 -->
			            <div class="profile-meta">
			               <div class="writer">
			                   작성자:
			                  <c:choose>
			                     <c:when test="${talent.member_id == sessionScope.loggedInUser.member_id}">
			                        <a href="<c:url value='/mypage' />">${talent.username}</a>
			                     </c:when>
			                     <c:otherwise>
			                        <a href="<c:url value='/profile/${talent.member_id}' />">${talent.username}</a>
			                     </c:otherwise>
			                  </c:choose>
			               </div>
			
			               <span><i class="fa-solid fa-folder me-1 text-muted"></i> 카테고리: ${talent.category}</span>
			               <span><i class="fa-regular fa-clock me-1 text-muted"></i> 판매시간: ${talent.timeSlotDisplay}</span>
			               <span><i class="fa-regular fa-calendar-days me-1 text-muted"></i> 등록일: 
			                  <fmt:formatDate value="${createdDate}" pattern="yyyy-MM-dd HH:mm" />
			               </span>
			            </div>
			         </div>
			      </div>
			   </div>
			</div>

            <!-- ✅ 첨부파일 영역 카드 스타일 -->
           <c:if test="${not empty talent.filename}">
			    <c:set var="fileExtension" value="${fn:toLowerCase(fn:substringAfter(talent.filename, '.'))}" />
			    <div class="alert alert-light border mb-3">
			        📎 <strong>첨부파일:</strong>
			        <a href="<c:url value='/resources/uploads/${talent.filename}' />"
			           download class="ms-2 text-decoration-underline">
			            ${talent.filename}
			        </a>
			
			        <!-- ✅ 이미지 미리보기 추가 -->
			        <c:if test="${fileExtension == 'jpg' || fileExtension == 'jpeg' || fileExtension == 'png' || fileExtension == 'gif' || fileExtension == 'webp'}">
			            <div class="mt-3">
			                <img src="<c:url value='/resources/uploads/${talent.filename}' />"
			                     alt="첨부 이미지 미리보기"
			                     style="max-width: 100%; height: auto; border-radius: 10px; border: 1px solid var(--border); box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);" />
			            </div>
			        </c:if>
			    </div>
			</c:if>

            <!-- ✅ 설명 박스 -->
            <div class="card mb-4 shadow-sm border-0" style="background-color: var(--surface-alt); border-radius: 16px;">
			   <div class="card-body">
			      <div class="d-flex align-items-center mb-3">
				   <i class="fa-solid fa-circle-info fa-lg" style="color: var(--accent); margin-right: 1rem;"></i>
				   <h5 class="mb-0" style="color: var(--primary); font-weight: 700;">서비스 설명</h5>
				  </div>
			      <p class="text-muted" style="line-height: 1.7; white-space: pre-line;">${talent.description}</p>
			   </div>
			</div>

            <!-- ⭐ 리뷰 정보 추가 -->
            <hr>

            <div class="box mb-4">
               <h5>
                  <i class="fa-solid fa-star"></i> 리뷰
               </h5>

               <p>
                  <strong>총 리뷰:</strong> ${reviewCount}개
               </p>

               <p>
                  <strong>평균 평점:</strong> <span> <c:set var="intPart"
                        value="${fn:substringBefore(averageRating, '.')}" /> <c:set
                        var="floatPart" value="${fn:substringAfter(averageRating, '.')}" />
                     <!-- 정수 부분: 꽉 찬 별 출력 --> <c:forEach var="i" begin="1"
                        end="${intPart}">
                        <i class="fa-solid fa-star text-warning"></i>
                     </c:forEach> <!-- 소수점이 5 이상이면 반 별 하나 추가 --> <c:if test="${floatPart >= 5}">
                        <i class="fa-solid fa-star-half-stroke text-warning"></i>
                     </c:if> <!-- 빈 별 채우기 --> <c:forEach var="j" begin="1"
                        end="${5 - intPart - (floatPart >= 5 ? 1 : 0)}">
                        <i class="fa-regular fa-star text-warning"></i>
                     </c:forEach>
                  </span> 
	                  (
	                  <fmt:formatNumber value="${averageRating}" pattern="#.0" />
	                  점)
               </p>

               <a href="<c:url value='/review/talent?id=${talent.talent_id}' />"
                  class="btn btn-outline-secondary mt-2"><i
                  class="fa-solid fa-comments"></i> 리뷰 전체 보기</a>
            </div>
			<hr>
            <div class="card mt-5">
               <div class="card-header d-flex align-items-center bg-light">
                  <h5 class="mb-0" style="color: var(--primary); font-weight: 700;">
				   <i class="fa-regular fa-comments me-2" style="color: var(--accent);"></i> 댓글
				</h5>
               </div>
			
               <div class="card-body">
                  <!-- 댓글 리스트 -->
                  <div id="commentList" class="mb-3"></div>

                  <!-- 댓글 작성 영역 -->
                  <c:if test="${sessionScope.loggedInUser != null}">
                     <div class="input-group mb-3 align-items-center">
                        <input type="text" id="commentInput" class="form-control"
                           placeholder="댓글을 입력하세요"
                           style="max-width: 100%; margin-right: 12px;" />
                        <button id="addCommentBtn" class="btn comment-submit-btn">등록</button>
                     </div>
                  </c:if>

                  <!-- 로그인 유도 메시지 -->
                  <c:if test="${sessionScope.loggedInUser == null}">
                     <div class="alert alert-light border d-flex align-items-center"
                        style="background-color: var(--surface-alt);">
                        <i class="fa-solid fa-circle-info text-muted me-2"></i>
                        <div class="text-muted">
                           댓글을 작성하려면 <a href="<c:url value='/login' />"
                              class="text-decoration-underline">로그인</a>이 필요합니다.
                        </div>
                     </div>
                  </c:if>
               </div>
            </div>

            <div
               class="card-footer d-flex flex-wrap justify-content-between align-items-center gap-2 mt-5">
               <a href="<c:url value='/talent' />"
                  class="btn btn-outline-secondary"> <i
                  class="fa-solid fa-arrow-left"></i> 목록으로
               </a>

               <div class="d-flex flex-wrap gap-4">
                  <!-- 찜하기 -->
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
                  <!-- 수정/삭제 or 신고 -->
                  <c:if
                     test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id == talent.member_id}">
                     <a href="<c:url value='/talent/update?id=${talent.talent_id}' />"
                        class="btn btn-warning"> <i
                        class="fa-solid fa-pen-to-square"></i> 수정
                     </a>
                     <a href="<c:url value='/talent/delete?id=${talent.talent_id}' />"
                        class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?')"><i
                        class="fa-solid fa-trash"></i> 삭제</a>
                  </c:if>
                  <c:if
                     test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id != talent.member_id}">
                     <button class="btn btn-danger"
                        onclick="openReportPopup('talent', '${talent.talent_id}', '${talent.member_id}')">
                        <i class="fa-solid fa-triangle-exclamation"></i> 신고하기
                     </button>
                  </c:if>


                  <!-- 구매하기 -->
                  <c:if
                     test="${sessionScope.loggedInUser != null && sessionScope.loggedInUser.member_id != talent.member_id}">
                     <a href="<c:url value='/purchase?id=${talent.talent_id}' />"
                        class="btn btn-primary"><i class="fa-solid fa-coins"></i>
                        구매하기</a>
                  </c:if>
                  <c:if test="${sessionScope.loggedInUser == null}">
                     <a href="javascript:void(0);" class="btn btn-primary"
                        onclick="alert('로그인 후 사용 가능한 기능입니다.'); location.href='<c:url value="/login" />';">💰
                        구매하기</a>
                  </c:if>
               </div>
            </div>
         </div>
      </div>

   </div>
   <jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
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
                        // 댓글 신고 버튼 추가 (본인이 아닌 경우에만)
                        html += `
                            <button class="btn btn-sm btn-outline-warning report-btn"
                                  onclick="openReportPopup('comment', '\${comment.commentId}', '\${comment.writerId}')"
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
