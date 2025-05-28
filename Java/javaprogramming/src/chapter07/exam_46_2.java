package chapter07;

public class exam_46_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User_2 user1 =new User_2("철수", 20); //인스턴스 생성
		User_2 user2 =new User_2("영희", 19); //인스턴스 생성
		
		//user2.age = 99; //에러 발생 직접 접근 불가
		
		user2.setAge(20);
		System.out.println(user2.getName() + "의 나이는" + user2.getAge());
	}

}
