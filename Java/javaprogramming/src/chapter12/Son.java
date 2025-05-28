package chapter12;

public class Son extends Thread{

	private Account account;
	
	Son(Account account){
		this.account = account;
		
	}

	@Override
	public void run() {
		for(int i = 0; i < 10; i++) {
			account.getMoney();
			
		}
	}
	
}
