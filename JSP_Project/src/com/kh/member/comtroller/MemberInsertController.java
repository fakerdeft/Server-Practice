package com.kh.member.comtroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//회원가입 폼에서 넘어온 데이터 변수 처리하기
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interests = request.getParameterValues("interest");
		String interest = "";
		
		if(interests != null) {
			interest = String.join(",", interests);
		}
		
		Member m = new Member(userId,userPwd,userName,phone,email,address,interest);
		
		int result = new MemberService().insertMember(m);
		
		//회원가입 성공시 index페이지
		if(result>0) {
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "회원가입이 완료 되었습니다.");
			response.sendRedirect(request.getContextPath());
		}
		
		//회원가입 실패시 error 페이지에 회원가입 실패 메세지 띄우기
		else {
			request.setAttribute("errorMsg", "회원가입에 실패했습니다.");			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
		
	}

}














