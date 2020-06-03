package com.fulln.demo.controller;

import com.fulln.demo.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class testController {

	@Autowired
	private testService testService;

	@GetMapping("1")
	public String get1(){
		testService.test01();
		return "1";
	}

	@GetMapping("2")
	public String get2(){
		testService.test02();
		return "2";
	}
}
