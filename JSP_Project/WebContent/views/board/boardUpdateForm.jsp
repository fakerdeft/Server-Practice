<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.* ,java.util.ArrayList"%>
<%
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");
	ArrayList<Category> cList = (ArrayList<Category>)request.getAttribute("cList");
	
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
	#update-form>table {
		border:1px solid white;
		}
	#update-form input,#update-form textarea{
		width:100%;
		box-sizing:border-box;
	}
	
 </style>
</head>
<body>
	<%@include file="/views/common/menubar.jsp" %>
	<div class="outer">
		<br>
		<h2 align="center">게시글 수정</h2>
		
		<form action="<%=contextPath %>/update.bo" id="update-form" method="post" enctype="multipart/form-data">
		<!-- 카테고리,제목,내용,첨부파일 -->
		<!-- 어떤 게시글에 대해 수정처리 할것인지 알아야 하기 때문에 boardNo를 넘긴다  -->
		<input type="hidden" name="boardNo" value="<%=b.getBoardNo()%>">
		
		<table align="center">
			<tr>
				<th width="100">카테고리</th>
				<td width="500">
					<select name="category">
						<%for(Category c : cList){ %>
						<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName() %></option>
						<%} %>
					</select>
					<script>
						$(function(){
							$("#update-form option").each(function(){
								if($(this).text()=="<%=b.getCategory()%>"){
									//update-form 안에 option태그를 순차적으로 접근하여
									//만약 해당요소($(this))에 있는 텍스트가 디비에서 조회해온 게시글의 category와
									//일치하다면 속성중에 selected(선택됨) 속성을 true 변경하여 선택되어지게끔 변경 
									$(this).attr("selected",true);
								}
							})
						})
					</script>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="boardTitle" value="<%=b.getBoardTitle()%>"required></td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="boardContent" rows="10" style="resize:none" required><%=b.getBoardContent() %></textarea>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
				<%if(at !=null) {%>
					<%=at.getOriginName() %>
					<!-- 원본파일의 파일번호,수정명 가지고 가자 -->
					<input type="hidden" name="originFileNo" value="<%=at.getFileNo() %>">
					<input type="hidden" name="originFileName" value="<%=at.getChangeName() %>">
				<%} %>
				<input type="file" name="reUpfile">
				</td>
			</tr>
			
		</table>
		
		<br>
		<div align="center">
			<button type="submit">수정하기</button>
			<button type="reset">취소</button>
		</div>
		
		
		</form>
	
	
		<br><br>
	</div>

</body>
</html>