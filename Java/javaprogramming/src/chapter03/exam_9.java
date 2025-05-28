package chapter03;

public class exam_9 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		double a = 3.14;
		double b = 5.14;
		System.out.println(a == b);
		System.out.println(a != b);
		
		
		String c1 = "Hello JAVA!";
		System.out.println(c1.equals("Hello java!"));
		//자바에서 대소문자는 다르게 취급되므로 false를 반환한다.
		System.out.println(c1.equals("Hello JAVA!"));
	}

}
