package com.springmvc.controller; // 컨트롤러가 들어 있는 패키지

import org.springframework.beans.factory.annotation.Autowired; // 의존성 주입을 위한 어노테이션
import org.springframework.stereotype.Controller; // 해당 클래스를 Spring MVC의 Controller로 등록
import org.springframework.web.bind.annotation.RequestMapping; // URL 요청을 메서드에 매핑하는 어노테이션

import com.springmvc.service.OrderService; // 주문 관련 비즈니스 로직을 처리할 서비스 클래스 import

@Controller // 이 클래스가 컨트롤러임을 Spring에게 알림
public class OrderController {

    @Autowired // OrderService를 스프링이 자동으로 주입하게 함 (DI)
    private OrderService orderService;

    @RequestMapping("/order/ISBN1234/2") // 이 URL 요청이 들어오면 아래 메서드를 실행함
    public String process() {
       System.out.println("OrderController 입장"); // 콘솔에 로그 출력 (서버 로그 확인용)
        orderService.confirmOrder("ISBN1234", 2); // 서비스 로직 실행 - 도서 ISBN1234를 2권 주문 처리
        return "redirect:/books"; // 주문 완료 후 도서 목록 페이지로 리다이렉트
    }
}
