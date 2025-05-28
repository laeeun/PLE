package chapter10;

public class StringBuilder_StringBuffer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String a = "java"; // 문자열 변수 a에 "java" 저장
        String b = "Hello"; // 문자열 변수 b에 "Hello" 저장
        
     // 문자열은 불변(한 번 만들면 안 바뀜)이기 때문에 a, b의 해시코드(주소 비슷한 숫자)를 출력
        System.out.println("a: " + a.hashCode()); // a("java")의 해시코드 출력
        System.out.println("b: " + b.hashCode()); // b("Hello")의 해시코드 출력
        
        a = a + b; // a와 b를 더함 → "javaHello"가 되고, 그걸 다시 a에 저장
     // 주의: a에 새로운 문자열이 들어가니까, 기존 해시코드와 달라짐
        System.out.println("a: " + a.hashCode());// 새로운 a("javaHello")의 해시코드 출력
        
     // StringBuffer 사용
        StringBuffer c = new StringBuffer(); // 비어 있는 StringBuffer c를 생성
        System.out.println("c: " + c.hashCode()); // c의 해시코드 출력 (StringBuffer는 바뀌어도 주소 안 바뀜)
        
        // c에 문자열을 붙이는 작업(append는 뒤에 붙인다는 뜻!)
        c.append(2); // c에 "2" 붙임
        c.append("+"); // c에 "+" 붙임 → 지금까지는 "2+"
        c.append(3);  // c에 "3" 붙임 → "2+3"
        c.append("=");  // c에 "=" 붙임 → "2+3="
        c.append(5); // c에 "5" 붙임 → 최종적으로 "2+3=5"가 됨
        
        System.out.println(c); // 지금까지 붙인 걸 출력: "2+3=5"
        
        // StringBuffer는 주소가 안 바뀌고 그대로 유지, 안에 내용만 바뀜
        c.append("TEST StringBuffer");  // 뒤에 또 문자열 붙임
        System.out.println("c: " + c.hashCode()); // 여전히 같은 c 객체이기 때문에 해시코드는 그대로
	}

}
