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
	
	#mypage-form table {
		margin:auto;
	}
	#mypage-form input {
		margin:5px;
	}
</style>
</head>
<body>
<%-- 	<%@ include file="/views/common/menubar.jsp" %> --%>
	<jsp:include page="/views/common/menubar.jsp"/>
<%-- 	<jsp:include page="../common/menubar.jsp"/> --%>
	
    <div class="outer">
        <br>
        <h2 align="center">마이 페이지</h2>

        <form action="update.me" id="mypage-form" method="post">
            <!--아이디,이름,전화번호,이메일,주소,관심사-->
            <!-- table>(tr>td*3)*6 -->
            <table>
                <tr>
                    <td>*아이디</td>
                    <td><input type="text" name="userId" value="${loginUser.userId}" required disabled></td>
                    <td></td>
                </tr>
                <tr>
                    <td>*이름</td>
                    <td><input type="text" name="userName" value="${loginUser.userName }" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>전화번호</td>
                    <td><input type="text" name="phone" value="${loginUser.phone }" placeholder="-포함 입력해주세요"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td><input type="email"  value="${loginUser.email }" name="email"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>주소</td>
                    <td><input type="text" name="address" value="${loginUser.address }"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>관심사</td>
                    <td colspan="2">
                        <!-- (input[type=checkbox name=interest id= value=]+label)*6 -->
                        <input type="checkbox" name="interest" id="coding" value="코딩">
                        <label for="coding">코딩</label>
                        <input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>
                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>
                        <br>
                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>
                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>
                        <input type="checkbox" name="interest" id="music" value="음악">
                        <label for="music">음악</label>

                    </td>
                </tr>
            </table>
            <script>
            	$(function(){
            		//사용자가 체크한 데이터를 배열로 전달받아 하나의 문자열로 만들어준다음 loginUser에 넣었다
            		//넣어놓은 문자열 interest를 search() 메소드를 이용하여 체크박스에 있는 value값과 비교해
            		//value값이 문자열에 포함되어 있다면 해당 체크박스를 checked 하여 체크되어 있게 만들기
            		
            		//순서 1)
            		//session에 담겨있는 loginUser에 interest 데이터 꺼내서 변수화하기
            		//순서 2)
            		//변수화한 데이터 스크립트 변수에 대입하기.
            		var interest = "${loginUser.interest}";
            		//순서 3)
            		//비교할 checkbox들 선택자로 선택해놓기
            		//    2)객체/배열.each(function(매개변수,매개변수2){
           			//			 순차적으로 접근하여 수행할 내용
        			//					});
            		
            		//console.log($("input[type=checkbox]"));
            		//console.log(interest); 
            		
            		$("input[type=checkbox]").each(function(){
            		//순서 4)
            		//checkbox들 순회하며 value값이 interest에 포함되어있는지 확인 후 checked 하기 
				//console.log($(this).val());
            		//순차적으로 접근된 요소의 value값을 알았으니 interest 문자열에 포함되어 있는지 확인하기.
            		//console.log(interest.search($(this).val()));
            		if(interest.search($(this).val()) != -1 ){
            			//interest 문자열에 해당하는 value값이 포함이 되어있으면  
            			$(this).attr("checked",true); //해당 요소의 checked속성을 true로 바꿔서 체크되게한다.
            		}
            		});
            		
            		
            		//interest : "코딩,요리,게임"
            		// "" / "코딩,요리 ..."
            		//$("input[type=checkbox]") input type checkbox인 요소들이 배열의 형태로 선택되어진다.
            		
            		
            	})
            
            </script>
			
			<br><br>
			<div align="center">
				<button type="submit" class="btn btn-primary">정보변경</button>
				<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#updatePwdForm">비밀번호 변경</button>
				<button type="button" class="btn btn-info" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
			
			</div>
        </form>
		<br><br>
    </div>

	<!-- 회원탈퇴 모달용 div  -->
	<div class="modal" id="deleteForm">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">회원 탈퇴</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	        <b>탈퇴 후 복구가 불가능합니다. <br> 정말로 탈퇴하시겠습니까? </b> <br><br>
	        
	        <form action="delete.me" method="post">
       			<!-- 데이터 숨겨서 보내기 -->
<%--        		 <input type="hidden" name="userId" value="<%=userId %>">  --%>
	        	<table>
	        		<tr>
	        			<td>비밀번호</td>
	        			<td><input type="password" name="userPwd" required></td>
	        		</tr>
	        	</table>
				<br>
				<button type="submit" class="btn btn-danger">탈퇴하기</button>	        
	        </form>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 비밀번호변경모달  div  -->
	<div class="modal" id="updatePwdForm">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">비밀번호 변경</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body">
	        
	        <form action="updatePwd.me" method="post">
       			<!-- 데이터 숨겨서 보내기 -->
       			<input type="hidden" name="userId" value="${loginUser.userId }">
	        	<table>
	        		<tr>
	        			<td>현재 비밀번호</td>
	        			<td><input type="password" name="userPwd" id="inputPwd" required></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호</td>
	        			<td><input type="password" name="updatePwd" required></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호 확인</td>
	        			<td><input type="password" name="pwdCheck" required></td>
	        		</tr>
	        	</table>
				<br>
				<button type="submit" class="btn btn-danger" onclick="return invalidate();">변경하기</button>
	       		<button type="submit" class="btn btn-danger" id="btn">변경하기2</button>
	        </form>
	         
	         <script>
	         function invalidate(){
	         		//세션에 담겨져있는 로그인 정보의 비밀번호를 꺼내기
	         		//현재비밀번호 input에 작성된 value 꺼내기
	         		var originPwd = "${loginUser.userPwd}"; //세션에 담겨있는 로그인 정보
	         		var inputPwd = document.querySelector("#inputPwd").value;
					// inputPwd = document.getElementById("inputPwd").value;


					if(originPwd != inputPwd){
						alert("현재 비밀번호를 잘못입력하셨습니다.");
						return false;
					}
	         		//변경할 비밀번호 input value와 변경할 비밀번호 확인 input value를 비교하기 
	         		
					var updatePwd = document.querySelector("input[name=updatePwd]").value;
	         		var pwdCheck = document.querySelector("input[name=pwdCheck]").value;
	         	
					if(updatePwd!=pwdCheck){
						alert("변경할 비밀번호와 확인 일치하지 않습니다.");
						return false;
					}
	         	}
	         
	         //jquery방식 
	         $("#btn").click(function(){
	        	 var originPwd = "${loginUser.userPwd}"; //세션에 담겨있는 로그인 정보
	        	 var inputPwd = $("#inputPwd").val();
	        	 
	        	 if(originPwd != inputPwd){
						alert("현재 비밀번호를 잘못입력하셨습니다.");
						return false;
				}
	        	 
	        	 var updatePwd = $("input[name=updatePwd]").val();
	         	 var pwdCheck = $("input[name=pwdCheck]").val();
	         	
					if(updatePwd!=pwdCheck){
						alert("변경할 비밀번호와 확인 일치하지 않습니다.");
						return false;
					}
	         });
            </script>
	      </div>
	    </div>
	  </div>
	</div>



</body>
</html>