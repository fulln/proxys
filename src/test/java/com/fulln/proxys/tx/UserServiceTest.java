package com.fulln.proxys.tx;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserServiceTest {

	@Test
	public void update() {
		AnnotationConfigApplicationContext
				context =  new AnnotationConfigApplicationContext(TxConfig.class);

		UserService service = context.getBean(UserService.class);
		service.update();
	}
}