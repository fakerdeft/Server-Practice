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
	
	#detail-area{
		 border:2px solid white;
	}
	
	
</style>
</head>
<body>
	<%@include file="/views/common/menubar.jsp" %>
	<div class="outer">
		<h2 align="center">공지사항 상세보기</h2>	
		
		<br><br>
		
		<table id="detail-area" align="center" border="2">
			<tr>
				<th width="70">제목</th>
				<td width="350" colspan="3"><%=n.getNoticeTitle() %></td>
			</tr>
			
			<tr>
				<th>작성자</th>
				<td><%=n.getNoticeWriter() %></td>
				<th>작성일</th>
				<td><%=n.getCreateDate() %></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:150px"><%=n.getNoticeContent() %></p>
				</td>
			</tr>
		</table>
		<br><br>
		
		<div align="center">
			<a href="<%=contextPath %>/list.no" class="btn btn-success">목록으로</a>
			
			<%if(loginUser != null && loginUser.getUserId().equals("admin")) {%>
				<a href="<%=contextPath %>/updateForm.no?nno=<%=n.getNoticeNo() %>" class="btn btn-info">수정하기</a>
				<a href="<%=contextPath %>/deleteForm.no?nno=<%=n.getNoticeNo() %>" onclick="return chk();" class="btn btn-danger">삭제하기</a>
		
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