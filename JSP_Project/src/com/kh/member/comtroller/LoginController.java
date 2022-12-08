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
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		 * <HttpServletRequest 객체와 HttpServletResponse 객체>
		 * -request: 서버로 요청할 때의 정보들이 담겨있다. (요청 시 전달값, 요청 전송 방식 등등)
		 * -response: 요청에 대해 응답할 때 필요한 객체들
		 * 
		 * <Get방식과 Post방식 차이>
		 * -Get 방식: 사용자가 입력한 값들이 url에 노출되고 데이터 길이 제한이 있다.
		 * -Post 방식: '' 노출되지 않고 데이터 길이 제한 없음
		 */
		
		//1) 전달값에 한글이 포함되어 있을 경우 인코딩 설정을 해야한다 (post 방식일 때)
		request.setCharacterEncoding("UTF-8");
		//2) 사용자가 요청시에 전달한 데이터를 꺼내어 변수 또는 객체화 시키기 (request에 parameter영역에 있는)
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		//3) 해당 요청을 처리하는 서비스 클래스의 메소드를 호출
		Member loginUser = new MemberService().loginMember(userId, userPwd);//로그인 회원 정보 또는 null
		
		//4) 처리된 결과를 가지고 사용자가 보게될 뷰 지정 후 포워딩 또는 url 재요청
		
		/*
		 * 응답페이지에 전달할 값이 있을 경우 값을 어딘가에 보관해서 전달해야함
		 * 그 데이터를 담아놓을 내장객체 4가지
		 * 1) application : application에 담은 데이터는 웹 애플리케이션 전역에서 꺼낼 수 있다.
		 * 2) session : session에 담은 데이터는 모든 jsp와 servlet에서 꺼낼 수 있다.
		 * 			  -한 번 담아놓은 데이터는 내가 직접 지우기 전까지, 서버가 멈추기 전까지, 브라우저가 종료되기 전까지,
		 * 			  -접근하여 사용할 수 있다.
		 * 3) request : request에 담은 데이터는 해당 request를 포워딩(forward)한 응답 jsp에서만 꺼낼 수 있다.
		 * 4) page : 해당 jsp 페이지에서만 꺼낼 수 있다.
		 * 
		 * -session과 request를 자주 사용하게 될 것
		 * 
		 * -객체들의 공통 메소드
		 * 	데이터를 꺼내기 위한 메소드 getAttribute("키");
		 * 	데이터를 넣기 위한 메소드 setAttribute("키","밸류");
		 *  데이터를 지우기 위한 메소드 remobeAttribute("키");
		 *  
		 */
		
		//로그인 실패
		if(loginUser==null) {//회원정보 없을 경우
			//에러 페이지로 응답
			request.setAttribute("errorMsg", "로그인에 실패했습니다.");
			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
//			HttpSession session = request.getSession();
//			session.setAttribute("alertMsg", "로그인 실패");
//			response.sendRedirect(request.getContextPath());
		}
		
		//로그인 성공
		else {//회원정보 있을 경우
			//index페이지 응답
			//로그인한 회원의 정보를 로그아웃하기 전까지는 유지 시키기 위해서 session객체에 담을 것
			
			//Servlet에서 JSP 내장객체인 session에 접근하고자 한다면 우선 session 객체를 얻어와야 한다.
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			session.setAttribute("alertMsg", "성공적으로 로그인 되었습니다.");
			
			//1. 포워딩 방식으로 응답 뷰 출력하기(forward 방식)
			//포워딩시에 선택한 페이지가 보여질 뿐 url매핑정보는 그대로 유지된다.
			//http://localhost:8889/jsp/login.me
//			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
//			view.forward(request, response);
			
			//2. url 재요청 방식(sendRedirect 방식)
//			response.sendRedirect("/jsp");
			//각 프로젝트의 context root(path)가 다를 수 있기 때문에
			//또는 변경될 수 있으니
			response.sendRedirect(request.getContextPath());
			
		}
		
	}

}





















