package chapter07;

public class User_2 {
	private String name;
	private int age;
	User_2(String name, int age){//매개변수를 가진 생성자
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
