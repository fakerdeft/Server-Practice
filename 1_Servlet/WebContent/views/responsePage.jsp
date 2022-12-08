<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%  //<% % >: 선언부 작성 공간
	//<%= % >: 이 영역은 값을 꺼내는 공간 = 표현식 

	//이 영역을 스크립틀릿이라고 하여 html 문서 내에 자바코드 작성 가능 영역
    //현재 이 jsp에서 필요로 하는 데이터들 -> request의 attribute 영역에 담아서 위임 받음 
    //위임받은 데이터 필요시 request의 attribute영역에서 꺼내서 사용
    //request.getAttrivute("키값") : Object (그에 해당하는 밸류값의 자료형)
    //Object 형식에서 내가 받고자 하는 자료형으로 강제형변환 하여 사용.
    
    String name = (String)request.getAttribute("name");
    int age = (int)request.getAttribute("age");
    String gender = (String)request.getAttribute("gender");
    String city = (String)request.getAttribute("city");
    double height = (double)request.getAttribute("height");
    double weight = (double)request.getAttribute("weight");
    String[] foods = (String[])request.getAttribute("foods");
%>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here
.</title>
</head>
<body>

	<h2>개인정보 응답화면</h2>
	<span>이름 <%= name %></span><br>
	성별은 :
	<% if(gender==null){ %>
		선택을 하지 않았습니다
	<% }
	else{ %>
		<% if(gender.equals("M")){ %>
			<span>남자입니다.</span><br>
		<% }
		else{ %>
			<span>여자입니다.</span><br>
	<% 	}%>
	<% }%>
	
	<span>나이는 : <%= age %></span><br>
	<span>사는 도시는: <%=city %></span><br>
	<span>키는: <%= height %> </span><br>
	<span>몸무게는:<%=weight %> </span><br>
	 좋아하는 음식은
	 <%if(foods==null){ %>
		없습니다.
	 <%}
	   else{ %>
		<ul>
		<%for(int i=0;i<foods.length;i++){%>
		<li><%=foods[i] %></li>
		<%} %>
		</ul>
	 <%} %>
</body>
</html>