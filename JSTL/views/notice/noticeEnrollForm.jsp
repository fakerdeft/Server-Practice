<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	#enroll-form>table {border:1px solid white;}
	#enroll-form input,#enroll-form textarea{
		width:100%; /*가로길이를 부모요소의 100%로 */
		box-sizing:border-box; 
	}	
	
	
</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp"%>
	<div class="outer">
		<h2 align="center">공지사항 작성</h2>
		
		<form action="<%=contextPath %>/insert.no" method="post" id="enroll-form">
		<input type="hidden" name="userNo" value="<%=loginUser.getUserNo() %>">
		<!-- 글 작성하고 있는 작성자가 누구인지 식별자인 userNo를 넘겨준다. -->
			<table align="center">
				<tr>
					<th width="50">제목</th>
					<td width="350"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="content" rows="10" style="resize:none" required></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			<div align="center">
				<button type="submit">등록하기</button>
			
			</div>
			<br><br>
		
		
		</form>
		</div>
		
		
		
</body>
</html>