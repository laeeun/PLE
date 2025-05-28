package chapter12;

public class SleepThread extends Thread{

	@Override
	public void run() {
		System.out.println("카운트다운 5초");
		for( int i = 5; i >= 0; i--) { //5부터 시작
			System.out.println(i);
			if(i != 0) { //0초인 경우에는 1초를 기다리지 않음
				try {
					Thread.sleep(10000); //10초동안 스레드 block
				}catch(InterruptedException ie) {
					System.out.println(ie.toString());
				}
			}
		}
		System.out.println("종료");
	}
	
}
