package chapter07;

public class exam_52 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Lion lion1 = new Lion(); //Lion 인스턴스 생성
		Zookeeper1 james = new Zookeeper1(); //jamse이름의 사육사 인스턴스 생성
		james.feed(lion1); //james가 lion1에게 먹이를 줌
		
		Rabbit rabbit1 = new Rabbit();
		james.feed(rabbit1);
		
		Monkey monkey1 = new Monkey();
		james.feed(monkey1);
	}

}
