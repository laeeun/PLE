package chapter10;

public class charAt_length { //charAt : 특정 위치에 있는 문자 하나를 반환 length : 문자열의 길이 반환

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "Hello Java!";
		char b = a.charAt(0);
		for(int i = 0; i < a.length(); i++) {
			System.out.println(a.charAt(i));
		}
	}

}
