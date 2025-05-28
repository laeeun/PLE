package chapter02;

public class exam_1 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		byte a =127; //byte가 가질수 있는 최댓값
		int b =a; //자동형변환(byte -> int)
		System.out.println(b);
		
		float c = b; //자동형변환(int -> float)
		System.out.println(c);
	}

}
