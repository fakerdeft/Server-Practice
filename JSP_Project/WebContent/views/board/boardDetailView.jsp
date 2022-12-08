<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board"%>
<%
	Board b = (Board)request.getAttribute("board");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.outer{
		background:pink;
		color:white;
		width:1000px;
		margin:auto; /*가운데 정렬*/
		margin-top:50px; /*위로부터 50px만큼 여백*/
	}
	
	#detail-area{
		background-color: black;
		border: 1px solid white;
	}
	
</style>
</head>
<body>
<%@ include file="/views/common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center" style="color:black">일반게시판 상세보기</h2>
		
		<br><br>
		
		<table id="detail-area" align="center" border="2">
			<tr>
				<th>카테고리</th>
				<td><%=b.getCategory() %></td>
				<th>제목</th>
				<td colspan="3"><%=b.getBoardTitle()%></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><%=b.getBoardWriter()%></td>
				<th>작성일</th>
				<td><%=b.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:300px"><%=b.getBoardContent() %></p>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">선택된 파일 없음</td>
			</tr>
		</table>
		<br><br>
		
		<div align="center">
			<a href="<%=contextPath%>/list.bo?currentPage=1" class="btn btn-success">목록으로</a>
			
			<%if(loginUser != null && loginUser.getUserId().equals("admin")){ %>
				<a href="<%=contextPath%>/updateForm.bo?bno=<%=b.getBoardNo()%>" class="btn btn-info">수정하기</a>
				<a href="<%=contextPath%>/deleteForm.bo?bno=<%=b.getBoardNo()%>" onclick="return chk();" class="btn btn-danger">삭제하기</a>
			<%} %>
			
		</div>
		
		<script>
			function chk(){
				
				var result = confirm("정말로 삭제하시겠습니까? ");
				
				return result;
			}
		</script>

		<br><br>
		
	</div>
</body>
</html>








