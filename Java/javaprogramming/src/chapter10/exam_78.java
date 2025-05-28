package chapter10;

import java.util.Timer;
import java.util.TimerTask;

public class exam_78 {

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		Timer t = new Timer(true);
		TimerTask w1 = new Work1();
		TimerTask w2 = new Work2();
		t.schedule(w1, 3000); //3초 뒤 실행
		t.schedule(w2, 1000); //1초 뒤 실행
		Thread.sleep(4000); //종료하지 않고 스레드 기다리기
		System.out.println("모든 작업 종료");
	}

}
