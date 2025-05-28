package chapter07;

public class exam_46_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User_1 user1 = new User_1("철수", 20); //인스턴스 생성
		User_1 user2 = new User_1("영희", 19); //인스턴스 생성
		
		
		System.out.println(user2.name + "의 나이는" + user2.age);
		user2.age =99;
		System.out.println(user2.name + "의 나이는" + user2.age);

	}

}
