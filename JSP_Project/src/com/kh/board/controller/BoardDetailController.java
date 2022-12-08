package com.kh.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;

/**
 * Servlet implementation class BoardDetailController
 */
@WebServlet("/detail.bo")
public class BoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		//조회수 증가 / 조회수증가가 성공적으로 이루어졌다면 게시글번호로 게시글 조회 / 게시글 번호로 첨부파일 조회
		int result = new BoardService().increaseCount(bno);
		
		//조회해온 데이터 담아서 상세보기 페이지로 포워딩 
		if(result>0) {
			//게시글 정보 가지고 오기
			Board b = new BoardService().selectBoard(bno);
			//첨부파일 정보 가지고 오기 
			Attachment at = new BoardService().selectAttachment(bno);
			
			
			request.setAttribute("b", b);
			request.setAttribute("at", at);
			
			request.getRequestDispatcher("/views/board/boardDetailForm.jsp").forward(request, response);
			
		}else {
			//실패시 에러페이지 
			request.setAttribute("errorMsg", "게시글 조회 실패");
			request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
			
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
