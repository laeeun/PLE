package chapter02;

public class testchar {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//char에 대한 연구
		//char은 숫자도 문자도 되는 마법의 변수
		
		//Step 1. char에 데이터를 넣기
		char a = 'A'; //출력시 'A'
		char b = 65; //출력시 'A'
		
		int c = 'A'; //데이터가 자동으로 형변환이 이루어짐
		
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		
		//char 변수 <- int 변수
//		a = c;
		//int 변수 <- char 변수
		c = a;
		System.out.println(c);
	}

}
