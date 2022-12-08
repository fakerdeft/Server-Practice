<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Category"%>
<% 
	
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
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
	#enroll-form>table {
		border:1px solid white;
		}
	#enroll-form input,#enroll-form textarea{
		width:100%;
		box-sizing:border-box;
	}
	
 </style>
</head>
<body>
	<%@include file="/views/common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">게시글 작성</h2>
		
		<form action="<%=contextPath %>/insert.bo" id="enroll-form" method="post" enctype="multipart/form-data">
		<!-- 카테고리,제목,내용,첨부파일 -->
		<!-- 글작성시에 어떤 회원이 작성했는지 알아야한다. -->
		<!-- 작성자 회원 번호를 hidden으로 숨겨서 넘긴다 또는 session에서 꺼내쓴다. -->
		<input type="hidden" name="userNo" value="<%=loginUser.getUserNo()%>">
		
		<table align="center">
			<tr>
				<th width="100">카테고리</th>
				<td width="500">
					<select name="category">
						<%for(Category c : list){ %>
						<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
						<%} %>
					</select>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" required></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content" rows="10" style="resize:none" required></textarea>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td><input type="file" name="upfile"></td>
			</tr>
			
		</table>
		
		<br>
		<div align="center">
			<button type="submit">글작성</button>
			<button type="reset">취소</button>
		</div>
		
		
		</form>
	
	
		<br><br>
	</div>

</body>
</html>