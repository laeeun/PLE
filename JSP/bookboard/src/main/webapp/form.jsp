<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
   <form name="newWrite" action="create"  method="post" >
       <div class="mb-3 row">
           <label class="col-sm-2 control-label" >성명</label>
           <div class="col-sm-3">
               <input name="name" type="text" class="form-control"  placeholder="name">
           </div>
       </div>
       <div class="mb-3 row">
           <label class="col-sm-2 control-label" >제목</label>
           <div class="col-sm-5">
               <input name="subject" type="text" class="form-control"  placeholder="subject">
           </div>
       </div>
       <div class="mb-3 row">
           <label class="col-sm-2 control-label" >내용</label>
           <div class="col-sm-8">
               <textarea name="content" cols="50" rows="5" class="form-control"placeholder="content"></textarea>
           </div>
       </div>
       <div class="mb-3 row">
           <div class="col-sm-offset-2 col-sm-10 ">
            <input type="submit" class="btn btn-primary " value="등록 ">             
           <input type="reset" class="btn btn-primary " value="취소 ">
           </div>
       </div>
   </form>
</body>
</html>