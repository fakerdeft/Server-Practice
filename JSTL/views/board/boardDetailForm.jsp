<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.*"%>
<%
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");
	
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
	.outer table{boarder-color:white;}
</style>
</head>
<body>
	<%@ include file="/views/common/menubar.jsp" %>
	
	<div class="outer">
		<br>
		<h2 align="center">게시글 상세 보기</h2>
		<br>
		<table id="detail-area" align="center" border="1">
			<tr>
				<th width="70">카테고리</th>
				<td width="70">${b.category }</td>
				<th width="70">제목</th>
				<td width="350">${b.boardTitle }</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${b.boardWriter }</td>
				<th>작성일</th>
				<td>${b.createDate }</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:200px; white-space:pre;">${b.boardContent }</p>
						
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
				<c:choose>
					<c:when test="${empty at }">
					첨부파일이 없습니다.
					</c:when>
					<c:otherwise>
					<a download="${at.originName }" href="${pageContext.request.contextPath}${at.filePath}${at.changeName}">${at.originName }</a>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
		<br>
		<div align="center">
			<button onclick="location.href='list.bo?currentPage=1'">목록가기</button>
			<!--현재 로그인된 유저의 아이디가 글 작성자와 동일하다면  -->
			<c:if test="${not empty loginUser and loginUser.userId eq b.boardWriter }">
			<button onclick="location.href='updateForm.bo?bno=${b.boardNo}'">수정하기</button>
			<button onclick="location.href='delete.bo?bno=${b.boardNo}'">삭제하기</button>
			</c:if>
		</div>
		<br>
		
		<div id="reply-area">
			<table border="1" align="center">
				<thead>
					<tr>
						<th>댓글작성</th>
						<td>
							<textarea id="replyContent"rows="3" cols="50" style="resize:none"></textarea>
						</td>
						<td><button onclick="insertReply();">댓글 작성</button></td>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
		</div>
		
		<script>
		
			$(function(){
				selectReplyList();
				
				setInterval(selectReplyList,1000);
			});
		
		
			function insertReply(){
				$.ajax({
					url : "rinsert.bo",
					data : {
						bno : ${b.boardNo},
						content : $("#replyContent").val()
					},
					type : "post",
					success : function(result){
						
						if(result>0){
							console.log("댓글작성 성공");
							selectReplyList();//댓글목록 갱신
							$("#replyContent").val("");
						}else{
							console.log("댓글작성 실패");
						}
					},
					error : function(){
						console.log("통신 실패");
					}
				});
			}
			
			
			function selectReplyList(){
				$.ajax({
					url : "rlist.bo",
					data : {bno : ${b.boardNo}},
					success : function(list){
						var str ="";
						for(var i in list){
							str += "<tr>"
									+ "<td>"+list[i].replyWriter+"</td>"
									+ "<td>"+list[i].replyContent+"</td>"
									+ "<td>"+list[i].createDate+"</td>"
									+ "</tr>";
						}
						$("#reply-area tbody").html(str);
					},
					error : function(){
						console.log("통신 실패");
					}
				});
			}
			
			
			
			
			
		</script>
		
		<br><br>
	</div>
</body>
</html>