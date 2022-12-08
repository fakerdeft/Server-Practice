package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.board.model.vo.Reply;
import com.kh.common.JDBCTemplate;
import com.kh.common.model.vo.PageInfo;

public class BoardService {

	public int selectListCount() {
		Connection conn = JDBCTemplate.getConnection();
		
		int count = new BoardDao().selectListCount(conn);
		
		JDBCTemplate.close(conn);
		
		return count;
	}

	//사용자 요청 페이지 목록 메소드
	public ArrayList<Board> selectList(PageInfo pi) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectList(conn,pi);
		
		JDBCTemplate.close(conn);
		
		return list;
				
	}
	
	//카테고리 목록 조회
	public ArrayList<Category> selectCategoryList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
		
		
	}

	public int insertBoard(Board b, Attachment at) {
		Connection conn = JDBCTemplate.getConnection();
		
		//게시글 정보 먼저 board 테이블에 insert 하기 
		int result = new BoardDao().insertBoard(conn,b);
		
		//첨부파일 추가시 돌아올 변수 선언 및 1로 초기화
		int result2 = 1;
		
		//첨부파일 객체가 비어있지 않으면 (즉, 첨부파일이 있다면)
		if(at != null) {
			result2 = new BoardDao().insertAttachment(conn,at);
		}
		
		int fr = result*result2;
		//트랜잭션 처리 
		
//		if(result>0 && result2>0) {
		if(fr>0) {
			//첨부파일이 없는 경우에 result2를 0으로 초기화 해둔다면 게시글이 insert가 성공했을 때도 
			//result2는 여전히 0이라서 rollback처리가 될것이기 때문에
			//result2의 초기값을 1로 초기화해둔다
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		return fr; //둘중 하나라도 실패하여 0값을 갖게 될경우 돌려주는 값도 실패값을 돌려주기 위해 곱셈결과 리턴 
	}

	//게시글 조회수 증가 메소드
	public int increaseCount(int bno) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().increaseCount(conn,bno);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//게시글 한개 조회 메소드
	public Board selectBoard(int bno) {
		Connection conn = JDBCTemplate.getConnection();
		
		Board b = new BoardDao().selectBoard(conn,bno);
		
		JDBCTemplate.close(conn);
		
		return b;
	}
	
	//첨부파일 조회 메소드 
	public Attachment selectAttachment(int bno) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		Attachment at = new BoardDao().selectAttachment(conn,bno);
		
		JDBCTemplate.close(conn);
		
		return at;
	}
	
	//게시글 수정 메소드
	public int updateBoard(Board b, Attachment at) {
		Connection conn = JDBCTemplate.getConnection();
		
		//게시글 정보 수정 
		int result = new BoardDao().updateBoard(conn,b);
		
		int result2 = 1;
		
		//새롭게 첨부된 파일이 있는 경우
		if(at!=null) {
		
			if(at.getFileNo()!=0) {//기존 첨부파일이 있었던 경우 - update
				result2 = new BoardDao().updateAttachment(conn,at);
			}else {//기존 첨부파일이 없었던 경우 - insert
				//기존 insertAttachment를 쓸수 없는 이유 : 기존에는 참조 게시글 번호를 게시글이 등록됨과
				//동시에 currentValue를 잡아줬기 때문에 이미 뽑혀있는 시퀀스 번호를 찾으려면 
				//전달받아서 진행해야한다. 
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
		}
		
		//게시글정보 수정과 첨부파일 정보수정이 둘다 올바르게 되었을때를 알수 있게 곱연산을 한다
		//만약 둘중 하나라도 실패하여 0이 나오면 result가 0이 되게끔
		int finalResult = result*result2;
		
		
		if(finalResult>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return finalResult;
	}

	//게시글 삭제 메소드
	public int deleteBoard(int boardNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result =new BoardDao().deleteBoard(conn,boardNo);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int insertThumbnail(Board b, ArrayList<Attachment> list) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertThumbnailBoard(conn, b);
		int result2 = new BoardDao().insertThumbnailAttachment(conn,list);
		
		
		int finalResult = result*result2;
		
		if(finalResult>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return finalResult;
	}

	public ArrayList<Board> selectThumbnailList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}
	
	//사진게시판 상세조회시 첨부파일 리스트 조회 메소드
	public ArrayList<Attachment> selectAttachmentList(int bno) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Attachment> list = new BoardDao().selectAttachmentList(conn,bno);
		
		JDBCTemplate.close(conn);
		
		return list;
	}

	public int insertReply(Reply r) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertReply(conn,r);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	//댓글 리스트 조회 메소드
	public ArrayList<Reply> selectReplyList(int bno) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Reply> list = new BoardDao().selectReplyList(conn,bno);
		
		JDBCTemplate.close(conn);
		
		return list;
		
		
	}

	public ArrayList<Board> selectTopList() {
		
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Board> list = new BoardDao().selectTopList(conn);
		
		JDBCTemplate.close(conn);
		
		return list;
	}


}
