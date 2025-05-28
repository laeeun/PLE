package chapter04;
import java.util.*; //Scanner 클래스를 사용하기 위해서 추가한 부분
public class exam_17 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int age;
		Scanner input = new Scanner(System.in);
		String tmp = input.nextLine();
		age = Integer.parseInt(tmp);
		
		if(age > 19) 
		{
			System.out.println("성인입니다.");
			System.out.println("성인 요금이 적용됩니다.");
		}
		//19 >= age가 14 ~ 19살인 경우
		else if(age  > 13) 
		{
			System.out.println("청소년입니다.");
			System.out.println("청소년 요금이 적용됩니다.");
		}
		//13 >= age가 9 ~ 13살인 경우
		else if(age > 8) 
		{
			System.out.println("유아입니다.");
			System.out.println("유아 요금이 적용됩니다.");
		}
		System.out.println("결제를 진행해주세요.");
	}

}
