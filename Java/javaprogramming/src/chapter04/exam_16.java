package chapter04;

public class exam_16 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int age = 15;
		
		if(age > 19) 
		{ //조건식은 fasle이다.
			System.out.println("성인입니다.");
			System.out.println("성인 요금이 적용됩니다.");
		}
		else 
		{
			System.out.println("청소년입니다.");
			System.out.println("청소년 요금이 적용됩니다.");
		}
		System.out.println("결제를 진행해주세요.");
	}

}
