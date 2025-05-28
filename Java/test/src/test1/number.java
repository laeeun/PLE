package test1;

import java.util.Scanner;

public class number {

	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("곱하고 싶은 첫 번째 수를 입력하세요");
		int a = scanner.nextInt();
		
		System.out.println("곱하고 싶은 두 번째 수를 입력하세요");
		int b = scanner.nextInt();
		
		
		System.out.println(a*b);
		


		scanner.close();
	}
	

}
