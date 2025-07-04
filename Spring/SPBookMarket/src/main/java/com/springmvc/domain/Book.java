package com.springmvc.domain; // 도메인 패키지: 데이터 모델 클래스가 모여 있음

import java.io.Serializable; // 직렬화 가능하게 만들어주는 인터페이스

import javax.validation.constraints.Digits; // 숫자 자리수 유효성 검사
import javax.validation.constraints.Min; // 최소값 유효성 검사
import javax.validation.constraints.NotNull; // null 허용 안함
import javax.validation.constraints.Pattern; // 정규식 유효성 검사
import javax.validation.constraints.Size; // 문자열 길이 검사

import org.springframework.web.multipart.MultipartFile; // 업로드된 파일을 받는 타입

import com.springmvc.validator.BookId; // 사용자 정의 BookId 검증 애너테이션

public class Book implements Serializable { // Book 객체는 직렬화 가능해야 세션에 저장하거나 전송 가능
	private static final long seriaVersionUID = -7715651009026349175L; // 클래스 버전 관리용 ID

	@BookId // 사용자 정의 애너테이션으로 도서 ID 형식 검증 (예: 중복 방지)
	@Pattern(regexp="ISBN[1-9]+", message="{Pattern.NewBook.bookId}") // ISBN으로 시작하는 패턴만 허용
	private String bookId; // 도서 ID

	@Size(min=4, max=50, message="{Size.NewBook.name}") // 이름은 4~50자 사이여야 함
	private String name; // 도서명

	@Min(value=0, message="{Min.NewBook.unitPrice}") // 최소값 0 이상
	@Digits(integer=8, fraction=2, message="{Digits.NewBook.unitPrice}") // 정수 8자리 + 소수점 2자리까지
	@NotNull(message="{NotNull.NewBook.unitPrice}") // null 허용 안함
	private int unitPrice; // 도서 가격

	private String author; // 저자 이름
	private String description; // 책 설명
	private String publisher; // 출판사 이름
	private String category; // 도서 분류 (예: IT, 소설 등)
	private long unitsInStock; // 재고 수량
	private String releaseDate; // 출간일 (yyyy/mm/dd 형태)
	private String condition; // New / Old / E-Book 등 도서 상태
	private MultipartFile bookImage; // 업로드할 도서 이미지 파일
	private String fileName; // 실제 저장된 이미지 파일명

	// 기본 생성자
	public Book() {
		super();
	}

	// 책 ID, 이름, 가격만 받는 생성자
	public Book(String bookId, String name, int unitPrice) {
		super();
		this.bookId = bookId;
		this.name = name;
		this.unitPrice = unitPrice;
	}

	// 아래는 모든 필드의 getter, setter
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(long unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public MultipartFile getBookImage() {
		return bookImage;
	}

	public void setBookImage(MultipartFile bookImage) {
		this.bookImage = bookImage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	// 객체 정보 문자열로 반환 (디버깅용)
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", name=" + name + ", unitPrice=" + unitPrice + ", author=" + author
				+ ", description=" + description + ", publisher=" + publisher + ", category=" + category
				+ ", unitsInStock=" + unitsInStock + ", releaseDate=" + releaseDate + ", condition=" + condition
				+ ", bookImage=" + bookImage + "]";
	}
}
