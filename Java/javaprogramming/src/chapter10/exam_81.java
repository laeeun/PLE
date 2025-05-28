package chapter10;

import java.time.LocalDate;

public class exam_81 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LocalDate ld = LocalDate.now();
		System.out.println(ld);
		
		LocalDate ld2 = ld
				.minusYears(2)
				.plusMonths(3)
				.minusDays(4);
		System.out.println(ld2);
		
		//자동형변환
		LocalDate ld3 = ld2.minusDays(3);
		System.out.println(ld3);
		
		//주 더하기
		LocalDate ld4 = ld3.plusWeeks(3);
		System.out.println(ld4);
	}

}
