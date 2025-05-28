package chapter13;

import java.io.*;

public class exam_101 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//파일 복사 프로그램
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			fis = new FileInputStream("prac1.txt");
			fos = new FileOutputStream("result1.txt");
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			int data;
			
			while((data = bis.read())!= -1) {
				bos.write(data);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bos.close();
				bis.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
