package chapter10;

import java.util.Calendar;

public class exam_71 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	Calendar today = Calendar.getInstance();
	Calendar endOfYear = Calendar.getInstance();
	Calendar Christmas = Calendar.getInstance();
	Calendar My = Calendar.getInstance();
	
	endOfYear.set(Calendar.MONTH,11);
	endOfYear.set(Calendar.DATE,31);
	long diff = endOfYear.getTimeInMillis()-today.getTimeInMillis();
	System.out.println("연말까지 남은 날:" + diff/(24*60*60*1000) + "일");
	
	Christmas.set(2025,12,31);
	diff = Christmas.getTimeInMillis()-today.getTimeInMillis();
	System.out.println("크리스마스까지 남은 날:" + diff/(24*60*60*1000) + "일");
	
	My.set(2026,1,31);
	diff = My.getTimeInMillis()-today.getTimeInMillis();
	System.out.println("내 생일까지 남은 날:" + diff/(24*60*60*1000) + "일");
	}

}
