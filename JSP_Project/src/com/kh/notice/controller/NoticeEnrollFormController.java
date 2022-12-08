package com.kh.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeEnrollFormController
 */
@WebServlet("/enrollForm.no")
public class NoticeEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public NoticeEnrollFormController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//url에 디렉토리 구조를 노출시키지 않기 위해 매핑값을 가진 채로 포워딩 작업
		request.getRequestDispatcher("/views/notice/noticeEnrollForm.jsp").forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
