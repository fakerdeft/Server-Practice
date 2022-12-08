<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList
			    				,com.kh.board.model.vo.Board
			    				,com.kh.common.model.vo.PageInfo"%>

<% 
	//list와 pi 꺼내기 
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");

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
        <h2 align="center">게시판</h2>
        <br>
        <!-- 공지사항은 관리자만 작성이 가능하기 때문에 해당 조건을 추가해줘야한다. -->
        
        <!-- 로그인되어있는 회원인지 확인  -->
        <%if(loginUser != null){%> 
        <div align="center">
        	<!-- 버튼에는 a태그와 같이 경로이동 속성인 href 속성이 없기때문에 이벤트로 추가해서 작성한다. -->
        	<!-- <button onclick="location.href='/jsp/enrollForm.no'">글작성</button> -->
        	<a href="<%=contextPath%>/enrollForm.bo" class="btn btn-info">글 작성</a>
        	<!-- a태그를 버튼모양으로 보이게 부트스트랩의 클래스를 이용한다. -->
        </div>
        <br>
        <%} %>
        
        

        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th width="70">글번호</th>
                    <th width="70">카테고리</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
            	<%if(list.isEmpty()) {%>
            	<tr>
            		<td colspan="6">조회된 게시글이 없습니다.</td>
            	</tr>
            	
            	<%}else{%>
				   		<%for(Board b : list) {%>
			                <tr>
			                    <td><%=b.getBoardNo() %></td>
			                    <td><%=b.getCategory() %></td>
			                    <td><%=b.getBoardTitle() %></td>
			                    <td><%=b.getBoardWriter() %></td>
			                    <td><%=b.getCount() %></td>
			                    <td><%=b.getCreateDate() %></td>
			                </tr>
			             <%} %>
	            <%} %>
                <!-- 형식 예시
                 <tr>
                    <td>20</td>
                    <td>요리</td>
                    <td>제가 한 요리 좀 보세요</td>
                    <td>cooking</td>
                    <td>14</td>
                    <td>2022-10-18</td>
                </tr>
                 -->
            </tbody>
        </table>
        
        <script>
        	$(function(){
        		$(".list-area>tbody>tr").click(function(){
        			location.href="<%=contextPath%>/detail.bo?bno=" + $(this).children().eq(0).text();
        		})
        	});
        </script>
        
        
        
        <br><br>
        
        <!-- 페이징바 -->
        <div align="center" class="paging-area">
        	<%if(pi.getCurrentPage() != 1) {%>
        	<button class="btn btn-info"  onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=pi.getCurrentPage()-1%>'">&lt;</button>
        	<%}else{ %>
        	<button class="btn btn-info"  disabled>&lt;</button>
        	<%} %>
        	
        	 <%for(int i=pi.getStartPage(); i<=pi.getEndPage(); i++) {%>
        	 	<%if(i==pi.getCurrentPage()) {%>
        	 	<!-- 내가 요청한 페이지 버튼 비활성화 -->
        	 	<button class="btn btn-info" disabled><%=i %></button>
        	 	<%}else{ %>
        			 <button class="btn btn-info" onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=i%>'"><%=i%></button>
				<%} %>        	 
        	 <%} %>
        	 
        	 <%if(pi.getCurrentPage() != pi.getMaxPage()) {%>
        	<button class="btn btn-info"  onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=pi.getCurrentPage()+1%>'">&gt;</button>
        	<%} %>
        </div>
        <br><br>
    </div>
</body>
</html>