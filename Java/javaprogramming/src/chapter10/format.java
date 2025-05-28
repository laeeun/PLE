package chapter10;

public class format { //문자열을 형식에 맞춰서 만들 때 사용 (%s는 문자열을, %d는 정수)

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println(String.format("%d", 1234+5678)); //정수형
	     System.out.println(String.format("%s", "1234"+"5678")); //문자열 
	}

}
