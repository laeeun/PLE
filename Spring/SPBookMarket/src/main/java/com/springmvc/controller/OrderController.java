package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.service.OrderService;

@Controller
public class OrderController {

	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order")
	public String process(@RequestParam("bookId") String bookId,
	                      @RequestParam("quantity") int quantity) {
	    orderService.confirmOrder(bookId, quantity);
	    return "redirect:/books";
	}

}
