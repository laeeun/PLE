package chapter11;

import java.util.HashMap;
import java.util.Scanner;

public class exam_92 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap hm = new HashMap();
		Scanner sc = new Scanner(System.in);
		
		hm.put("apple", "사과");
		hm.put("paper", "종이");
		hm.put("flower", "꽃");
		String voca;
		
		System.out.println("알고 싶은 단어를 입력하세요 :");
		voca = sc.nextLine();
		
		if(hm.containsKey(voca)) {
			System.out.println("해당하는 뜻은 :" +hm.get(voca));
		}else {
			System.out.println("해당 단어에 대한 뜻은 데이터베이스에 없습니다.");
		}
	}

}
