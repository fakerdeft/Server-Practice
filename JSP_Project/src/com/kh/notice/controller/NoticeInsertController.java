package com.kh.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeInsertController() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//post 요청은 encoding 설정을 해줘야한다. (기본 인코딩이 utf-8이 아니기 때문에)
		request.setCharacterEncoding("UTF-8");
		//userNo(글 작성자)를 얻기 위한 방법 2가지
		
		//방법 1) input hidden 으로 숨겨서 넘기기
		String noticetitle = request.getParameter("title");
		String noticecontent = request.getParameter("content");
		int userNo = Integer.parseInt(request.getParameter("userNo"));
		
		//방법 2) session loginUser에서 꺼내기
//		int userNo2 = ((Notice)request.getSession().getAttribute("loginUser")).getUserNo();
		
		
		Notice n = new Notice(noticetitle,noticecontent,userNo);
		
		ArrayList<Notice> result = new NoticeService().insertNotice(n);
		
		//service - dao 다녀와서 성공시에는 list.no로 목록띄우기
		if(result != null) {
			
			request.setAttribute("list",result);
			request.getRequestDispatcher("/views/notice/noticeListView.jsp").forward(request, response);
		}
		
		//실패시에는 errorPage에 공지사항 등록 실패 메세지 띄워주기
		else {
			request.setAttribute("errorMsg", "공지사항 등록 실패");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
		
	}

}






