package chapter07;

public class SportsCar extends Car2{
	int speedLimit; //제한속도 km
	
	SportsCar(String color,int speedLimit){
		super();
		//super(color) 로 써도되긴하지만 위 방법으로 생성자 생성 후 해도됨 
		this.color =color;
		this.speedLimit = speedLimit;
	}

}
