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

@Controller // 이 클래스가 Spring MVC의 컨트롤러 역할을 함
@RequestMapping("/books") // /books로 시작하는 URL을 이 컨트롤러가 처리함
public class BookController {
    public static Logger logger = LoggerFactory.getLogger(BookController.class); // 로깅 기능

    @Autowired
    private BookService bookService; // 도서 비즈니스 로직을 처리할 서비스 클래스 주입

    @Autowired
    private BookValidator bookValidator; // 도서 유효성 검증기 주입

    @GetMapping // 전체 도서 목록을 조회하는 메서드
    public String requestBookList(Model model) {
        List<Book> list = bookService.getAllBookList();
        model.addAttribute("bookList", list); // 조회한 도서 목록을 모델에 담음
        return "books"; // books.jsp 뷰 반환
    }

    @GetMapping("/all") // 모든 도서 목록 조회 - 위 메서드와 비슷하지만 ModelAndView 사용
    public ModelAndView requestAllBooks() {
        ModelAndView ModelAndView = new ModelAndView();
        List<Book> list = bookService.getAllBookList();
        ModelAndView.addObject("bookList", list);
        ModelAndView.setViewName("books");
        return ModelAndView;
    }

    @GetMapping("/{category}") // 카테고리별 도서 목록 조회
    public String requestBooksByCategory(@PathVariable("category") String bookCategory, Model model) {
        List<Book> booksByCategory = bookService.getBookListByCategory(bookCategory);
        if (booksByCategory == null || booksByCategory.isEmpty()) {
            throw new CategoryException(); // 카테고리 없으면 예외 발생
        }
        model.addAttribute("bookList", booksByCategory);
        return "books";
    }

    @GetMapping("/filter/{bookFilter}") // 필터 조건으로 도서 검색 (예: 가격, 출판사 등)
    public String requestBooksByFilter(@MatrixVariable(pathVar="bookFilter") Map<String, List<String>> bookFilter, Model model) {
        Set<Book> booksByFilter = bookService.getBookListByFilter(bookFilter);
        model.addAttribute("bookList", booksByFilter);
        return "books";
    }

    @GetMapping("/book") // 특정 도서 상세 보기 (id로 조회)
    public String requestBookById(@RequestParam("id") String bookId, Model model) {
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "book"; // book.jsp 뷰 반환
    }

    @GetMapping("/add") // 도서 등록 폼 요청
    public String AddBookForm(@ModelAttribute("NewBook") Book book) {
        return "addBook";
    }

    @PostMapping("/add") // 도서 등록 처리
    public String submitAddNewBook(@Valid @ModelAttribute("NewBook") Book book, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) {
            return "addBook"; // 에러 있으면 다시 등록폼 보여줌
        }
        // 이미지 처리
        MultipartFile bookImage = book.getBookImage();
        String saveName = bookImage.getOriginalFilename();
        String path = request.getRealPath("resources/images");
        File saveFile = new File(path, saveName);
        if (bookImage != null && !bookImage.isEmpty()) {
            try {
                bookImage.transferTo(saveFile);
                book.setFileName(saveName);
            } catch(Exception e) {
                throw new RuntimeException("도서 이미지 업로드가 실패하였습니다.", e);
            }
        }
        bookService.setNewBook(book); // 도서 저장
        return "redirect:/books";
    }

    @GetMapping("/update") // 도서 수정 폼 요청
    public String getUpdateBookForm(@ModelAttribute("updateBook") Book book, @RequestParam("id") String bookId, Model model) {
        Book bookById = bookService.getBookById(bookId);
        model.addAttribute("book", bookById);
        return "updateForm";
    }

    @PostMapping("/update") // 도서 수정 처리
    public String submitUpdateBookForm(@ModelAttribute("updateBook") Book book) {
        MultipartFile bookImage = book.getBookImage();
        String rootDirectory = "c:/upload/";
        if(bookImage != null && !bookImage.isEmpty()) {
            try {
                String fname = bookImage.getOriginalFilename();
                bookImage.transferTo(new File(rootDirectory + fname));
                book.setFileName(fname);
            }catch(Exception e) {
                throw new RuntimeException("Book Image saving failed", e);
            }
        }
        bookService.setUpdateBook(book);
        return "redirect:/books";
    }

    @RequestMapping(value="/delete") // 도서 삭제 처리
    public String getDeleteBookForm(Model model, @RequestParam("id") String bookId) {
        bookService.setDelete(bookId);
        return "redirect:/books";
    }

    @ModelAttribute // 모든 요청에 addTitle이라는 속성을 기본 포함시킴
    public void addAttributes(Model model) {
        model.addAttribute("addTitle", "신규 도서 등록");
    }

    @InitBinder // 유효성 검사와 허용 필드 지정
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(bookValidator); // 커스텀 유효성 검사기 설정
        binder.setAllowedFields("bookId", "name", "unitPrice", "author", "description", "publisher", "category", "unitsInStock", "totalPages", "releaseDate", "condition", "bookImage");
    }

    @ExceptionHandler(value= {BookIdException.class}) // BookIdException 발생 시 처리
    public ModelAndView handleError(HttpServletRequest req, BookIdException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidBookId", exception.getBookId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL() + "?" + req.getQueryString());
        mav.setViewName("errorBook");
        return mav;
    }
}
