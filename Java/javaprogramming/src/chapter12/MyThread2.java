package chapter12;

public class MyThread2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub

		for(int i = 0; i < 100; i++) {
			System.out.println("러너블 진행 중" + i);
			System.out.println("---------------");
	    }

	}

}
