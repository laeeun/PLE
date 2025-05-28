package test1;

import java.util.Scanner;

public class Lol2 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("챔피언의 특성을 알고싶나요? -네 / 아니요라고 대답하시오.");
		String answer = scanner.nextLine(); //문자열로 입력받기
		
		while(answer.equals("네")) {
			System.out.println("챔피언의 이름을 입력하세요.");
			String champ = scanner.nextLine();

			switch(champ) {
			case "아리" :
				System.out.println("마법사, 미드 라인");
		        break;
			case "가렌":
                System.out.println("탱커, 탑 라인");
                break;
            case "징크스":
                System.out.println("원거리 딜러, 바텀 라인");
                break;
            case "미스포츈":
            	System.out.println("원거리 딜러, 바텀 라인");
            	 break;
            case "소라카":
            	System.out.println("서포터, 바텀 라인");
            	 break;
            case "샤코":
            	System.out.println("암살자, 정글");
            	 break;
            case "럭스":
            	System.out.println("마법사, 미드 라인");
            	 break;
            case "카타리나":
            	System.out.println("암살자, 미드 라인");
            	 break;
            case "티모":
            	System.out.println("원거리 딜러, 탑 라인");
            	 break;
            default:
                System.out.println("알 수 없는 챔피언입니다.");
            
            
			}
			 System.out.println("계속 하시겠습니까? - 네 / 아니요");
	            answer = scanner.nextLine(); // 다음 반복 여부 확인
		}
        scanner.close(); // 반복문 끝나고 나서 닫기
        System.out.println("프로그램을 종료합니다.");
	}

}
