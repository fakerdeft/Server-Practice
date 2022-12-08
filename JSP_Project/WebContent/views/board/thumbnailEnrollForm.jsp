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
	#enroll-form>table {
		border:1px solid white;
		}
	#enroll-form input,#enroll-form textarea{
		width:100%;
		box-sizing:border-box;
	}
</style>
</head>
<body>
	 <%@include file ="/views/common/menubar.jsp" %> 
	<div class="outer">
		<br>
		<h2 align="center">사진게시판 작성하기</h2>
		<br>

		<form action="<%=contextPath %>/insert.th" id="enroll-form" method="post" enctype="multipart/form-data">
			<input type="hidden" name="userNo" value="<%=loginUser.getUserNo() %>">
			<table align="center">
				<!-- (tr>th+td+td+td)*4 -->
				<tr>
					<th width="100">제목</th>
					<td colspan="3"><input type="text" name="title" required></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan="3"><textarea name="content" style="resize: none;" cols="30" rows="10" required></textarea></td>
				</tr>
				<tr>
					<th>대표이미지</th> <!--미리보기-->
					<td colspan="3" align="center">
						<img id="titleImg" width="250" height="170">
					</td>
				</tr>
				<tr>
					<th>상세이미지</th>
					<td><img id="contentImg1" width="150" height="120"></td>
					<td><img id="contentImg2" width="150" height="120"></td>
					<td><img id="contentImg3" width="150" height="120"></td>
				</tr>

			</table>
			
			<!-- 파일 첨부 영역 -->
			<div id="file-area">
				<!-- input[type=file id=file$ name=file$]*4 -->
				<input type="file" id="file1" name="file1" onchange="loadImg(this,1);" required> <!--대표이미지라서 필수-->
				<input type="file" id="file2" name="file2" onchange="loadImg(this,2);">
				<input type="file" id="file3" name="file3" onchange="loadImg(this,3);">
				<input type="file" id="file4" name="file4" onchange="loadImg(this,4);">
			</div>
			<br><br>
			<div align="center">
				<button type="submit">등록하기</button>
			</div>

		</form>
		<br><br>
		
		<script>

			$(function(){
				$("#file-area").hide();

				$("#titleImg").click(function(){
					$("#file1").click();
				});
				
				$("#contentImg1").click(function(){
					$("#file2").click();
				});
				
				$("#contentImg2").click(function(){
					$("#file3").click();
				});
				
				$("#contentImg3").click(function(){
					$("#file4").click();
				});
			});


			function loadImg(inputFile,num){
				// inputFile : 현재 변화가 생긴 input type="file" 요소 객체
				// num : 몇번째 input 요소인지 확인 후 해당 영역에 미리보기를 위한 매개변수  

				//files속성은 업로드된 파일의 정보를 배열의 형태로 묶어서 반환하는 속성
				//파일 선택시 length가 1을 반환 취소하면 0을 반환한다.
				console.log(inputFile.files);
				
				if(inputFile.files.length == 1){
					//선택된 파일이 존재하면
					//선택된 파일을 읽어서 해당 영역에 미리보기 띄워주기

					//파일을 읽어주기 위한 객체 FileReader 
					var reader = new FileReader();

					//파일을 읽어들이는 메소드 -어떤 파일을 읽을것인지 매개변수로 제시
					//readAsDataUrl(파일);
					//파일을 읽어들이는 순간 해당 파일만의 고유 url을 부여한다. 
					//부여된 url을 src 속성에 추가하면 된다.
					reader.readAsDataURL(inputFile.files[0]);

					//파일 읽기가 완료된 시점에 src에 url을 부여하는 함수 만들기
					reader.onload = function(e){
						//e는 이벤트 객체 
						//부여된 url은 e객체의 target에 result에 들어있음 
						//각 영역에 맞춰서 이미지 미리보기 
						switch(num){
							case 1 : $("#titleImg").attr("src",e.target.result); break;
							case 2 : $("#contentImg1").attr("src",e.target.result); break;
							case 3 : $("#contentImg2").attr("src",e.target.result); break;
							case 4 : $("#contentImg3").attr("src",e.target.result); break;

						}
					}
				}else{
					switch(num){
							case 1 : $("#titleImg").attr("src",null); break;
							case 2 : $("#contentImg1").attr("src",null); break;
							case 3 : $("#contentImg2").attr("src",null); break;
							case 4 : $("#contentImg3").attr("src",null); break;
						}
				}
			}
		</script>
	</div>
	
</body>	
	
	
</body>
</html>