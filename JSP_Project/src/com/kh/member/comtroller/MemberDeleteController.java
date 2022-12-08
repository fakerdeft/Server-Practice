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
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MemberDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		//로그인한 회원 정보를 얻어내는 방법
		//방법 1. view에서 form태그에 input type hidden으로 숨겨서 넘겨주기
		//String userId = request.getParameter("userId");
		//방법 2. 세션에 있는 로그인 회원 정보 꺼내기
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginUser");
		String userId = m.getUserId();
		
		//비밀번호는 사용자가 탈퇴시에 입력한 비밀번호를 들고 와야 성공 실패 가능
		String userPwd = request.getParameter("userPwd");
		
		
		
		//회원의 아이디와 비밀번호를 가지고 
		//service - dao 다녀오기
		//sql 구문은 update 구문으로 status 컬럼값을 N 으로 변경하기
		
		int deleteUser = new MemberService().deleteMember(userId,userPwd);
		
		//성공시
		//변경에 성공했으면 성공적으로 회원 탈퇴 되었습니다 메세지를 alertMsg에 세팅
		//로그아웃처리를 해주면서 메인 페이지로 이동 (재요청)
		if(deleteUser>0) {
			session.removeAttribute("loginUser");
			session.setAttribute("alertMsg", "회원탈퇴 성공");
			response.sendRedirect(request.getContextPath());
		}
		
		//실패시
		//에러페이지에 errorMsg 메시지 회원탈퇴 실패 보내고 포워딩
		else {
			request.setAttribute("errorMsg", "회원탈퇴 실패");			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
		
	}

}












