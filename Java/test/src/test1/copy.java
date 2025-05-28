package test1;

import java.util.Arrays;

public class copy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] original = {1, 2, 3, 4, 5};
		int[] copy = new int[original.length];

		for (int i = 0; i < original.length; i++) {
		    copy[i] = original[i];  // 하나씩 복사
		}
        System.out.println("Original Array: " + Arrays.toString(original));
        System.out.println("Copy Array: " + Arrays.toString(copy));
		
//		int[] scores = {90, 85, 70, 88, 75};
//
//		for (int score : scores) {    // 배열 scores의 각 요소를 score에 넣음 //foreach
//		    System.out.println(score); // score 출력
//		}

	}

}
