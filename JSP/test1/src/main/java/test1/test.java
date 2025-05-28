package test1;

public class test {

	public static void main(String[] args) {
		//1번
		int num = 10;
		System.out.println(num);

		//2번
		double pi = 3.14;
		System.out.println(pi);
		
		//3
		String name = "Alice";
		String meet = "Hello, " + name + "!";
		System.out.println(meet);
		
		//4
		double PI = 3.14159;
		System.out.println(PI);
		
		//5
		int a = 7;
		double b = (double) a;
		System.out.println(b);
		
		//6
		double pi = 3.99;
		int a = (int)pi;
		System.out.println(a);
		
		//7
		char ch = 'A';
		int a =(int)ch;
		System.out.println(a);
		
		//8
		int num = 66;
		char b =(char)num;
		System.out.println(b);
		
		//9
		String str = "123";
		int a =Integer.parseInt(str);
		System.out.println(a);
		
		//10
		int num = 5;
		if(num % 2 == 0) {
			System.out.println("짝수");
		}else {
			System.out.println("홀수");
		}
		
		//11
		int n = -3;
		if(n > 0) {
			System.out.println("양수");
		}else if(n < 0) {
			System.out.println("음수");
		}else {
			System.out.println("0");
		}
		
		//12
		if(a < b) {
			System.out.println(b);
		}
		
		//13
		if(score > 90) {
			System.out.println("A");
		}else if(score > 80) {
			System.out.println("B");
		}else if(score > 70) {
			System.out.println("C");
		}else if(score > 60) {
			System.out.println("D");
		}else {
			System.out.println('F');
		}
		
		//14
		if(id.equals("admin")) {
			System.out.println("접속 성공");
		}else {
			System.out.println("접속 실패");
		}
		
		//15
		if(num == 3) {
			System.out.println("수요일");
		}
		
		//16
		switch(month) {
		case 12:System.out.println("겨울");break;
		case 1:System.out.println("겨울");break;
		case 2:System.out.println("겨울");break;
		case 3:System.out.println("봄");break;
		case 4:System.out.println("봄");break;
		case 5:System.out.println("봄");break;
		default: System.out.println("없음");
		}
		
		//17
		switch(score / 10) {
		case 9:System.out.println("A");break;
		case 8:System.out.println("B");break;
		case 7:System.out.println("C");break;
		default:System.out.println("해당없음");
		}
		
		//18
		switch(op) {
		case "+":System.out.println(a + b);break;
		case "-":System.out.println(a - b);break;
		case "*":System.out.println(a * b);break;
		case "/":System.out.println(a / b);break;
		default:System.out.println("해당없음");
		}
		
		//19
		switch(fruit) {
		case "apple":System.out.println("1000");break;
		case "banana":System.out.println("1200");break;
		case "orange":System.out.println("1500");break;
		default:System.out.println("찾는거 없습니다.");
		}
		
		//20
		for(int i = 1; i < 6; i++) {
			System.out.println(i);
		}
		
		//21
		for(int i = 1; i < 11; i++) {
			if(i % 2 !=0)continue;
			System.out.println(i);
		}
		
		//22
		int num = 0;
		for(int i = 1; i <6; i++) {
			num += i;
		}
		System.out.println(num);
		
		//23
		for(int  i = 1; i < 10; i++) {
			System.out.println("2 x "+ i + " = " + 2 * i );
		}
		
		//24
		for(int i = 0; i < 5; i++) {
			System.out.print("★");
		}
		System.out.println();
		
		//25
		int i = 1;
		while(i <= 5) {
			System.out.println(i);
			i++;
		}
		
		//26
		do {
			System.out.println("실행됨");
		}while(condition);
		
		//27
		while(i <= 10) {
			System.out.println(i);
			if(i == 5)break;
			i++;
		}
		
		//28
		while(i <= 10) {
			if(i % 2 != 0)continue;
			System.out.println(i);
			i++;
		}
	}

}
