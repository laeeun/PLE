package chapter11;

import java.util.ArrayList;

public class exam_90 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList list1 = new ArrayList();
		list1.add("A");
		list1.add("C");
		list1.add("E");
		list1.add("D");
		System.out.println("초기상태: ");
		System.out.println(list1);
		System.out.println("----------------");
		
		System.out.println("인덱스1 위치에 B추가 :");
		list1.add(1, "B");
		System.out.println(list1);
		
		System.out.println("----------------");
		System.out.println("인덱스2 위치의 값 삭제 :");
		list1.remove(2);
		System.out.println(list1);
		System.out.println("인덱스2번 위치의 값 반환: " + list1.get(2));
		
		System.out.println("----------------");
		for(int i = 0; i < list1.size(); i++) {
			list1.get(i);
			System.out.println(list1.get(i));
		}
		
	}

}
