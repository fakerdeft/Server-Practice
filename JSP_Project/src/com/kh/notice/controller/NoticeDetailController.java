package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeDetailController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//클릭 시 해당 공지사항 번호
		int noticeNo = Integer.parseInt(request.getParameter("nno"));
	
		//해당 글을 클릭했을 때 상세조회가 되어야 되기 때문에 조회수도 같이 증가되어야 한다.
		
		//조회수 증가용 서비스
		Notice n = new NoticeService().increaseCount(noticeNo);
		
		if(n!=null) {
			request.setAttribute("notice", n);
			request.getRequestDispatcher("/views/notice/noticeDetailView.jsp").forward(request, response);
		}
		else {
			request.setAttribute("errorMsg", "조회가 정상적으로 수행 x");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
		}
	}

}
