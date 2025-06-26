package com.springmvc.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.springmvc.domain.Book;

@Component
public class UnitsInStockValidator implements Validator{
	public boolean supports(Class<?> calzz) {//Book 클래스의 유효성 검사 여부를 위한 메서드
		return Book.class.isAssignableFrom(calzz);
	}
	
	public void validate(Object target, Errors errors) { //Book 클래스의 유효성 검사 메서드 
		Book book = (Book) target;
		if(book.getUnitPrice() >= 10000 && book.getUnitsInStock() > 99) {
			//오류 객체의 속성과 메세지 저장
			errors.rejectValue("unitsInStock", "UnitsInStockValidator.message");
		}
	}
}
