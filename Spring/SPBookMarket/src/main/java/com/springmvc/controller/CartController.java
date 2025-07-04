package com.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.springmvc.domain.Book;
import com.springmvc.domain.Cart;
import com.springmvc.domain.CartItem;
import com.springmvc.exception.BookIdException;
import com.springmvc.service.BookService;
import com.springmvc.service.CartService;

@Controller // 이 클래스가 컨트롤러임을 명시 (DispatcherServlet이 인식)
@RequestMapping(value="/cart") // 모든 메소드 앞에 /cart 붙은 요청만 받음
public class CartController {

    private final BookController bookController;
    
    @Autowired
    private CartService cartService; // 장바구니 서비스 주입
    
    @Autowired
    private BookService bookService; // 도서 서비스 주입

    CartController(BookController bookController) {
        this.bookController = bookController;
    }

    @PutMapping("/add/{bookId}") // 장바구니에 도서 추가하는 요청 (PUT 방식)
    @ResponseStatus(value=HttpStatus.NO_CONTENT) // 응답 코드 204 (본문 없음) 반환
    public void addCartByNewItem(@PathVariable String bookId, HttpServletRequest request) {
        // 세션 ID를 장바구니 ID로 사용
        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId); // 기존 장바구니 가져오기
        if(cart == null)
            cart = cartService.create(new Cart(sessionId)); // 없으면 새로 생성
        Book book = bookService.getBookById(bookId); // 도서 정보 조회
        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId)); // 도서가 없으면 예외
        cart.addCartItem(new CartItem(book)); // 장바구니에 도서 추가
        cartService.update(sessionId, cart); // 장바구니 갱신
    }

    @PutMapping("/remove/{bookId}") // 장바구니에서 도서 제거하는 요청
    public String removeCartByItem(@PathVariable String bookId, HttpServletRequest request) {
        System.out.println("remove mapping들어옴 !!!!!");
        String sessionId = request.getSession(true).getId();
        Cart cart = cartService.read(sessionId); // 기존 장바구니 조회
        System.out.println(cart);
        if(cart == null)
            cart = cartService.create(new Cart(sessionId)); // 없으면 생성
        Book book = bookService.getBookById(bookId); // 도서 조회
        System.out.println(book);
        if(book == null)
            throw new IllegalArgumentException(new BookIdException(bookId)); // 없으면 예외
        cart.removeCartItem(new CartItem(book)); // 장바구니에서 항목 제거
        System.out.println("삭제한거 갱신하고 cart컨트롤러 다시왔다");
        cartService.update(sessionId, cart); // 장바구니 업데이트
        System.out.println("장바구니 갱신했다");
        return "redirect:/cart"; // 장바구니 목록 페이지로 리다이렉트
    }

    @GetMapping // "/cart" 요청 시 동작, 세션 ID 기반으로 리다이렉트
    public String requestCartId(HttpServletRequest request) {
        String sessionid = request.getSession(true).getId();
        return "redirect:/cart/" + sessionid; // 실제 장바구니 페이지로 이동
    }

    @PostMapping // 새로운 장바구니 생성 요청 (JSON 바디 기반)
    public @ResponseBody Cart create(@RequestBody Cart cart) {
        return cartService.create(cart); // 장바구니 생성 후 반환
    }

    @GetMapping("/{cartId}") // 특정 ID의 장바구니 정보 조회
    public String requestCartList(@PathVariable(value="cartId") String cartId, Model model) {
        Cart cart = cartService.read(cartId); // 장바구니 데이터 가져오기
        model.addAttribute("cart", cart); // 모델에 담아서 JSP로 전달
        return "cart"; // cart.jsp 뷰 렌더링
    }

    @PutMapping("/{cartId}") // RESTful API 용도로 장바구니 데이터 반환
    public @ResponseBody Cart read(@PathVariable(value="cartId") String cartId) {
        return cartService.read(cartId); // 해당 cartId 장바구니 JSON 형태로 반환
    }

    @DeleteMapping("/{cartId}") // 장바구니 삭제 요청
    public String deleteCartList(@PathVariable(value="cartId")String cartId) {
        System.out.println("delete mapping");
        cartService.delete(cartId); // 장바구니 삭제
        return "redirect:/cart"; // 장바구니 메인 페이지로 리다이렉트
    }

}
