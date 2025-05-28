package chapter10;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class exam_69 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar a = Calendar.getInstance(); //싱글턴 패턴
		Calendar b = new GregorianCalendar();
		System.out.println(a.toString());
		System.out.println(b.toString());
	}

}
