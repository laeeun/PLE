
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

@Controller
@RequestMapping(value="/cart")
public class CartController {
	

    private final BookController bookController;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private BookService bookService;

    CartController(BookController bookController) {
        this.bookController = bookController;
    }
	
	@PutMapping("/add/{bookId}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT) //오류 응갑 상태 코드 설정
	public void addCartByNewItem(@PathVariable String bookId, HttpServletRequest request) {
		//장바구니 ID인 세션ID 가져오기
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId); //장바구니에 등록된 모든 정보 얻어 오기
		if(cart == null)
			cart = cartService.create(new Cart(sessionId));
		//경로 변수 bookId에 대한 정보 얻어 오기
		Book book = bookService.getBookById(bookId);
		if(book == null)
			throw new IllegalArgumentException(new BookIdException(bookId));
		//bookId에 대한 도서 정보를 장바구니에 등록하기
		cart.addCartItem(new CartItem(book));
		cartService.update(sessionId, cart); // 세션 ID에 대한 장바구니 갱신하기
	}
	
	@PutMapping("/remove/{bookId}")
	public String removeCartByItem(@PathVariable String bookId, HttpServletRequest request) {
		System.out.println("remove mapping들어옴 !!!!!");
		//장바구니 ID인 세션 ID 가져오기
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId); //장바구니에 등록된 모든 정보 얻어 오기
		System.out.println(cart);
		if(cart == null)
			cart = cartService.create(new Cart(sessionId));
		//경로 변수 bookId에 대한 정보 얻어오기
		Book book = bookService.getBookById(bookId);
		System.out.println(book);
		if(book == null)
			throw new IllegalArgumentException(new BookIdException(bookId));
		//bookId에 대한 도서 정보를 장바구니에서 삭제하기
		
		
		
		cart.removeCartItem(new CartItem(book));
		System.out.println("삭제한거 갱신하고 cart컨트롤러 다시왔다");
		cartService.update(sessionId, cart); // 세션ID에 대한 장바구니 갱신하기
		System.out.println("장바구니 갱신했다");
		
		return "redirect:/cart";
	}
	
	@GetMapping
	public String requestCartId(HttpServletRequest request) {
		String sessionid = request.getSession(true).getId();
		return "redirect:/cart/" + sessionid;
	}
	
	@PostMapping
	public @ResponseBody Cart create(@RequestBody Cart cart) {
		return cartService.create(cart);
	}
	
	@GetMapping("/{cartId}")
	public String requestCartList(@PathVariable(value="cartId") String cartId, Model model) {
		Cart cart = cartService.read(cartId);
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	@PutMapping("/{cartId}")
	public @ResponseBody Cart read(@PathVariable(value="cartId") String cartId) {
		return cartService.read(cartId);
	}
	
	@DeleteMapping("/{cartId}")
	public String deleteCartList(@PathVariable(value="cartId")String cartId) {
		System.out.println("delete mapping");
		cartService.delete(cartId);
		
		return "redirect:/cart";
	}
	
	
}
