package chapter09;

public class exam_65 {
	
	public static void methodA() throws Exception{
		methodB();
	}
	
	public static void methodB() throws Exception{
		methodC();
	}
	
	public static void methodC() throws Exception{
		Exception e = new Exception();
		throw e;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			methodA();
		}catch(Exception e) {
			System.out.println("메인에서 처리");
		}
	}

}
