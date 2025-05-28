package test1;

import java.util.Scanner;

public class Lol {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("챔피언 이름을 입력하세요.");
		
		String champ = scanner.nextLine();
		
		if(champ.contentEquals("아리")) {
			System.out.println("아리의 궁극기는 3번의 이동이 가능합니다.");
			}
		else if(champ.contentEquals("애쉬")) {
				System.out.println("애쉬의 궁극기는 화살로 상대를 기절시킵니다.");
			}
		else if(champ.contentEquals("이즈리얼")) {
			System.out.println("이즈리얼의 궁극기는 화살을 발사해 여러명에게 데미지를 입힙니다.");
		}
		else {
			System.out.println("해당 챔피언의 분석을 실패하였습니다.");
		}
		
		scanner.close();
		
		}
	

}
