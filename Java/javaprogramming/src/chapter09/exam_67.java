package chapter09;

public class exam_67 {
	
	public static void ticketing(int age)throws AgeException{
		if(age < 0) {
			//throw new AgeException("나이 입력이 잘못되었습니다.") //객체 생성 후 변수에 안담아놓음
			AgeException e = new AgeException("나이 입력이 잘못되었습니다."); //객체 생성 후 변수에 담아놓음
			throw e; //고의 예외
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int age = -19;
		try {
			ticketing(age);
		}catch(AgeException e){
			e.printStackTrace();
			System.out.println("나이 입력이 잘못되었음을 감지함.");
		}
	}

}
