package com.kh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.JDBCTemplate;
import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	public ArrayList<Notice> selectNoticeList() {
		Connection conn = JDBCTemplate.getConnection();		
		
		ArrayList<Notice> list = new NoticeDao().selectNoticeList(conn);
		
		//select문 실행해서 트랜잭션 처리 필요없음
		JDBCTemplate.close(conn);
	
		return list;
	}
	
	public ArrayList<Notice> insertNotice(Notice n) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().insertNotice(conn, n);
		
		ArrayList<Notice> updateList = null;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
			updateList = new NoticeDao().selectNoticeList(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateList;
	}

	public Notice increaseCount(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().increaseCount(conn, noticeNo);
		Notice n = null;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
			//조회수 증가가 이루어졌으니
			//해당 글을 조회해오면 된다.
			n = new NoticeDao().selectNotice(conn, noticeNo);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return n;
		
	}
	
	//공지글 하나만 가져오는 메소드
	public Notice selectNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
	
		Notice n = new NoticeDao().selectNotice(conn, noticeNo);
		
		JDBCTemplate.close(conn);
		
		return n;
	}
	
	public int updateNotice(String title, String content, int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().updateNotice(conn,title,content,noticeNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().deleteNotice(conn, noticeNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
}
