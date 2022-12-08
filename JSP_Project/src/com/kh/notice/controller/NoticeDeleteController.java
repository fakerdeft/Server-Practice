package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeDeleteController
 */
@WebServlet("/deleteForm.no")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeDeleteController() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//삭제하기
		//공지삭제 성공시 alertMsg로 공지사항 삭제 성공 메세지 띄우면서 공지사항 리스트 보여주기
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
		//실패시 에러페이지에 공지사항 삭제 실패 메세지 띄우기
		int result = new NoticeService().deleteNotice(noticeNo);
		
		//공지 삭제 성공시 alertMsg로 공지사항 삭제 성공 메세지 띄우면서 공지사항 리스트 보여주기
		if(result>0) {
			request.getSession().setAttribute("alertMsg", "공지사항 삭제 성공");
			response.sendRedirect(request.getContextPath()+"/list.no");
		}else {
			//실패시 에러페이지에 공지사항 삭제 실패 메세지 띄우기 
			request.setAttribute("errorMsg", "공지사항 삭제 실패");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
		
		
		
	}


}
