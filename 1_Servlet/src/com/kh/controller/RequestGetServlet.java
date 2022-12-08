package com.kh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestGetServlet
 */
@WebServlet("/test1.do")
public class RequestGetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestGetServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Get 방식으로 요청했다면 doGet 메소드가 호출된다
		 * 
		 * 첫번째 매개변수인 HttpServletRequest request 에는 요청시 전달된 내용들이 담긴다
		 * 	- 사용자가 입력한 값, 요청 전송 방식, 요청한 사용자의 ip등등..
		 * 두번째 매개변수인 HttpServletResponse response 에는 요청 처리 후 응답을 할 때 사용하는 객체
		 * 
		 * 우선, 요청을 처리하기 위해 요청시 전달된 값(사용자 입력값) 들을 뽑는다
		 * request의 parameter 영역 안에 존재 -> key - value 세트로 담겨있다 (name값 value값)
		 * 
		 * 따라서 request의 parameter 영역으로부터 전달된 데이터를 뽑는 메소드
		 * -request.getParameter("키값"): String(그에 해당하는 value값) 
		 * 		-> 무조건 문자열로 반환되기 때문에 다른 자료형으로 받고자 한다면 파싱 해야 한다.
		 * -request.getParameterValues("키값"): String[](그에 해당하는 value값들) 
		 * 		-> 하나의 key값으로 여러개의 value들을 받을 경우(체크박스) 문자열 배열 형태로 반환됨.
		 * 
		 */
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		int age= Integer.parseInt(request.getParameter("age"));
		String city = request.getParameter("city");
		double weight = Double.parseDouble(request.getParameter("weight"));
		double height = Double.parseDouble(request.getParameter("height"));
		String[] foods = request.getParameterValues("food");
		
		System.out.println(name);
		System.out.println(gender);
		System.out.println(age);
		System.out.println(city);
		System.out.println(height);
		System.out.println(weight);
		System.out.println(String.join(",",foods));
		if(foods==null) {
			System.out.println("foods: 없음");
		}
		else {
			//String join: 문자열 배열을 구분자와 함께 하나의 문자열로 만들어주는 메소드
			System.out.println("좋아하는 음식: "+String.join(",", foods));
		}
		
		//뽑아낸 값들을 가지고 요청 처리하기 (DB와 상호작용)
		//기본 흐름: service의 메소드 호출하며 뽑은 값들 전달 - DAO호출 및 전달
		//			-DB구문 실행 - 결과 반환
		
//	int result = new MemberService().insertMember(name, gender, age, city, height, foods)
//	service나 dao같은 경우는 jdbc프로젝트때 했던 내용과 거의 흡사하니 복습하기
		
		//위와 같은 처리를 했다고 가정하고 해당 결과값으로 응답처리 해보기
		
		//응답 페이지 만들기 (자바코드로)
		
		//장점: Java 코드 내에 작성하기 때문에 자바문법들 활용 가능(조건문, 반복문, 메소드들 등등)
		//단점: 복잡, 혹시라도 후에 html을 수정하려면 java코드내에서 수정이 이루어져야 하기 때문에
//				수정된 내용을 다시 반영시키려면 서버를 재시작하여야 한다.
		
		//response 객체를 통해서 사용자에게 html(응답화면) 전달
		
		//1) 이제부터 내가 출력할 내용은 문서형태 html이 고 문자셋 UTF-8
		response.setContentType("text/html; charset=UTF-8");
		
		//2) 응답하고자 하는 사용자(요청했던 사용자)와의 스트림(클라이언트와의 통로) 생성
		PrintWriter out = response.getWriter();
		
		//3) 생성된 스트림을 통해서 한줄씩 응답 html구문을 작성해서 출력
		out.println("<html>");
		out.println("<head>");
		
		out.println("<style>");
		out.println("h2{color: red}");
		out.println("</style>");
		out.println("<title>하하하</title>");
		out.println("</head>");
		
		out.println("<body>");
		
		out.println("<h2>개인정보 응답화면</h2>");
		out.println("<span> 이름: "+name+"</span><br>");
		
		out.printf("<span> 성별은: %s </span>", gender);
		if(gender==null) {
			out.println("선택x");
		}
		else {
			if(gender.equals("M")) {
				out.println("<span>남자입니다</span><br>");
			}
			else {
				out.println("<span>여자입니다</span><br>");
			}
		}
		out.printf("<span> 나이는: %d </span><br>", age);
		out.printf("<span> 사는 도시는: %s</span><br>", city);
		out.printf("<span> 키는: %.1f </span><br>", height);
		out.printf("<span> 몸무게는: %.1f </span><br>", weight);
		out.print("좋아하는 음식은");
		if(foods==null){
	        out.print("선택x");
	    }
	    else{
	        out.print("<ul>");
	            for(int i=0; i<foods.length; i++){
	                out.printf("<li>%s</li>",foods[i]);
	            }
	            out.print("</ul>");
	    }
		
		out.println("</body>");
		out.println("</html>");
		
	}
	
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}














