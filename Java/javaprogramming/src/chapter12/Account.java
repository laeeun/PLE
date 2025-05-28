package chapter12;

public class Account {
	int money = 0;
	//입금 ,출금
	public int showMoney() {
		return money;
	}
	
	public synchronized void setMoney() {
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ie){
			System.out.println(ie.toString());
		}
		this.money+=2000;
		
		System.out.println("어머니가 용돈을 입금했습니다. 현재 잔액:" + this.showMoney());
		System.out.println("------------------");
		this.notify();
	}
	
//	public synchronized void getMoney() 
//	{
//		while(money <= 0) 
//		{
//			try 
//			{
//				System.out.println("통장 잔고가 없어서 아들 대기중");
//				System.out.println("------------------");
//				this.wait(); //이 메서드를 호출한 놈이 잔다.
//			}catch(InterruptedException e) 
//			{
//				
//			}
//		}
//		this.money-=2000;
//		System.out.println("아들이 용돈을 출금했습니다. 현재 잔액:" +this.showMoney());
//		System.out.println("------------------");
//	}
	
	public synchronized void getMoney() {
	    while (money <= 0) {
	        try {
	            System.out.println("통장 잔고가 없어서 아들 대기중");
	            System.out.println("------------------");
	            this.wait(); // 이 메서드를 호출한 놈이 잔다.
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    // 💡 여기에 1초 딜레이 추가
	    try {
	        Thread.sleep(1000); // 1초 대기
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }

	    this.money -= 2000;
	    System.out.println("아들이 용돈을 출금했습니다. 현재 잔액: " + this.showMoney());
	    System.out.println("------------------");
	}

}
