package project1;

import java.util.Scanner;

public class buy 
{
	public void choice() {
		repository repository = new repository();
		String name = null;
		int price = 0;
		String color = null;
		Scanner sc = new Scanner(System.in);
		Scanner scch = new Scanner(System.in);
		
		while(true) {
		System.out.println("자동차 선택");
		System.out.println("1. 소나타");
		System.out.println("2. 벤츠");
		
		int choicecar = sc.nextInt();
		if(choicecar==1) {
			name = "소나타";
			price=3000;
		}
		else if(choicecar==2) {
			name= "벤츠";
			price=5000;
		}
		else {
			continue;
		}
		
		System.out.println("옵션 선택");
		System.out.println("1. 기본 옵션");
		System.out.println("2. 풀 옵션");
		
		int choiceoption = sc.nextInt();
		if(choiceoption==1) {
			price += 1000;
		}
		else if(choiceoption==2) {
			price += 2000;
		}
		else {
			continue;
		}
		
		System.out.println("색선택");
		System.out.println("1. 검정색");
		System.out.println("2. 흰색");
		
		int choicecolor = sc.nextInt();
		if(choicecolor==1) {
			color ="black";
		}
		else if(choicecolor==2) {
			color ="white";
		}
		else {
			continue;
		}
		System.out.println("구매자 이름을 입력해주세요!");
		String user = scch.nextLine();
		System.out.println("구매자 전화번호를 입력해주세요: -제외");
		String phone = scch.nextLine();
		car makecar = new car(name,price,color,user,phone);
		repository.repo.add(makecar); //add는 특정 개체의 주소를 저장
		break;
		
	  }
	}
}
