package com.kh.board.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class ThumbnailInsertController
 */
@WebServlet("/insert.th")
public class ThumbnailInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThumbnailInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			
			//용량 제한
			int maxSize = 10 * 1024 * 1024;
			//파일 경로
			String savePath = request.getServletContext().getRealPath("/resources/thumbnail_upfiles/");
			
			//파일명수정작업+multiRequest전환 및 파일 업로드
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize,"UTF-8",new MyFileRenamePolicy());
			
			//db에 등록할 데이터 꺼내주기 
			//Board에 넣을 데이터
			Board b = new Board();
			b.setBoardTitle(multiRequest.getParameter("title"));
			b.setBoardContent(multiRequest.getParameter("content"));
			b.setBoardWriter(multiRequest.getParameter("userNo"));
			
			//Attachment에 넣을 데이터 
			//첨부파일이 여러개 넘어올 수 있기 때문에 ArrayList에 담아주기
			//적어도 1개이상은 담긴다 (대표이미지는 필수사항이라서)
			ArrayList<Attachment> list = new ArrayList<>();
			
			for(int i=1; i<=4; i++) {//name값이 file1,file2,file3,...이기때문에 인덱스를 활용해서 키값 활용 
				
				//키값
				String key = "file"+i;
				
				//키값에 해당하는 input file요소에서 넘어온 파일이 있는지(파일이름이 있는지)
				if(multiRequest.getOriginalFileName(key) != null) {
					//첨부파일이 있으면 
					//Attachment 객체를 생성해서 데이터 담고 list에 추가하기
					//원본명,수정명,폴더경로,파일레벨
					
					Attachment at = new Attachment();
					at.setOriginName(multiRequest.getOriginalFileName(key));
					at.setChangeName(multiRequest.getFilesystemName(key));
					at.setFilePath("/resources/thumbnail_upfiles/");
					
					if(i==1) {//대표이미지
						at.setFileLevel(1);
					}else {//상세이미지
						at.setFileLevel(2);
					}
					
					//at 객체 데이터 세팅이 끝났으니 list에 추가해주기
					list.add(at);
				}
			}
				
				int result = new BoardService().insertThumbnail(b,list);
				
				
				if(result>0) {
					//성공시 사진게시판 목록 띄워주기
					request.getSession().setAttribute("alertMsg", "사진게시글 작성 성공");
					response.sendRedirect(request.getContextPath()+"/list.th");
				}else {
					//실패시 에러페이지
					request.setAttribute("errorMsg", "사진게시글 작성 실패");
					request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
				}
		}
	
	}

}
