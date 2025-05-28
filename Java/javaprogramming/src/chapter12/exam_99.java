package chapter12;

public class exam_99 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Account account = new Account();
		Son son = new Son(account);
		Mom mom = new Mom(account);
		son.start();
		mom.start();
	}

}
