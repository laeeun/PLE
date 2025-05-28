package test1;

import java.util.Scanner;

public class Lol3 
{

	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("궁극기 쿨 타임이 몇초인가요??");
		
		int cooltime = scanner.nextInt(); //총 쿨타임 초
		
		System.out.println("궁극기를 사용한지 몇초가 지났나요?");
		
		 int elapsed = scanner.nextInt();

	        int remaining = elapsed;

	        if (remaining <= 0) {
	            System.out.println("궁극기 사용 가능!");
	        } else {
	            int minutes = ((cooltime - remaining) / 60);  // 분 단위
	            int seconds = ((cooltime - remaining) % 60); // 초 단위
	            System.out.println("아직 " + minutes + "분 " + seconds + "초 남았습니다.");
	        }
	        
	        scanner.close();
	        
	}

}
