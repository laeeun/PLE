package chapter12;

public class Ticketing {

	int ticketNumber = 1;
	public void ticketing() {
		if(ticketNumber > 0) {
			System.out.println(Thread.currentThread().getName()+"가 티켓팅 성공");
			ticketNumber = ticketNumber - 1;
		}
		else {
			System.out.println(Thread.currentThread().getName()+"가 티켓팅 실패");
		}
		System.out.println(Thread.currentThread().getName()+"가 티켓팅시도 후 티켓 수 :" + ticketNumber);
	}
}
