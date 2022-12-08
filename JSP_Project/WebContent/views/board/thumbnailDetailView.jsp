<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,com.kh.board.model.vo.*"%>
<%
	Board b = (Board)request.getAttribute("b");
	ArrayList<Attachment> list = (ArrayList<Attachment>)request.getAttribute("list");
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
	table{
		border : 2px solid white;
	}
</style>
</head>
<body>
	 <%@include file ="/views/common/menubar.jsp" %> 
	<div class="outer">
		<br>
		<h2 align="center">사진게시글 상세보기</h2>
		<br>
			<table align="center" border="1">
				<!-- (tr>th+td+td+td)*4 -->
				<tr>
					<th width="100">제목</th>
					<td colspan="3"><%=b.getBoardTitle() %></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><%=b.getBoardWriter() %></td>
					<th>작성일</th>
					<td><%=b.getCreateDate() %></td>
					
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3">
						<p style="height:70px"><%=b.getBoardContent() %></p>
					</td>
				</tr>
				<tr>
					<th>대표이미지</th> <!--미리보기-->
					<td colspan="3" align="center">
						<img id="titleImg" src="<%=contextPath%><%=(list.get(0).getFilePath())+(list.get(0).getChangeName())%>"width="250" height="170">
					</td>
				</tr>
				<tr>
					<th>상세이미지</th>
					<%for(int i=1; i<list.size(); i++) {%>
					<td><img id="contentImg<%=i %>" src="<%=contextPath%><%=(list.get(i).getFilePath())+(list.get(i).getChangeName())%>" width="150" height="120"></td>
					<%} %>
				</tr>

			</table>
			
		<br><br>
		
	</div>
	
</body>	
	
	
</body>
</html>