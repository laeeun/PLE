package chapter08;

public class TourGuide {
	private Providable tour = new KoreaTour();
	//인터페이스로 타입 선언
	public void leisureSports() {
		tour.leisureSports();
	}
	
	public void sightseeing() {
		tour.sightseeing();
	}
	
	public void food() {
		tour.food();
	}
}
