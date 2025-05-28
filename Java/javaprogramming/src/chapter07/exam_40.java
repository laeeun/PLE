package chapter07;
public class exam_40 {
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Student s1 = new Student(); //학생 인스턴스 s1 생성
		s1.breath(); //사람 클래스의 breath 메서드를 상속받았음
		s1.learn();
		
		Teacher t1 = new Teacher(); //선생 인스턴스 t1 생성
		t1.eat(); //사람클래스의 eat 메서드를 상속받았음
		t1.teach();
	}

}
