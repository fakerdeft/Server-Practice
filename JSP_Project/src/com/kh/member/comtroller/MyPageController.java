package com.kh.member.comtroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MyPageController
 */
@WebServlet("/mypage.me")
public class MyPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MyPageController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//url직접 요청도 가능하기 때문에
		//로그인 전 요청시에 메인페이지를 띄우면서 alert 메세지로 로그인 후 가능한 서비스
		
		//로그인 시에 세션에 로그인 유저 정보를 담아놨기 때문에 로그인 유저 정보가 있는지 없는지 판단하여
		//로그인 유무를 확인한다.
		HttpSession session = request.getSession();		
		
		if(session.getAttribute("loginUser")==null){
			session.setAttribute("alertMsg", "로그인 후 가능한 서비스 입니다.");
		
			response.sendRedirect(request.getContextPath());
		}
		else {//로그인 후 요청시엔 myPage.jsp로 포워딩 시키기
			request.getRequestDispatcher("views/member/myPage.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
		
	}

}


















