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
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
		
    public MemberUpdateController() {
        super();
        
    }
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1)인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		//2)요청시 전달한 데이터 꺼내서 변수화 및 가공처리
		//userId를 disabled로 사용하지 않겠다고 해놓았으니
//		String userId = request.getParameter("userId");
		//위 방법으로는 데이터가 넘어오지 않는다
		
		//방법2) session에 로그인되어있는 정보가 있을테니 해당 정보에서 꺼내오기
		HttpSession session = request.getSession();
		Member m = (Member)session.getAttribute("loginUser");
		String userId = m.getUserId();
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		String interest = "";
		if(request.getParameterValues("interest") != null) {
			interest = String.join(",", request.getParameterValues("interest"));
		}
		
		//전달받은 데이터를 가지고 service - dao 다녀오기
		m.setUserName(userName);
		m.setPhone(phone);
		m.setEmail(email);
		m.setAddress(address);
		m.setInterest(interest);
		
		Member updateUser = new MemberService().updateMember(m);
		
		//처리된 결과를 가지고 사용자가 보게될 응답 뷰 지정하기
		//서비스단에서 dao에서 처리된 결과 행수를 가지고 (MODIFY_DATE에  sysdate넣기)
		
		//성공 실패여부를 통해 성공했을 시에는 commit하고
		//변경된 회원의 정보를 조회해서 객체에 담아 돌아온 뒤
		
		//해당하는 회원의 정보를 마이페이지에 다시 뿌려주기(세션에 loginUser 갱신).
		//다시 조회해온 회원정보를 세션에 loginUser에 담아주기
		
		//성공시 alertMsg에 회원정보 수정 성공 메세지 담고 메인페이지로 재요청
		if(updateUser!=null) {
			session.setAttribute("loginUser", updateUser);
			session.setAttribute("alertMsg", "정보수정이 완료 되었습니다.");
			response.sendRedirect(request.getContextPath());
		}
		
		//실패시 errorPage에 회원정보 수정 실패 메세지 포워딩
		else {
			request.setAttribute("errorMsg", "정보수정에 실패했습니다.");			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
		}
		
	}
	
}













