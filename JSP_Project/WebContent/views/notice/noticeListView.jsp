<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.notice.model.vo.Notice"%>
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
%>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
    .outer{
		background:black;
		color:white;
		width:1000px;
		margin:auto;  /*가운데 정렬*/
		margin-top:50px; /*위로부터 50px만큼 여백*/
	}
    .list-area{
        border: 1px solid white;
        text-align: center;
    }
    .list-area>tbody>tr:hover{
        background-color: gray;
        cursor: pointer;
    }

 </style>
</head>
<body>
    <!-- include -->
    <%@include file="/views/common/menubar.jsp" %>
    <div class="outer">
        <br>
        <h2 align="center">공지사항</h2>
        <br>
        <!-- 공지사항은 관리자만 작성이 가능하기 때문에 해당 조건을 추가해줘야한다. -->
        
        <!-- 로그인되어있는 회원의 아이디가 admin인지 확인 -->
        <%if(loginUser != null && loginUser.getUserId().equals("admin")){%> 
        <div align="center">
        	<!-- 버튼에는 a태그와 같이 경로이동 속성인 href 속성이 없기때문에 이벤트로 추가해서 작성한다. -->
        	<!-- <button onclick="location.href='/jsp/enrollForm.no'">글작성</button> -->
        	<a href="<%=contextPath%>/enrollForm.no" class="btn btn-info">글 작성</a>
        	<!-- a태그를 버튼모양으로 보이게 부트스트랩의 클래스를 이용한다. -->
        </div>
        <br>
        <%} %>
        
        

        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th>글번호</th>
                    <th width="400">글제목</th>
                    <th width="100">작성자</th>
                    <th>조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
                <!-- 
                <tr>
                    <td>1</td>
                    <td width="400">제목입니다.</td>
                    <td width="100">김작성</td>
                    <td>5</td>
                    <td width="100">2022-10-14</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>테러한다요.</td>
                    <td>박테러</td>
                    <td>0</td>
                    <td>2022-10-14</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>이 게시글은 영국에서부터</td>
                    <td>이행운</td>
                    <td>1</td>
                    <td>2022-10-14</td>
                </tr>
 			-->
 			<!-- 리스트가 비어있을경우 -->
 			<%if(list!=null) {%>
 			<%if(list.isEmpty()) {%>
				<tr>
					<td colspan="5">존재하는 공지사항이 없습니다.</td>
				</tr> 			
 			
 			<%}else{ %>
 			<!-- 리스트가 비어있지 않을 경우 -->
 				<%for(Notice n : list){  %><!-- 향상된 for문 -->
 				<tr>
                    <td><%=n.getNoticeNo() %></td>
                    <td><%=n.getNoticeTitle() %></td>
                    <td><%=n.getNoticeWriter() %></td>
                    <td><%=n.getCount() %></td>
                    <td><%=n.getCreateDate() %></td>
                </tr>
 				<%} %>
 			<%} %>
 			<%} %>
            </tbody>

        </table>
        <br><br><br>
    </div>
    
    <script>
    	$(function(){
    		$(".list-area>tbody>tr").click(function(){
    			//console.log("클릭됨");
    			//글을 클릭했을때 해당 글을 구별할 수 있는 식별자 역할을 하는 글 번호를 들고 요청해야한다.
    			//글번호는 tr에 자손중 첫번째 자손 td안에 들어있다 해당 요소를 가지고 가자.
    			var nno = $(this).children().eq(0).text();
    			//console.log(nno);
    			
    			//요청할 url 뒤에 ?키=벨류 형태로 요청을 보내면 된다.
    			//물음표 뒤에 있는 내용들을 쿼리스트링 이라고 한다. 요청을 보낼때 직접 작성하여 넘겨준다. 
    			// http://localhost:8888/jsp/요청주소?키=벨류
    					//localhost:8888/jsp/detail.no?nno= + 글번호
    			location.href="<%=contextPath%>/detail.no?nno="+nno;				
    		});
    		
    	});
    
    
    </script>
    
    

</body>
</html>