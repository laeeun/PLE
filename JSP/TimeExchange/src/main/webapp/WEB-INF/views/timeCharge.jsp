<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>시간 충전소</title>
    <!-- 기존 부트스트랩 사용 그대로 -->
    <link href="<c:url value='/resources/css/bootstrap.min.css' />" rel="stylesheet">
    <!-- Pretendard 폰트 -->
    <link href="https://cdn.jsdelivr.net/npm/pretendard@1.3.8/dist/web/static/pretendard.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        /* ===========================
           TimeFair - 시간 충전소 프리미엄 테마 (CSS만 수정)
           =========================== */
        :root{
          --tf-primary:#7c6cff;
          --tf-primary-2:#a88bff;
          --tf-accent:#e7e1ff;
          --tf-bg-1:#f7f5ff;
          --tf-text:#231f2b;
          --tf-border:rgba(124,108,255,.35);
          --tf-shadow:0 12px 36px rgba(124,108,255,.18);
          --tf-glass:rgba(255,255,255,.66);
          --tf-white:#ffffff;
        }

        @media (prefers-color-scheme: dark){
          :root{
            --tf-bg-1:#0f0e13;
            --tf-text:#f5f4f8;
            --tf-glass:rgba(20,18,27,.55);
            --tf-white:#181625;
            --tf-border:rgba(168,139,255,.28);
            --tf-shadow:0 16px 40px rgba(168,139,255,.15);
          }
        }

        html,body{height:100%;}
        body{
          font-family:"Pretendard",-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Apple SD Gothic Neo","Noto Sans KR","Malgun Gothic",sans-serif;
          color:var(--tf-text);
          background:
            radial-gradient(1200px 600px at 10% -10%, #efeaff 0%, transparent 60%),
            radial-gradient(1000px 500px at 110% 0%, #f5f0ff 0%, transparent 55%),
            linear-gradient(180deg, var(--tf-bg-1), #ffffff);
          background-attachment:fixed;
          position:relative;
          overflow-x:hidden;
        }

        /* 떠다니는 그라데이션 오브젝트 (장식) */
        body::before, body::after{
          content:"";
          position:fixed;
          z-index:-1;
          width:560px; height:560px;
          border-radius:50%;
          filter:blur(50px);
          opacity:.55;
          pointer-events:none;
          transform:translate(-30%, -20%);
          background:radial-gradient(circle at 30% 30%, rgba(124,108,255,.55), transparent 60%),
                     radial-gradient(circle at 70% 70%, rgba(168,139,255,.45), transparent 60%);
          animation:floatXY 18s ease-in-out infinite;
        }
        body::after{
          right:-220px; bottom:-220px; left:auto; top:auto;
          transform:translate(30%, 20%);
          animation-duration:22s;
          background:radial-gradient(circle at 70% 30%, rgba(255,149,204,.32), transparent 60%),
                     radial-gradient(circle at 20% 70%, rgba(124,108,255,.38), transparent 60%);
        }
        @keyframes floatXY{
          0%{transform:translate(-30%,-20%) scale(1);}
          50%{transform:translate(-20%,-30%) scale(1.05);}
          100%{transform:translate(-30%,-20%) scale(1);}
        }

        /* 스크롤바 */
        *{scrollbar-width:thin; scrollbar-color:var(--tf-primary) transparent;}
        *::-webkit-scrollbar{width:10px;height:10px;}
        *::-webkit-scrollbar-thumb{background:var(--tf-primary);border-radius:999px;}
        *::-webkit-scrollbar-track{background:transparent;}

        .container.mt-5{max-width:780px;margin-top:64px !important;}

        /* 타이틀: 은은한 반짝 */
        .container.mt-5>h2{
          position:relative;
          font-weight:800;
          letter-spacing:-.2px;
          margin-bottom:18px;
          display:inline-flex;align-items:center;gap:10px;
          background:linear-gradient(90deg,var(--tf-primary),var(--tf-primary-2));
          -webkit-background-clip:text;background-clip:text;color:transparent;
        }
        .container.mt-5>h2::after{
          content:"";
          position:absolute;left:0;bottom:-8px;height:3px;width:100%;
          background:linear-gradient(90deg,rgba(124,108,255,.35),rgba(168,139,255,.0) 60%);
          border-radius:999px;
          mask:linear-gradient(90deg,transparent 0, #000 15%, #000 85%, transparent 100%);
        }
        .container.mt-5>h2::before{
          content:"";
          position:absolute;left:0;right:0;bottom:-8px;height:3px;
          background:linear-gradient(90deg,transparent, #fff, transparent);
          filter:blur(2px);opacity:.6;
          animation:shine 2.6s linear infinite;
        }
        @keyframes shine{
          0%{transform:translateX(-60%);}
          100%{transform:translateX(60%);}
        }

        /* 글래스 카드 공통: 이중 배경으로 그라데이션 보더 */
        .glass-card{
          border-radius:22px;
          padding:22px;
          background:
            linear-gradient(180deg, var(--tf-glass), var(--tf-glass)) padding-box,
            linear-gradient(135deg, rgba(124,108,255,.45), rgba(168,139,255,.30)) border-box;
          border:1px solid transparent;
          backdrop-filter:blur(10px);
          -webkit-backdrop-filter:blur(10px);
          box-shadow:var(--tf-shadow);
        }

        /* 현재 잔액 박스 */
        #account-info{
          display:flex;align-items:center;gap:12px;
          border-left:6px solid var(--tf-primary);
        }
        #account-info strong#currentAccount{
          font-size:1.12rem;padding:2px 10px;border-radius:10px;
          background:rgba(255,255,255,.55);
        }

        /* 메시지 영역 */
        #messageArea{margin-top:16px;}
        #messageArea .alert{
          margin:0;
          border-radius:18px;
          border:1px solid rgba(0,0,0,.06);
          animation:fadeUp .35s ease-out;
        }
        @keyframes fadeUp{
          from{opacity:0; transform:translateY(8px) scale(.99);}
          to{opacity:1; transform:translateY(0) scale(1);}
        }

        /* 충전 폼 */
        #chargeForm{margin-top:22px;}
        #chargeForm .form-label{font-weight:800;margin-bottom:10px;display:inline-block;}

        /* 칩 라디오: input 숨기고 label을 칩처럼 */
        #chargeForm .form-check{display:inline-block;margin:8px 10px 8px 0;}
        #chargeForm .form-check-input{
          position:absolute;opacity:0;pointer-events:none;
        }
        #chargeForm .form-check-label{
          position:relative;
          display:inline-flex;align-items:center;gap:10px;
          padding:12px 16px;border-radius:999px;
          background:var(--tf-white);
          border:1px solid rgba(0,0,0,.08);
          transition:transform .18s ease, box-shadow .18s ease, border-color .18s ease, background .18s ease;
          box-shadow:0 6px 14px rgba(0,0,0,.06);
          cursor:pointer;
        }
        /* 라벨 아이콘(시계) */
        #chargeForm .form-check-label::before{
          content:"⏱";
          font-size:1rem;line-height:1;
          opacity:.9;
        }
        /* Hover */
        #chargeForm .form-check-label:hover{
          transform:translateY(-1px);
          box-shadow:0 10px 22px rgba(0,0,0,.08);
          border-color:var(--tf-border);
        }
        /* 선택 상태 */
        #chargeForm .form-check-input:checked + .form-check-label{
          color:#fff;
          background:
            radial-gradient(120% 120% at 20% 20%, rgba(255,255,255,.28), transparent 50%),
            linear-gradient(135deg, var(--tf-primary), var(--tf-primary-2));
          border-color:transparent;
          box-shadow:0 14px 28px rgba(124,108,255,.28);
        }
        /* 체크 애니메이션 링 */
        #chargeForm .form-check-input:checked + .form-check-label::after{
          content:"";
          position:absolute;inset:0;border-radius:inherit;
          border:2px solid rgba(255,255,255,.55);
          opacity:0; transform:scale(.9);
          animation:ring .6s ease-out;
          pointer-events:none;
        }
        @keyframes ring{
          0%{opacity:.9; transform:scale(.9);}
          100%{opacity:0; transform:scale(1.12);}
        }

        /* 버튼 */
        .btn{border-radius:14px !important; padding:11px 18px; font-weight:700;
             transition:transform .15s ease, box-shadow .15s ease;}
        .btn-primary{
          position:relative; isolation:isolate;
          background-image:linear-gradient(135deg,var(--tf-primary),var(--tf-primary-2));
          border:0; box-shadow:0 12px 28px rgba(124,108,255,.28);
        }
        .btn-primary:hover,.btn-primary:focus{
          transform:translateY(-1px);
          box-shadow:0 16px 32px rgba(124,108,255,.34);
        }
        /* Ripple 효과 */
        .btn-primary:active::after{
          content:""; position:absolute; inset:0; border-radius:inherit;
          background:radial-gradient(circle at var(--x,50%) var(--y,50%), rgba(255,255,255,.55), transparent 40%);
          opacity:.9; animation:ripple .5s ease-out;
          pointer-events:none; z-index:-1;
        }
        @keyframes ripple{
          from{opacity:.9;}
          to{opacity:0;}
        }

        .btn-secondary{
          background:var(--tf-white) !important; color:var(--tf-primary) !important;
          border:1px solid var(--tf-border) !important;
          box-shadow:0 10px 22px rgba(0,0,0,.08);
        }
        .btn-secondary:hover,.btn-secondary:focus{
          transform:translateY(-1px);
          box-shadow:0 14px 26px rgba(0,0,0,.1);
        }
        #chargeForm .btn + .btn{margin-left:10px;}

        /* 알럿 톤 & 아이콘 */
        .alert{position:relative; padding-left:46px;}
        .alert::before{
          content:""; position:absolute; left:16px; top:50%; transform:translateY(-50%);
          width:22px;height:22px; border-radius:50%;
          background:#fff; box-shadow:0 4px 10px rgba(0,0,0,.07);
        }
        .alert-success{
          background:linear-gradient(180deg,#ecfff3,#ffffff) !important;
          border-color:rgba(16,185,129,.25) !important; color:#065f46 !important;
        }
        .alert-success::before{content:"✓"; display:flex; align-items:center; justify-content:center; font-weight:900; color:#10b981;}
        .alert-danger{
          background:linear-gradient(180deg,#fff1f1,#ffffff) !important;
          border-color:rgba(239,68,68,.25) !important; color:#7f1d1d !important;
        }
        .alert-danger::before{content:"!"; display:flex; align-items:center; justify-content:center; font-weight:900; color:#ef4444;}
        .alert-info{
          background:linear-gradient(180deg,#eef3ff,#ffffff) !important;
          border-color:rgba(59,130,246,.25) !important; color:#1e3a8a !important;
        }
        .alert-info::before{content:"i"; display:flex; align-items:center; justify-content:center; font-weight:900; color:#3b82f6;}

        /* 링크 */
        a{text-decoration:none;} a:hover{text-decoration:none;}

        /* 선택 영역 */
        ::selection{background:var(--tf-primary); color:#fff;}

        /* 접근성: 포커스 링 */
        :focus-visible{outline:3px solid rgba(124,108,255,.35); outline-offset:3px; border-radius:10px;}

        /* 반응형 */
        @media (max-width:576px){
          .container.mt-5{padding:0 14px;}
          #chargeForm .form-check-label{padding:10px 14px;}
        }

        /* 모션 최소화 */
        @media (prefers-reduced-motion: reduce){
          *{animation:none !important; transition:none !important;}
        }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/nav.jsp" />

<div class="container mt-5">
    <h2>⏱ 시간 충전소</h2>

    <!-- ✅ 현재 잔액 -->
    <div class="alert alert-info mt-3 glass-card" id="account-info">
        현재 잔액: <strong id="currentAccount">${sessionScope.loggedInUser.account}</strong> 분
    </div>

    <!-- ✅ 메시지 영역 -->
    <div id="messageArea"></div>

    <!-- ✅ 충전 폼 -->
    <form id="chargeForm" class="mt-4 glass-card">
        <div class="mb-3">
            <label class="form-label">충전할 시간 선택 (30분 단위)</label><br>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge30" value="30" required>
                <label class="form-check-label" for="charge30">30분</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge60" value="60">
                <label class="form-check-label" for="charge60">1시간</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge90" value="90">
                <label class="form-check-label" for="charge90">1시간 30분</label>
            </div>

            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="amount" id="charge120" value="120">
                <label class="form-check-label" for="charge120">2시간</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">충전하기</button>
        <a href="<c:url value='/' />" class="btn btn-secondary">홈으로</a>
    </form>
</div>

<jsp:include page="/WEB-INF/views/floatingButtons.jsp" />
<jsp:include page="/WEB-INF/views/footer.jsp" />

<script>
  // 버튼 립플 위치 계산용(순수 CSS로도 가능하지만 위치 자연스럽게)
  document.addEventListener('pointerdown', function(e){
    if(e.target.classList && e.target.classList.contains('btn-primary')){
      const rect = e.target.getBoundingClientRect();
      const x = ((e.clientX - rect.left)/rect.width)*100;
      const y = ((e.clientY - rect.top)/rect.height)*100;
      e.target.style.setProperty('--x', x + '%');
      e.target.style.setProperty('--y', y + '%');
    }
  });

  $("#chargeForm").submit(function (e) {
    e.preventDefault(); // 폼 기본 제출 막기

    const amount = $("input[name='amount']:checked").val();
    if (!amount) {
      alert("충전할 시간을 선택해주세요.");
      return;
    }

    $.ajax({
      type: "POST",
      url: "<c:url value='/charge/ajax' />",
      data: { amount: amount },
      success: function (response) {
        // 메시지 출력
        if (response.success) {
          $("#messageArea").html(`<div class="alert alert-success glass-card">\${response.message}</div>`);
          $("#currentAccount").text(response.newAccount);
        } else {
          $("#messageArea").html(`<div class="alert alert-danger glass-card">\${response.message}</div>`);
        }
        // 스크롤 유도(모바일)
        const msg = document.getElementById('messageArea');
        if(msg) msg.scrollIntoView({behavior:'smooth', block:'center'});
      },
      error: function () {
        $("#messageArea").html(`<div class="alert alert-danger glass-card">충전 중 오류가 발생했습니다.</div>`);
      }
    });
  });
</script>

</body>
</html>
