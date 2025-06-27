package com.springmvc.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.domain.Book;
import com.springmvc.exception.BookIdException;
import com.springmvc.exception.CategoryException;
import com.springmvc.service.BookService;
import com.springmvc.validator.BookValidator;
import com.springmvc.validator.UnitsInStockValidator;

@Controller
@RequestMapping("/books")
public class BookController {
	public static Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookValidator bookValidator; //BookValidator 인스턴스 선언
		
	@GetMapping
	public String requestBookList(Model model) {
		System.out.println("📥 [GET] /books 진입");
		List<Book> list = bookService.getAllBookList(); // 모델이동
		logger.info("📡 서비스 결과 리스트 크기: {}", list.size());
		model.addAttribute("bookList", list);
		return "books";
	}
	
	@GetMapping("/all")
	public ModelAndView requestAllBooks() {
		System.out.println("📥 [GET] /books/all 진입");
		ModelAndView ModelAndView = new ModelAndView();
		List<Book> list = bookService.getAllBookList(); // 모델이동
		logger.info("📡 서비스 결과 리스트 크기: " + list.size());
		ModelAndView.addObject("bookList", list);
		ModelAndView.setViewName("books");
		return ModelAndView;
	}
	
	@GetMapping("/{category}")
	public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
		logger.info("📥 [GET] /books/{category} 진입. category = " + bookCategory);
		List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
		
		if (booksByCategory == null || booksByCategory.isEmpty()) {
			System.out.println("⚠️ 카테고리에 해당하는 도서 없음. 예외 발생!");
			throw new CategoryException();
		}
		logger.info("📚 category 결과 리스트 크기: " + booksByCategory.size());
		model.addAttribute("bookList", booksByCategory);
		logger.info("👉 booksByCategory값 : " + booksByCategory);
		return "books";
	}
	
	@GetMapping("/filter/{bookFilter}")
	public String requestBooksByFilter(
	@MatrixVariable(pathVar="bookFilter") Map<String, List<String>> bookFilter,
	Model model) {
		logger.info("📥 [GET] /books/filter 호출됨!");
		logger.info("🔍 전달받은 필터 Map: " + bookFilter);
		Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
		logger.info("📚 필터 결과 Set 크기: " + booksByFilter.size());
		
		model.addAttribute("bookList", booksByFilter);
		return "books";
	}
	
	@GetMapping("/book")
	public String requestBookById(@RequestParam("id") String bookId, Model model) {
		logger.info("📥 [GET] bookId : " + bookId);
		Book bookById = bookService.getBookById(bookId);
		logger.info("📘 조회된 책: " + bookById);
		model.addAttribute("book", bookById);
		return "book";
	}
	
	@GetMapping("/add") 
	public String AddBookForm(@ModelAttribute("NewBook") Book book) {
		logger.info("📥 [GET] /books/add 진입 - 도서 등록 폼 요청");
		return "addBook";
	}
	@PostMapping("/add")
	public String submitAddNewBook(@Valid @ModelAttribute("NewBook") Book book,  BindingResult result,HttpServletRequest request) {
		logger.info("📤 [POST] /books/add 진입 - 등록할 도서 정보: " + book.toString());
		if(result.hasErrors()) {
			return "addBook";
		}
		
		MultipartFile bookImage = book.getBookImage();
		String saveName = bookImage.getOriginalFilename();
		String path = request.getRealPath("resources/images");
		File saveFile = new File(path, saveName);
		
		if (bookImage != null && !bookImage.isEmpty()) {
			try {
				bookImage.transferTo(saveFile);
				logger.info("✅ 이미지 업로드 성공");
			} catch(Exception e) {
				logger.info("❌ 이미지 업로드 실패: " + e.getMessage());
				throw new RuntimeException("도서 이미지 업로드가 실패하였습니다.", e);
			}
		}
		
		bookService.setNewBook(book);
		logger.info("📚 도서 등록 완료!");
		return "redirect:/books";
	}
	
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("addTitle", "신규 도서 등록");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		logger.info("🛠 InitBinder 동작 - 허용 필드 설정");
		// binder.setValidator(unitsInStockValidator); //생성한 unitsInStockValidator 설정
		binder.setValidator(bookValidator);
		binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher", "category", "unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
	}
	
	@ExceptionHandler(value= {BookIdException.class})
	public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
		logger.info("🚨 BookIdException 발생 핸들러 진입!");
		logger.info("🔍 잘못된 Book ID: " + exception.getBookId());
		logger.info("🌐 요청 URL: " + req.getRequestURL() + "?" + req.getQueryString());
        
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidBookId", exception.getBookId());
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
		mav.setViewName("errorBook");
		return mav;
	}
}
