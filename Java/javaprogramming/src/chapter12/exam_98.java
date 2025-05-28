package chapter12;

public class exam_98 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThreadB s1 = new MyThreadB();
		Thread t1 = new Thread(s1, "A");
		Thread t2 = new Thread(s1, "B");
		Thread t3 = new Thread(s1, "C");
		t1.start();
		t2.start();
		t3.start();
		
	}

}
