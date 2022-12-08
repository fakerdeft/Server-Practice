<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.notice.model.vo.Notice"%>
<% 
	Notice n = (Notice)request.getAttribute("notice");
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
	
	#update-form input,#update-form textarea{
		 width:100%;
	}
	
	
</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp"%>
	<div class="outer">
		<h2 align="center">공지사항 수정</h2>
		
		<form action="<%=contextPath %>/update.no" method="post" id="update-form">
		<!-- 글번호 가지고 가기 (어떤 글을 수정할지 알아야 하기 때문에)  -->
		<input type="hidden" name="nno" value="<%=n.getNoticeNo()%>">
			<table align="center">
				<tr>
					<th width="50">제목</th>
					<td width="350"><input type="text" name="title" required value="<%=n.getNoticeTitle()%>"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="content" rows="10" style="resize:none" required><%=n.getNoticeContent() %></textarea>
					</td>
				</tr>
			</table>
			<br><br>
			<div align="center">
				<button type="submit">수정하기</button>
				<button type="button" onclick="history.back();">뒤로가기</button>
			
			</div>
			<br><br>
		
		
		</form>
		</div>
		
</body>
</html>
