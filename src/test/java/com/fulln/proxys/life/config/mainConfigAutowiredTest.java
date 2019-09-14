package com.fulln.proxys.life.config;

import com.fulln.proxys.life.dao.BookDao;
import com.fulln.proxys.life.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class mainConfigAutowiredTest {

	AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(mainConfigAutowired.class);

	@Test
	public void test(){

		BookService bean = annotationConfigApplicationContext.getBean(BookService.class);
		System.out.println(bean);
		BookDao bean1 = (BookDao) annotationConfigApplicationContext.getBean("bookDao2");
		System.out.println(bean1);
		annotationConfigApplicationContext.close();
	}

	private void printBean(AnnotationConfigApplicationContext annotationConfigApplicationContext){
		String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
	}

}