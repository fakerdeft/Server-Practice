<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.innerOuter{
		width:1000px;
		margin:auto;  /*가운데 정렬*/
		margin-top:50px;
	}
</style>
</head>
<body>
	<!-- CRUD  Create / Read (Select) / Update / Delete
		회원서비스 - 로그인(R)/회원가입(C)/회원탈퇴(U/D)/마이페이지(R/수정U)	
		
		공지사항 서비스 - 공지사항 리스트 조회(R) /공지사항 상세조회(R)/공지사항 작성(C)/공지사항수정(U)/공지사항 삭제(D)
		
		일반게시판 서비스 - 게시판리스트 조회(R)-페이징처리/게시판 상세조회(R)/게시판작성(C)-첨부파일1개/
					    게시판 수정(U)/게시판삭제(U/D)/[댓글리스트조회(R)/댓글작성(C)]
		
		사진게시판 서비스 - 게시판리스트 조회(R)-썸네일/게시판 상세조회(R)/게시판작성(C)-다중파일업로드
	-->
	<%@ include file="views/common/menubar.jsp" %>
	
	<!-- 인덱스에 게시글 조회수 TOP 5 뽑아보기 1초마다 조회해올수 있게 해보기-->
		<br><br>
		<div class="innerOuter">
			<h4 align="center">게시글 Top5</h4>
			<br>
			<a href="list.bo?currentPage=1" style="float:right">더보기&raquo;</a>
			
            <table id="boardList" class="table table-hover">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>첨부파일</th>
                    </tr>
                </thead>
                <tbody>
                	<!-- 현재 조회수가 가장 높은 상위 5개의 게시글 조회해서 뿌리기 (Ajax) -->
                </tbody>
            </table>
		</div>
		
		<script>
			
			$(function(){
				//조회하는 function 넣기
				selectTopList();
				//반복적으로 하고싶다면 setinterval 사용하기
				
				//글을 클릭했을때 상세보기 (이 방식으론 동적으로 생성된 요소에 이벤트가 작동하지 않음)
				/*
				$("#boardList>tbody>tr").click(function(){
					location.href="<%=contextPath%>/detail.bo?bno="+$(this).children().eq(0).text();
				});
				*/
				//동적으로 생성된 요소에 이벤트를 적용하고 싶다면 상위요소 선택자 , 하위요소선택자 를 구별하는 이벤트 구문을 사용
				$("#boardList").on("click","tbody>tr",function(){
					location.href="<%=contextPath%>/detail.bo?bno="+$(this).children().eq(0).text();
				});
				
				
			});
			
			
			function selectTopList(){
				$.ajax({
					url : "topList.bo",
					success : function(list){
						var str = "";
						
						for(var i in list){
							str += "<tr>"
								  +"<td>"+list[i].boardNo+"</td>"
								  +"<td>"+list[i].boardTitle+"</td>"
								  +"<td>"+list[i].boardWriter+"</td>"
								  +"<td>"+list[i].count+"</td>"
								  +"<td>"+list[i].createDate+"</td>"
								  +"<td>";
							if(list[i].titleImg != null){//첨부파일(originName)이 있으면 
								str+= "★";
							}
							str+="</td></tr>";  
						}
						
						$("#boardList tbody").html(str);
					
					},
					error : function(){
						console.log(실패);
					}
				});
			}
			
		
	   	</script>
		
		
		
		<br><br>
		
	
</body>
</html>