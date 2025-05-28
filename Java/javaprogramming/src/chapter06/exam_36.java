package chapter06;

public class exam_36 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Area.manual(); //클래스 메서드 접근 가능
		//Area.triangle(3,5); //에러 발생
		//Area.rectangle(3,4); //에러 발생
		
		Area cal = new Area();
		
		cal.manual();
//		int a = 3;
//		int b = 5;
//		System.out.println(cal.triangle(a, b)); 변수의 값이 바뀔 수도 있기에 이렇게 사용하는 것 !
		System.out.println(cal.triangle(3, 5)); //파라미터
		System.out.println(cal.rectangle(3, 4));
	}

}
