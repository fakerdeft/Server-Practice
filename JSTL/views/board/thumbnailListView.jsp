<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Board"%>
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
		<c:if test="${not empty loginUser }">
		<div align="center">
			<button onclick="location.href='enrollForm.th'">글작성</button>
		</div>
		</c:if>
		<div class="list-area">
		<c:choose>
			<c:when test="${not empty list }">
				<c:forEach var="b" items="${list }">
			<div class="thumbnail" align="center">
				<input type="hidden" value="${b.boardNo }">
				<img src="${pageContext.request.contextPath}${b.titleImg}" width="200px" height="150px">
				<p>
					No.${b.boardNo} ${b.boardTitle }<br>
					조회수 : ${b.count }
				</p>
			</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				조회된 게시글이 없습니다.
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	
	<script>
		$(function(){
			$(".thumbnail").click(function(){
				location.href="detail.th?bno="+$(this).children().eq(0).val();
			})
		})
	
	</script>
	



</body>
</html>