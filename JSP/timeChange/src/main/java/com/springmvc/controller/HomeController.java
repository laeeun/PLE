package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private String ID;
	private String PW;
	private String Name;
	private String Email;
	private String phone1;
	private String phone2;
	private String phone3;
	private String address;
	String isExpert;
	
	@RequestMapping("/")
	public String main() {
		
		return "home";
	}
}
