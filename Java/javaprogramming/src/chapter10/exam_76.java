package chapter10;

import java.io.FileInputStream;
import java.util.Scanner;


public class exam_76 {

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("sample.txt");
			Scanner s = new Scanner(fis);
			
			
			while (s.hasNext()) { //값이 존재 하면 계속해서 반복
				System.out.println(s.nextLine());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
