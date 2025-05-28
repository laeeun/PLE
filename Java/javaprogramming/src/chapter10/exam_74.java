package chapter10;

import java.util.Random;

public class exam_74 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Random random = new Random();
		Random random2 = new Random();
		Random random3 = new Random();
		
		for(int i = 0; i < 5; i++) {
			System.out.println("기본 생성자:" + random.nextInt());
		}
		
		int a = random2.nextInt(11)+20; // 20 ~ 30 11개
		//0 ~ 10
		//20 ~ 30
		System.out.println("random2 :" + a);
		
		int b = random3.nextInt(100)+100;
		System.out.println("random3 :" + b);
	}

}
