package com.kh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestPostServl0et
 */

@WebServlet("/test2.do")
public class RequestPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public RequestPostServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
//		System.out.println("포스트 요청");
//		요청시 전달된 데이터들은 request parameter 영역에 담긴다
		
		//post 방식 요청같은 경우 값을 뽑기전에 utf-8방식으로 인코딩 설정을 해야한다.
		//post 방식의 기본 인코딩 설정이 iso-8859-1 이기 때문에
		
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		int age =Integer.parseInt(request.getParameter("age"));
		String city = request.getParameter("city");
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));
		String foods[] = request.getParameterValues("food");
		
		System.out.println(name);
		System.out.println(gender);
		System.out.println(age);
		System.out.println(city);
		System.out.println(height);
		System.out.println(weight);
		System.out.println(String.join(", ", foods));
		
		// 요청 처리 : service - dao - sql문 - 다시 반환
		
		// 순수 Servlet: Java코드 내에 html을 작성
		// JSP (Java Server Page) : html 문서내에 Java코드를 쓸 수 있는 기술
		// 응답페이지를 만드는 과정을 jsp에게 위임
		
		// 단 위임하기 전에 응답화면에서 필요한 데이터를 request객체에 담아서 전달해줘야 한다.
		// request에 attribute 영역에 담아서 보낸다 (키와 밸류로)
		// request.setAttribute("키", "밸류");
		request.setAttribute("name", name);
		request.setAttribute("gender", gender);
		request.setAttribute("age", age);
		request.setAttribute("city", city);
		request.setAttribute("height", height);
		request.setAttribute("weight", weight);
		request.setAttribute("foods", foods);
		
		//위임시 필요한 객체: RequestDispatcher
		// 1) 응답하고자 하는 뷰(jsp)를 선택하면서 생성
		 
		RequestDispatcher view = request.getRequestDispatcher("/views/responsePage.jsp");
		
		// 2) 포워딩(떠넘기기/위임)
		view.forward(request, response);
		
	}
 
}




















