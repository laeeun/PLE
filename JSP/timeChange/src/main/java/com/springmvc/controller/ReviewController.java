package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/review")
@Controller
public class ReviewController {
	
	@GetMapping("/form")
	public String reviewForm(@RequestParam("required = false") Long Id, @RequestParam("required = false") Long historyId, @RequestParam("required = false") ) {
		
	}
}
