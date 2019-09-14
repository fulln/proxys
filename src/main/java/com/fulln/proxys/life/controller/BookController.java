package com.fulln.proxys.life.controller;

import com.fulln.proxys.life.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
}
