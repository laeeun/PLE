package chapter07;

public class exam_53 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Animal lion2 = new Lion(); //Lion 인스턴스 생성
		Zookeeper james = new Zookeeper(); //james이름의 사육사 인스턴스 생성
		james.feed(lion2); //james가 lion1에게 먹이를 줌
		
		Animal rabbit2 = new Rabbit();
		james.feed(rabbit2);
		
		Animal monkey2 = new Monkey();
		james.feed(monkey2);
		
	}

}
