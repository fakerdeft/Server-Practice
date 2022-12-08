package com.kh.notice.model.vo;

import java.sql.Date;

public class Notice {
	
	private int noticeNo;	// NOTICE_NO 		Number
	private String noticeTitle; // notice_title	varchar2(100 byte)
	private String noticeContent; // 글작성자 notice_writer number
	private String noticeWriter;
	private String userid;
	private int UserNo;
	private int count; // count number
	private Date createDate; // create_date date
	private String status; // status varchar2(1 byte)
	
	public Notice() {
		super();
	}
	public String getNoticeWriter() {
		return noticeWriter;
	}
	
	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public Notice(int noticeNo, String noticeTitle, String noticeContent, String userid, int count, Date createDate, String status) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.userid = userid;
		this.count = count;
		this.createDate = createDate;
		this.status = status;
	}

	public Notice(int noticeNo, String noticeTitle, String noticeContent, String noticeWriter, int count,
			Date createDate) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeWriter = noticeWriter;
		this.count = count;
		this.createDate = createDate;
	}
	
	public Notice(String noticetitle2, String noticecontent2, int userNo) {
		super();
		this.noticeTitle=noticetitle2;
		this.noticeContent=noticecontent2;
		this.UserNo= userNo;
	}

	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public int getUserNo() {
		return UserNo;
	}
	public void setUserNo(int userNo) {
		UserNo = userNo;
	}
	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeWriter=" + noticeWriter + ", userid=" + userid + ", UserNo=" + UserNo + ", count=" + count
				+ ", createDate=" + createDate + ", status=" + status + "]";
	}
	
	
}
