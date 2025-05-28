package chapter06;

public class exam_39 {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
//		Cellphone my phone1 = new Cellphone(); //에러 발생 (파라미터가 없어서)
		
		Cellphone myphone = new Cellphone("Silver", 64); //생성자 호출
		
		System.out.println(myphone.model);
		System.out.println(myphone.color);
		System.out.println(myphone.capacity);
	}

}
