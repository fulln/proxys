package com.fulln.proxys.life.config;

import com.fulln.proxys.life.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class mainConfigPropertyConfigTest {

	AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(mainConfigPropertyConfig.class);

	@Test
	public void test(){
//		printBean(annotationConfigApplicationContext);
		Person person = (Person) annotationConfigApplicationContext.getBean("person");
		System.out.println(person);


		ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
		String property = environment.getProperty("person.nickName");
		System.out.println(property);
		annotationConfigApplicationContext.close();
	}

	private void printBean(AnnotationConfigApplicationContext annotationConfigApplicationContext){
		String[] beanDefinitionNames = annotationConfigApplicationContext.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			System.out.println(beanDefinitionName);
		}
	}

}