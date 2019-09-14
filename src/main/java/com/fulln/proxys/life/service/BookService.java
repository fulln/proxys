package com.fulln.proxys.life.service;

import com.fulln.proxys.life.dao.BookDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookService {

//	@Qualifier("bookDao")
//	@Autowired
//	@Resource
	@Inject
	private BookDao bookDao2;

	public void prints(){
		System.out.println(bookDao2);
	}

	@Override
	public String toString() {
		return "BookService{" +
				"bookDao=" + bookDao2 +
				'}';
	}
}
