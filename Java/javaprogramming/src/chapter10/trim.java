package chapter10;

public class trim { //문자열의 앞,뒤 공백을 자름

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = ("             Hello Java            ");
        System.out.println(a);
        String b = a.trim();
        System.out.println(b);
	}

}
