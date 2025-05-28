package chapter12;

public class exam_93 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread th1 = new MyThread(); //Thread 상속한 클래스
		Runnable th2 = new MyThread2(); //Runnable 구현한 클래스
		Thread t = new Thread(th2);
		th1.start();
		t.start();
		for(int i = 0; i < 100; i++) {
			System.out.println("메인 함수 진행 중" + i);
			System.out.println("---------------");
		}
		
	}

}
