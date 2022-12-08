<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,com.kh.board.model.vo.Board"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
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
		height:800px;
		margin:auto;  /*가운데 정렬*/
		margin-top:50px; /*위로부터 50px만큼 여백*/
	}
	.list-area{
		width:760px;
		margin:auto;
	}
	.thumbnail{
		border : 1px solid white;
		width : 220px;
		display : inline-block;
		margin : 15px;
	}
	.thumbnail:hover{
		cursor:pointer;
		opacity:0.7;
	}
</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">사진 게시판</h2>
		<br>
		<%if(loginUser != null) {%>
		<div align="center">
			<button onclick="location.href='<%=contextPath%>/enrollForm.th'">글작성</button>
		</div>
		<%} %>
		<div class="list-area">
		<%if(!list.isEmpty()) {%>
			<%for(Board b : list) {%>
			<div class="thumbnail" align="center">
				<input type="hidden" value="<%=b.getBoardNo() %>">
				<img src="<%=contextPath %><%=b.getTitleImg()%>" width="200px" height="150px">
				<p>
					No.<%=b.getBoardNo() %> <%=b.getBoardTitle() %><br>
					조회수 : <%=b.getCount() %>
				</p>
			</div>
			<%} %>
			<%}else{ %>
				조회된 게시글이 없습니다.
			<%} %>
		</div>
	</div>
	
	<script>
		$(function(){
			$(".thumbnail").click(function(){
				location.href="<%=contextPath%>/detail.th?bno="+$(this).children().eq(0).val();
			})
		})
	
	</script>
	


<br><br><br><br><br>
</body>
</html>