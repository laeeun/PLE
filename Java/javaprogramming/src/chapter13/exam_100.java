package chapter13;

import java.io.*;

public class exam_100 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//파일 복사 프로그램
		
		//데이터 수신
		FileInputStream fis = null;
		fis = new FileInputStream("prac.txt");
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		//데이터 출력
		FileOutputStream fos = null;
		fos = new FileOutputStream("result.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		int data;
		while((data = bis.read())!= -1) {
			bos.write(data);
		}
		bos.close();//사용한 스트림은 닫아주기
		bis.close();
	}

}
