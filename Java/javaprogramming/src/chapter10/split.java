package chapter10;

public class split { // 기준 문자가 나올 때마다 문자열을 나눠서 배열로 만들어준다

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "kor,eng,math";
		String div[] = a.split(",");
		for (int i =0; i < div.length; i++) {
	            System.out.println(div[i]);
	    }
	}

}
