package com.kh.member.model.service;

import java.sql.Connection;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	//db에 연결할 connection 객체와 transaction 처리 하기 위한 파트

	public Member loginMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
	
		//db접속 정보를 담은 connection을 생성했으니 해당 객체와 사용자에게 넘겨받은 데이터를 가지고
		//db에 sql문 실행시키기 위해서 DAO 호출하기
		Member m = new MemberDao().loginMember(conn,userId,userPwd);
		
		//자원반납 (select 문으로 조회했으니 트랜잭션처리 필요없음)
		JDBCTemplate.close(conn);
		
		return m;
	
	}

	public int deleteMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
	
		int result = new MemberDao().deleteMember(conn,userId,userPwd);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public int insertMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().insertMember(conn,m);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public Member updateMember(Member m) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn,m);
		
		Member updateUser = null;
		if(result>0) {
			JDBCTemplate.commit(conn);
			//update는 성공했으니 다시 dao로 가서 조회
			updateUser = new MemberDao().selectMember(conn,m.getUserId());
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return updateUser;
		
	}

	public Member updatePwd(Member m,String userPwd ,String userPwd2) {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updatePwd(conn, m,userPwd,userPwd2);
		
		Member updatePwd = null;
		if(result>0) {
			JDBCTemplate.commit(conn);
			//update는 성공했으니 다시 dao로 가서 조회
			updatePwd = new MemberDao().selectMember(conn,m.getUserId());
		}
		else {
			JDBCTemplate.rollback(conn);
			System.out.println("현재 비밀번호 일치 x");
		}
		
		JDBCTemplate.close(conn);
		
		return updatePwd;
	}
	
	
}




















