package com.fulln.proxys.life.config;

import com.fulln.proxys.life.aop.MathCalculate;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainConfigOfAopTest {

	AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);

	@Test
	public void test(){
		MathCalculate bean = annotationConfigApplicationContext.getBean(MathCalculate.class);

		bean.div(6,2);

		annotationConfigApplicationContext.close();
	}


}