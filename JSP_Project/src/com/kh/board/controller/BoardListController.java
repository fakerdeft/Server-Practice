package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.model.vo.PageInfo;

/**
 * Servlet implementation class BoardListController
 */
@WebServlet("/list.bo")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardListController() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//페이징 처리 
	int listCount;  //현재 총 게시글 개수
	int currentPage; //현재 페이지(즉,사용자가 요청한 페이지)
	int pageLimit; // 페이지 하단에 보여질 페이징바의 페이지 최대 개수
	int boardLimit; // 한 페이지에 보여질 게시글의 최대 개수 (몇개 단위씩 보여질건지)
	
	int maxPage;  //가장 마지막 페이지가 몇번페이지 인지 (총 페이지수)
	int startPage; //페이지 하단에 보여질 페이징바의 시작수
	int endPage; //페이지 하단에 보여질 페이징바의 끝수
	
	// listCount : 현재 총 게시글의 개수 
	listCount = new BoardService().selectListCount();
	
	//currentPage : 현재 페이지 (사용자가 요청한 페이지)
	currentPage = Integer.parseInt(request.getParameter("currentPage"));
	
	//pageLimit : 페이지 하단에 보여질 페이징 바 최대 개수 
	pageLimit = 10;
	
	//boardLimit : 한페이지에 게시글 몇개씩 보여줄것인지
	boardLimit = 10;
	
	/*
	maxPage : 가장 마지막 페이지가 몇번페이지인지 (총 페이지수)
	
	listCount와 boardLimit에 영향을 받는다 
	
	게시글이 107개이고 boardLimit이 10이면 
	10페이지까지는 가득차고 나머지 한페이지에 7개의 게시글이 표현되어야 하기때문에
	총 11페이지가 나온다 : maxPage = 11
	
	총 개수 		boardLimit 				maxPage
	100개	 /		10		=10.0		  10
	101개 	 /		10		=10.1		  11
	109개	 /		10		=10.9		  11
	111개 	 /		10		=11.1		  12
	
	총개수 / boardLimit 나온 결과를 올림처리 해주면 maxPage값이 나온다.
	
	1)listCount를 double자료형으로 변환
	2)listCount / boardLimit
	3)결과에 대해 올림처리 Math.ceil() 메소드로 올림
	4)결과값을 다시 int 자료형으로 변환 
	
	*/
	
	maxPage=(int)(Math.ceil(((double)listCount / boardLimit)));
	
	/*
	 * startPage : 페이징바의 시작수
	 * 
	 * pageLimit이 10일때
	 * 
	 * startPage : 1,11,21,31,41...... pageLimit의 배수 + 1
	 * 
	 * n * pageLimit + 1
	 * 
	 * -pageLimit이 10일때 
	 * 
	 * currentPage 		startPage
	 * 		1  				1
	 * 		5				1
	 * 		10				1
	 * 
	 * 		11				11
	 * 		15				11
	 * 		
	 * 		21				21
	 * 		25				21
	 * 		29				21
	 * 
	 * 	currentPage : 1~10 - starPage : 1	(10-1) =   9  /10 0 * 10 +1  = 1
	 *  currentPage : 11~20 - starPage : 11	 (11-1) =  10 /10 1 * 10 + 1 = 11
	 *  currentPage : 21~30 - starPage : 21   (25-1)=  21 /10 2 * 10 + 1 = 21
	 * 				1 -1 0 /10 0 * 0 +1
	 * 				10 -1  9 /10 0 * 10 +1 =1
	 * 				11 -1 10 /10 1 * 10 + 1 = 11
	 * 				25 -1 21 /10 2 * 10 + 1 = 21 
	 * 
	 * startPage = (currentPage-1)/pageLimit * pageLimit + 1
	 * 
	 * startPage 1  -  endPage 10
	 * startPage 11  - endPage 20
	 * startPage 21  - endPage 30
	 * 
	 * endPage = startPage + pageLimit - 1 
	 * 
	 * 
	 */
	startPage = (currentPage-1)/pageLimit * pageLimit + 1;
	
	endPage = startPage + pageLimit - 1; 
	
	//startPage가 11일때 endPage는 20으로 된다 (만약 maxPage가 13이라면?)
	//endPage를 maxPage로 변경
	if(endPage>maxPage) {
		endPage = maxPage;
	}
	
	//페이징 처리 구문 VO 생성자에 넣고 생성시에 작업 되도록
//	PageInfo pi2 = new PageInfo(currentPage);
	
	//페이지 정보들을 하나의 공간에 담아서 보내자
	//페이지 정보들을 담아줄 VO 생성
	//-공지사항에도 사용가능 사진게시판에서도 사용 가능 common 패키지에 생성
	
	//1. 페이징바를 만들기 위해 필요한 객체
	PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,
								maxPage,startPage,endPage);
	
	//2.현재 사용자가 요청한 페이지에 대해 보여질 게시글 리스트 조회해오기 
	ArrayList<Board> list = new BoardService().selectList(pi);
	
	request.setAttribute("list", list);
	request.setAttribute("pi", pi);
	
	request.getRequestDispatcher("/views/board/boardListView.jsp").forward(request, response);
	
	}
	
}









