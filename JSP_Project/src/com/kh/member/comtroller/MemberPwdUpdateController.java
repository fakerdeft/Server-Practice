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
 * Servlet implementation class MemberPwdUpdateController
 */
@WebServlet("/updatePwd.me")
public class MemberPwdUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberPwdUpdateController() {
        super();
 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//view에서 진행할 부분 = 변경할 비번과 비번확인이 동일한지 함수사용해서 진행
		
		//아이디 비밀번호 / 변경할 비밀번호를 들고 service dao 다녀오기
		//조건은 아이디 비밀번호 확인된 데이터 변경
		//modify_date = sysdate
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginUser");
		String userPwd = request.getParameter("userPwd");
		String userPwd2 = request.getParameter("updatePwd");
		
		Member result = new MemberService().updatePwd(m,userPwd,userPwd2);
		
		//성공 실패 전부 alertMsg로 메세지 넣고
		//성공시에는 변경된 유저 정보 조회해와서 세션에 갱신하기
		//마이페이지로 재요청 보내기
		
		if(result!=null) {
			session.setAttribute("loginUser", result);
			session.setAttribute("alertMsg", "비밀번호 변경 성공");
			response.sendRedirect(request.getContextPath()+"/mypage.me");
		}

		else {
			session.setAttribute("alertMsg", "비밀번호 변경 실패");
			response.sendRedirect(request.getContextPath()+"/mypage.me");
		}
		
	}
	
}
