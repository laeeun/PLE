<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="dto.BoardDTO"%>
<%@ page import="java.util.*"%>


<%
    int limit=5; 
    ArrayList allboard = (ArrayList)request.getAttribute("arr");
    int total_record = (int) request.getAttribute("total_record");
    int pageNum=(int) request.getAttribute("pageNum");
    int total_page=0;
    
    if (total_record % limit == 0){     
        total_page =total_record/limit;
        Math.floor(total_page);  
    }
    else{
       total_page =total_record/limit;
       Math.floor(total_page); 
       total_page = total_page + 1; 
    }
%>
<html>
<head>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<title>Board</title>
</head>
<body>
<div class="container py-4">   
     <div class="p-5 mb-4 bg-body-tertiary rounded-3">
      <div class="container-fluid py-5">
        <h1 class="display-5 fw-bold">게시판</h1>
        <p class="col-md-8 fs-4">Board</p>      
      </div>
    </div>
    
    <div class="row align-items-md-stretch   text-center">      
        <form action="#" method="post">
                <div class="text-end"> 
                    <span class="badge text-bg-success">전체 <%=total_record %>건   </span>
                </div>
        
            <div style="padding-top: 20px">
                <table class="table table-hover text-center">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성일</th>
                        <th>조회</th>
                        <th>글쓴이</th>
                    </tr>
                    <%
                    
                        for (int j = 0; j < allboard.size() ; j++){
                            
                            BoardDTO notice = (BoardDTO) allboard.get(j);
                    %>
                    <tr>
                        <td><%=notice.getNum()%></td>
                        <td><a href="./BoardViewAction.do?num=<%=notice.getNum()%>&pageNum=<%=pageNum%>" ><%=notice.getSubject()%></a></td>
                        <td><%=notice.getRegist_day()%></td>
                        <td><%=notice.getHit()%></td>
                        <td><%=notice.getName()%></td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </div>
            <div align="center">
                 
                <%for(int i=1; i<=total_page; i++){ %>
                    <a href="readall?pageNum=<%=i %>">
                        <% if(pageNum==i){%>
                                <font color='FF0000'><b> [<%=i %>]</b></font>
                        <%}else{ %>
                                <font color='4C5317'> [<%=i %>]</font>
                        <%} %>
                         
                    </a>
                <%} %>
            </div>
            
            <div class="py-3" align="right">                            
                <a href="#" class="btn btn-primary">&laquo;글쓰기</a>             
            </div>          
            <div align="left">              
                <select name="items" class="txt">
                    <option value="subject">제목에서</option>
                    <option value="content">본문에서</option>
                    <option value="name">글쓴이에서</option>
                </select> <input name="text" type="text" /> <input type="submit" id="btnAdd" class="btn btn-primary " value="검색 " />                
            </div>
            
        </form>         
    </div>
   
</div>
</body>
</html>