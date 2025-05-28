package chapter06;

public class exam_38 
{//Overloading 오버로드는 매개 변수의 갯수와 데이터 타입으로 구분한다 !
	static int sum(int a, int b) 
	{
		System.out.println("인자가 둘일 경우 호출됨"); //정수 2개
		return a+b;
	}
	
	static int sum(int a, int b, int c) 
	{
		System.out.println("인자가 셋일 경우 호출됨"); //정수 3개
		return a + b + c;
	}
	
	static double sum(double a, double b, double c) 
	{
		System.out.println("double 타입일 경우 호출됨"); //실수 3개
		return a + b + c;
	}
	

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		System.out.println(sum(3, 2));
		System.out.println(sum(2, 3, 4));
		System.out.println(sum(2.5, 3.5, 4.5));
	}

}
