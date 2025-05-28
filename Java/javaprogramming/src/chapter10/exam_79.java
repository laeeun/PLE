package chapter10;

import java.text.DecimalFormat;

public class exam_79 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] pattern = {//패턴의 배열
				"###.###",
				"000.000",
				"-###.###",
				"000000.00%"
		};
		
		double[] arr = {1.3, 3.33, 123.243, 242};
		//아직 형식화되지 않는 수들의 배열
		
		for(int p = 0; p < pattern.length; p++) {//패턴마다 반복
			DecimalFormat d = new DecimalFormat(pattern[p]);
			System.out.println("<<<<<" + pattern[p] + ">>>>>");
			for(int i = 0; i < arr.length; i++) {
				System.out.println(d.format(arr[i]));
			}
		}
	}

}
