<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Board
			    				,com.kh.common.model.vo.PageInfo"%>

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
    .list-area{
        border: 1px solid white;
        text-align: center;
    }
    .list-area>tbody>tr:hover{
        background-color: gray;
        cursor: pointer;
    }

 </style>
</head>
<body>
	<!-- include -->
    <%@include file="/views/common/menubar.jsp" %>
    
    <div class="outer">
        <br>
        <h2 align="center">게시판</h2>
        <br>
        <!-- 공지사항은 관리자만 작성이 가능하기 때문에 해당 조건을 추가해줘야한다. -->
        
        <!-- 로그인되어있는 회원인지 확인  -->
        <c:if test="${ not empty loginUser }">
        <div align="center">
        	<!-- 버튼에는 a태그와 같이 경로이동 속성인 href 속성이 없기때문에 이벤트로 추가해서 작성한다. -->
        	<!-- <button onclick="location.href='/jsp/enrollForm.no'">글작성</button> -->
        	<a href="enrollForm.bo" class="btn btn-info">글 작성</a>
        	<!-- a태그를 버튼모양으로 보이게 부트스트랩의 클래스를 이용한다. -->
        </div>
        <br>
        </c:if>
        
        

        <table class="list-area" align="center">
            <thead>
                <tr>
                    <th width="70">글번호</th>
                    <th width="70">카테고리</th>
                    <th width="300">제목</th>
                    <th width="100">작성자</th>
                    <th width="50">조회수</th>
                    <th width="100">작성일</th>
                </tr>
            </thead>
            <tbody>
            	<c:choose>
            		<c:when test="${ empty list }">
            	
            	<tr>
            		<td colspan="6">조회된 게시글이 없습니다.</td>
            	</tr>
            	</c:when>
            	<c:otherwise>
            		<c:forEach var="b" items="${list }">
			                <tr>
			                    <td>${b.boardNo }</td>
			                    <td>${b.category }</td>
			                    <td>${b.boardTitle }</td>
			                    <td>${b.boardWriter }</td>
			                    <td>${b.count }</td>
			                    <td>${b.createDate }</td>
			                </tr>
			         </c:forEach>
	            </c:otherwise>
	            </c:choose>
                <!-- 형식 예시
                 <tr>
                    <td>20</td>
                    <td>요리</td>
                    <td>제가 한 요리 좀 보세요</td>
                    <td>cooking</td>
                    <td>14</td>
                    <td>2022-10-18</td>
                </tr>
                 -->
            </tbody>
        </table>
        
        <script>
        	$(function(){
        		$(".list-area>tbody>tr").click(function(){
        			location.href="detail.bo?bno=" + $(this).children().eq(0).text();
        		})
        	});
        </script>
        
        
        
        <br><br>
        
        <!-- 페이징바 -->
        <div align="center" class="paging-area">
        	<c:choose>
        		<c:when test="${ pi.currentPage ne 1 }">
    	    	<button class="btn btn-info"  onclick="location.href='list.bo?currentPage=${pi.currentPage - 1 }'">&lt;</button>
        	</c:when>
        	<c:otherwise>
	        	<button class="btn btn-info"  disabled>&lt;</button>
        	</c:otherwise>
        	</c:choose>
        	
        	 <c:forEach var="i" begin="${pi.startPage }" end="${pi.endPage }">
        	 	<c:choose>
        	 		<c:when test="${i eq pi.currentPage }">
	        	 		<!-- 내가 요청한 페이지 버튼 비활성화 -->
	        	 		<button class="btn btn-info" disabled>${i }</button>
        	 		</c:when>
        	 		<c:otherwise>
	        			 <button class="btn btn-info" onclick="location.href='list.bo?currentPage=${i}'">${i }</button>
        	 		</c:otherwise>
        	 	</c:choose>
        	 </c:forEach>
        	 <c:if test="${pi.currentPage ne pi.maxPage }">
        		<button class="btn btn-info"  onclick="location.href='list.bo?currentPage=${pi.currentPage+1}'">&gt;</button>
        	 </c:if>
        </div>
        <br><br>
    </div>
</body>
</html>