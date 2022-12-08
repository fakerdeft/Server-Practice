<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome E Class</title>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>


<style>
	.outer{
		background:black;
		color:white;
		width:1000px;
		margin:auto;  /*가운데 정렬*/
		margin-top:50px; /*위로부터 50px만큼 여백*/
	}
    #login-form, #user-info {
        float: right;
    }
    #user-info a {
        text-decoration: none; /*밑줄 없애기*/
        color: black;
        font-size:12px;
    }

    .nav-area {
        background-color: black;
    }
    .menu {
        display: table-cell; /*인라인 요소처럼 배치하기*/
        height: 50px;
        width: 150px;
    }
    .menu a{ 
        text-decoration: none;
        color: white;
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px;
    }
    .menu a:hover{
        background-color: darkgray;
    }

</style>
</head>
<body>
		
	<c:if test="${ not empty alertMsg}">
		
			<script>
			alert(${alertMsg});
			</script>
			
			<c:remove var="alertMsg"/>
	</c:if>
		

	
    <h1 align="center">Welcome E Class</h1>
    <div class="login-area">
    	<c:choose>
		<c:when test="${ empty loginUser }">				<%--     <%if(loginUser==null) {%> --%>
        <form action="/jsp/login.me" method="post" id="login-form">
            <!-- table>(tr>th+td)*3 -->
            <table>
                <tr>
                    <th>아이디 : </th>
                    <c:choose>
                    	<%--쿠키중 userId라는 이름을 가진 쿠키의 value를 추출 --%>
                    	<c:when test="${ empty cookie.userId.value }">	<%--<%if(id==null) {--%>
        		            <td><input type="text" name="userId" required></td>
                    	</c:when>
                    	<c:otherwise> 		<%--<%}else{ --%>
		                    <td><input type="text" name="userId" value="${cookie.userId.value }" required></td>
                    	</c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <th>비밀번호 : </th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                	<td colspan="2"><input type="checkbox" id="saveId" name="saveId">&nbsp; <label for="saveId">아이디 저장</label>
                		</td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </th>
                </tr>
            </table>
        </form>
        <script>
        	$(function(){
        		<c:choose>        		
        		<c:when test="${ not empty cookie.userId.value}">
        			$("#saveId").attr("checked",true);
        		</c:when>
        		<c:otherwise>
        			$("#saveId").attr("checked",false);
        		</c:otherwise>
        		</c:choose>
        		
// 				var uId = "${cookie.userId.value}";
// 				if(uId==""){
// 					$("#saveId").attr("checked",false);
// 				}else{
// 					$("#saveId").attr("checked",true);
// 				}
        	});
        	function enrollPage(){
<%--         	location.href = "<%=contextPath%>/views/member/memberEnrollForm.jsp"; --%>
        		//웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약하다.
        		//단순한 정적 페이지 이동이여도 해당 페이지로 바로 이동하지 않고
        		//servlet을 거쳐서 서블릿 매핑값으로 보여지게 할것.
        		location.href="enrollForm.me";
        	}
        </script>
        </c:when>
        <c:otherwise>
		  	 <div id="user-info">
		   	<!-- 로그인 성공 후 보여질 폼 -->
		   		<b>${loginUser.userName }님 환영합니다.</b> <br><br>
		   		<div align="center">
		   			<a href="mypage.me">마이페이지 | </a>
		   			<a href="logout.me">로그아웃</a>
		   		</div>
		    </div>
		    </div>
    </c:otherwise>
    </c:choose>
   		
    

    <br clear="both"> <!--float 속성해제 -->

    <div class="nav-area" align="center">
        <!-- (div.menu>a)*4 -->
        <div class="menu"><a href="${pageContext.request.contextPath }">HOME</a></div>
        <div class="menu"><a href="list.no">공지사항</a></div>
        <div class="menu"><a href="list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="list.th">사진게시판</a></div>

    </div>



</body>
</html>