package chapter08;

abstract class Poketmon{//포켓몬 추상클래스
	String name;
	
	abstract void attack(); //공격 추상 메서드
	abstract void sound(); //소리 추상 메서드
	
	public String getName() {//포켓몬의 이름을 리턴하는 메서드
		return this.name;
	}
	

}
