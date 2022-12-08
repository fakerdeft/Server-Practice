<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member"%>
<%
	//context root(path) : 고정값이 아닌 메소드를 통해 얻어오기	
	String contextPath = request.getContextPath();

	//로그인 성공시 session에 담아놓은 loginUser 꺼내기
	Member loginUser = (Member)session.getAttribute("loginUser");
	//로그인 전 loginUser == null
	//로그인 후 loginUser == 로그인한 회원 정보를 담은 Member 객체
	
	//로그인 성공시 session에 담아놓은 알림 메세지도 꺼내주기 
	String alertMsg = (String)session.getAttribute("alertMsg");
	
	//서비스 요청 전 : null
	//서비스 요청 후 : 해당서비스에서 넣어놓은 알림  메세지
	
	//쿠키 꺼내기 
	Cookie[] cookies = request.getCookies();
	//쿠키에 저장된 아이디가 있으면 사용자 아이디입력란에 넣어줄 아이디 변수
	String id = null;
	
	if(cookies!=null){
		//반복문을 사용하여 쿠키배열에 담긴 쿠키중 내가 원하는 쿠키가 있는지 비교하기
		for(int i=0; i<cookies.length;i++){
			//반복문이 순차적으로 접근하며 접근된 쿠키의 이름이 userId 라면 
			if(cookies[i].getName().equals("userId")){
				id=cookies[i].getValue(); //사용자가 입력했던 userId를 꺼내 넣기
			}
		}
	}
	
%>    
    
    
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
	<script>
		//script 태그 내에도 스크립틀릿과 같은 jsp 요소를 사용할 수 있다.
		//alert메세지가 있으면 띄워주고 없으면 안띄워주면 됨 
		
		var msg = "<%=alertMsg%>";
		
		if(msg != "null"){
			alert(msg);
			//알림창을 띄워준 후에 session에 담긴 alertMsg를 지워야한다
			//하지않으면 새로고침(페이지 리로딩)될때마다 alert창이 띄워지게 된다
			<%session.removeAttribute("alertMsg");%>
		}
	</script>
	
    <h1 align="center">Welcome E Class</h1>
    <div class="login-area">
    <%if(loginUser==null) {%>
        <form action="/jsp/login.me" method="post" id="login-form">
            <!-- table>(tr>th+td)*3 -->
            <table>
                <tr>
                    <th>아이디 : </th>
                    <%if(id==null) {%>
                    <td><input type="text" name="userId" required></td>
                    <%}else{ %>
                    <td><input type="text" name="userId" value="<%=id%>" required></td>
                    <%} %>
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
        		if("<%=id%>" != "null"){//쿠키에 담긴 아이디가 있을때
        			$("#saveId").attr("checked",true);
        		}else{//쿠키에 담긴 아이디가 없을 때
        			$("#saveId").attr("checked",false);
        		}
        	});
        	function enrollPage(){
        		//location.href = "<%=contextPath%>/views/member/memberEnrollForm.jsp";
        		//웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약하다.
        		
        		//단순한 정적 페이지 이동이여도 해당 페이지로 바로 이동하지 않고
        		//servlet을 거쳐서 서블릿 매핑값으로 보여지게 할것.
        		location.href="<%=contextPath%>/enrollForm.me";
        		
        	
        	}
        
        </script>
        
        
  	 <%}else{ %>
  	 <div id="user-info">
   	<!-- 로그인 성공 후 보여질 폼 -->
   		<b><%=loginUser.getUserName() %>님 환영합니다.</b> <br><br>
   		<div align="center">
   			<a href="<%=contextPath%>/mypage.me">마이페이지 | </a>
   			<a href="<%=contextPath%>/logout.me">로그아웃</a>
   		</div>
    </div>
   <%} %>
    </div>
    
    
   		
    

    <br clear="both"> <!--float 속성해제 -->

    <div class="nav-area" align="center">
        <!-- (div.menu>a)*4 -->
        <div class="menu"><a href="/jsp">HOME</a></div>
        <div class="menu"><a href="<%=contextPath%>/list.no">공지사항</a></div>
        <div class="menu"><a href="<%=contextPath%>/list.bo?currentPage=1">일반게시판</a></div>
        <div class="menu"><a href="<%=contextPath%>/list.th">사진게시판</a></div>

    </div>



</body>
</html>