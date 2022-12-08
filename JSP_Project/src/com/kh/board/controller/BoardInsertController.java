package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BoardInsertController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
//		int userNo = Integer.parseInt(request.getParameter("userNo"));
//		String category = request.getParameter("category");
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		String upfile = request.getParameter("upfile");

//		System.out.println(userNo);
//		System.out.println(category);
//		System.out.println(title);
//		System.out.println(content);
//		System.out.println(upfile);
		
		//폼 전송을 일반 방식이 아니라 multipart/form-data 로 전송하는 경우
		//request로부터 값을 뽑을 수 없다.
		//multipart라는 객체에 담아서 사용해야 한다.
		
		//enctype이 multipart/form-data로 전송되었는지 확인하는 작업
		
		if(ServletFileUpload.isMultipartContent(request)) {
//			파일 업로드를 위한 라이브러리 cos.jar
		
//			1.전송되는 파일을 처리할 작업내용(전송되는 파일의 용량제한, 전달된 파일을 저장할 경로)
//			1_1. 정송파일 용량제한(int maxSize = byte단위의 값을 기술하여 제한한다.)
//			
//			단위정리
//			byte > kbyte > mbyte > gbyte - tbyte > ...
//			1kbyte = 1024byte
//			1mbyte = 10241kbyte

			int maxSize = 10 * 1024 * 1024; //10mbyte로 지정
			
//			1_2. 전달된 파일을 저장할 서버의 경로를 알아내기
//			세션객체에 있는 getRealPath 메소드를 통해 알아내기
//			현재 구동되고 있는 웹 어플리케이션을 기준으로 경로를 잡아줘야 한다. webcontent 뒤에 오는 경로는 따로 지정을 해줘야한다.
//  		결론적으로 해당 board_upfiles폴더 내부에 파일들이 저장될 것이기 때문에 경로 맨뒤에 /를 붙여준다.
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/");
//			System.out.println(savePath);
			
		//  2.기존 request객체로는 파일전달을 받을 수 없기 때문에 MultipartRequest 객체로 변환하는 작업을 해줘야한다.
		//  [표현법]
		//	MultipartRequest multiRequest = new MultipartRequest(request객체, 저장폴더경로, 용량제한, 인코딩값, 파일명 수정객체);
		//  위에 한 줄 작성만 한다면 업로드는 문제없이 진행된다. 보통 사용자가 전달한 파일명을 그대로 서버에 올리지 않음
		//  기본적으로 파일명을 수정해주는 객체를 제공하고 있지만 변경이 단조롭고 한글처리를 따로 해주지 않음 또는 서버에 따라 한글, 특문, 띄어쓰기 등이 포함된 경우
		//  제대로 처리가 되지 않을 가능성이 있기 때문에 별도로 파일명수정 작업을 거쳐줘야 한다.
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
		//  3. DB에 기록할 데이터들을 뽑아서 Attachment 객체에 담기
		//	-카테고리 번호, 제목, 내용, 작성자회원번호 뽑아 Board테이블에 insert 해야하고
		//	-만약 넘어온 첨부파일이 있다면, 원본명, 수정명, 폴더경로를 뽑아서 Attachment 테이블에 insert 해야한다.
			
//			사용자로부터 전달된 파라미터들도 전송형식이 multipart/form-data 형식이기 때문에 기존 request 객체로는 전달받을 수 없음
//			multipartRequest 객체로 변환한뒤에 전달받은 데이터를 추출해야 한다.
			String category = multiRequest.getParameter("category");
			String title = multiRequest.getParameter("title");
			String content = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board ();
			b.setCategory(category);
			b.setBoardTitle(title);
			b.setBoardContent(content);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null; //처음에는 null로 초기화, 첨부파일이 있을 수도 없을 수도 있기 때문에 있다면 그때그때 객체 생성해서 전달
			
			//multiRequest.getoriginalFileName("키");
			//-첨부파일이 있을 경우 원본명 / 없을 경우 null을 반환한다.
			
			if(multiRequest.getOriginalFileName("upfile") != null) {
				//첨부파일이 있는 경우
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));//원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile"));//실제 서버에 업로드 되어있는 파일명
				at.setFilePath("resources/board_upfiles/");
			}

			new File(savePath+"열쩡.jpg").delete();
			
			//4. 서비스 요청
			int result = new BoardService().insertBoard(b,at);
			
			if(result>0) {
				//성공시에는 가장 최신글로 등록이 될테니 게시글 목록 첫페이지 띄워주기
				HttpSession session = request.getSession();
				session.setAttribute("alertMsg", "성공");
				response.sendRedirect(request.getContextPath()+"/list.bo?currentPage=1");
			}
			else {
				//실패시에는 업로드된 첨부파일을 가지고 있을 필요가 없기 때문에 삭제작업을 해줘야한다.
				if(at != null) {//첨부파일이 있을 때(즉, db서비스 요청에 실패했는데 첨부파일이 있을 때)
					//삭제하고자 하는 파일 객체를 생성하여 delete 메소드를 호출해서 삭제한다.
					System.out.println("지우는 파일 : "+savePath+at.getChangeName());
					new File(savePath+at.getChangeName()).delete();
				}

				//실패시 에러메세지에 에러메세지와 함께 보내주기
				request.setAttribute("errorPage", "게시글 작성 실패");
				request.getRequestDispatcher("/views/common/errorPage.jsp").forward(request, response);
			}
		}
	}
}



















