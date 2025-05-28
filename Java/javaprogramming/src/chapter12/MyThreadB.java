package chapter12;

public class MyThreadB implements Runnable{

	Ticketing ticket = new Ticketing();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ticket.ticketing();
	}

}
