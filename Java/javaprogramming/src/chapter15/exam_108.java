package chapter15;

import java.net.*;

public class exam_108 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InetAddress ip = null;
		try {
			//예외처리
			ip = InetAddress.getLocalHost();
			//호스트의 이름을 출력합니다.
			System.out.println("getHostName():" + ip.getHostName());
			//호스트의 주소를 출력합니다.
			System.out.println("getHostAddress():" + ip.getHostAddress());
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
