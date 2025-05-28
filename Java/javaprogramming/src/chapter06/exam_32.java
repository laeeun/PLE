package chapter06;

public class exam_32 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Car mycar = new Car(); //객체 생성
		
		System.out.println("시동 처음 초기화" + mycar.powerOn);
		System.out.println("차의 색상 초기화" + mycar.color); //null: 참조변수에 주소가 없다.
		System.out.println("바퀴의 수 초기화" + mycar.wheel);
		System.out.println("속력 초기화" + mycar.speed);
		System.out.println("와이퍼 작동 초기화" + mycar.wiperOn);
		
		mycar.power(); //시동 메서드 실행
		System.out.println("시동 메서드 동작" + mycar.powerOn);
		mycar.power(); //시동메서드 실행
		System.out.println("시동 메서드 다시 동작" + mycar.powerOn);
		
		mycar.color = "black"; //색상 변수에 black 대입
		System.out.println("현재 차의 색상" + mycar.color);
	}

}
