package chapter06;

public class Area 
{
	static void manual() 
	{ //static이 있으므로 클래스 메서드
		System.out.println("현재 사용 사능한 함수 목록");
		System.out.println("triangle : 삼각형 넓이");
		System.out.println("rectangle : 사각형 넓이");
		System.out.println("입니다.");
	}
	
	double triangle(int a, int b)//매개변수
	{ //삼각형 넓이를 반환하는 메서드
		return (double)a*b/2;	
	}
	
	int rectangle(int a, int b)
	{ //사각형 넓이 반환하는 메서드
		return a*b;
	}
}
