package chapter12;

public class MyThread extends Thread{

	@Override
	public void run() {
		for(int i = 0; i <100; i++) {
			System.out.println("스레드 진행 중" + i);
			System.out.println("---------------");
		}
	}

}
