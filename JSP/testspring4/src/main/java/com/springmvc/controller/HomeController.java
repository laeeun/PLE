package com.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.domain.userDto;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String main() {
		return "home";
	}
	
	@PostMapping(value="/case1", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String case1(@RequestBody HashMap<String, String> map){
		System.out.println("mapping Ok");
		String name =(String)map.get("name");
		String age =(String)map.get("age");
		
		System.out.println(map.get("name"));
		System.out.println(map.get("age"));
		System.out.println(map);
		return "성공";
	}
	
	@PostMapping("/case2")
	@ResponseBody
	public userDto case2(@RequestBody HashMap<String, String> map) {
		System.err.println("case2");
		String id = (String)map.get("id");
		userDto dto = new userDto();
		dto.setId(id);
		return dto;
	}
	
	@ResponseBody
	@PostMapping("/case3")
	public List<userDto> case3(@RequestBody HashMap<String, String> map){
		System.out.println("case3");
		String id = (String)map.get("id");
		userDto dto1 = new userDto();
		dto1.setId("1번");
		dto1.setName("kim");
		userDto dto2 = new userDto();
		dto2.setId("2번");
		dto2.setName("Lee");
		userDto dto3 = new userDto();
		dto3.setId("3번");
		dto3.setName("choi");
		ArrayList<userDto> list = new ArrayList<userDto>();
		list.add(dto1);
		list.add(dto2);
		list.add(dto3);
		return list;
	}
}
