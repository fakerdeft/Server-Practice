package com.kh.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

//cos.jar에서 제공하고 있는 FileRenamePolicy 인터페이스를 구현해야 한다.
public class MyFileRenamePolicy implements FileRenamePolicy{
	
	//미완성된 rename메소드를 오버라이딩하여 구현하기
	//기존의 파일명을 받아서 파일명 수정작업을 거친 후에 수정된 파일을 반환해주는 메소드를 구현할 것
	
	@Override
	public File rename(File originFile) {
		
		//원본 파일명
		String originName = originFile.getName();
		
		//수정 파일명 : 파일업로드된시간(년월일시분초) + 5자리 랜덤값 (10000~99999)
		//확장자 : 원본파일의 확장자를 그대로 
		
		//원본파일명 : asd.jsp -> 수정파일명 : 2022102010483399998.jsp
		
		//1. 파일 업로드된 시간 (년월일시분초) -> String currentTime;
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // java.util 패키지
		
		//2. 5자리 랜덤값 -> int ranNum;
		int ranNum = (int)(Math.random()*90000) + 10000;
		
		//3. 원본파일의 확장자 추출 String ext
		//파일명 중간에 . 이 있을 가능성도 있으니 맨 뒤에서부터 가장 처음 오는 .을 찾아주면 된다
		//원본 파일명에서 가장 마지막의 .을 찾아 인덱스를 기준으로 확장자를 추출한다.
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		//원본파일(originFile)을 수정된 파일명으로 적용시켜서 파일 객체로 반환
		File f = new File(originFile.getParent(),changeName);
		
		return f;
	}
	
	//기존의 파일명을 받아서 파일명 수정작업을 거친 후에 수정된 파일을 반환해주는 메소드를 구현할 것
}












