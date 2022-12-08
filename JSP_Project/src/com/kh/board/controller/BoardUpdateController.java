package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardUpdateController
 */
@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardUpdateController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// multipart/form-data 로 전송이 되었는지 확인
		if (ServletFileUpload.isMultipartContent(request)) {
			// 전송파일 용량 제한 (int maxSize)
			int maxSize = 10 * 1024 * 1024;

			// 전달된 파일을 저장시킬 폴더의 물리적인 경로 알아내기 (String savePath)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");

//		전달된 파일명을 수정작업 후 서버에 업로드 
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
					new MyFileRenamePolicy());

			// multipartRequest로 변환하였으니 해당 객체로 전달받은 데이터 꺼내기
			int boardNo = Integer.parseInt(multiRequest.getParameter("boardNo"));
			String boardTitle = multiRequest.getParameter("boardTitle");
			String boardContent = multiRequest.getParameter("boardContent");
			String category = multiRequest.getParameter("category");

			Board b = new Board();
			b.setBoardNo(boardNo);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setCategory(category);

			// 만약 전달된 새로운 첨부파일이 있다면 (첨부파일 수정)
			Attachment at = null;

			// 새로 전달된 파일이름이 있을때
			if (multiRequest.getOriginalFileName("reUpfile") != null) {
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("/resources/board_upfiles/");

				// 만약 기존에도 첨부파일이 있는 경우 보냈던 파일번호랑 수정명을 받아준다
				if (multiRequest.getParameter("originFileNo") != null) {
					// 새로 첨부된 파일이 있고, 기존에도 파일이 있는 경우
					// update Attachment - 기존의 파일 고유번호를 이용하여 update한다.
					at.setFileNo(Integer.parseInt(multiRequest.getParameter("originFileNo")));

					// 새로운 첨부파일이 있다면 기존에 있던 첨부파일은 더이상 필요없으니 서버에서 삭제한다.
					new File(savePath + multiRequest.getParameter("originFileName")).delete();

				} else {// 새로운 첨부파일은 있지만 기존엔 첨부파일이 없을 경우
						// insert Attachment - 기존에 파일이 없었으니 새로운 파일정보를 insert한다.
						// 새로 추가하려면 참조된 게시글의 번호 refbno 를 가지고 가야한다.
					at.setRefBno(boardNo);
				}

			}
			
			//service로 요청보내기 
			//새로운 첨부파일이 없는 경우 - b,null : board update
			//새로운 첨부파일이 있고 기존 첨부파일이 있는 경우 -b,at(file_no) : board-update,at-update 
			//새로운 첨부파일이 있고 기존 첨부파일은 없는 경우 -b,at(refBno) : board-update,at-insert
			int result = new BoardService().updateBoard(b,at);
			
			if(result>0) {
				//성공시엔 게시글 상세 페이지로 재요청
				request.getSession().setAttribute("alertMsg","게시글 수정 성공");
				response.sendRedirect(request.getContextPath()+"/detail.bo?bno="+boardNo);
			}else {
				request.setAttribute("errorMsg", "게시글 수정 실패");
				request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
			}
		}

	}

}
