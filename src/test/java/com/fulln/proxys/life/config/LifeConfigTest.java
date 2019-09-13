package com.fulln.proxys.life.config;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class LifeConfigTest {

	@Test
	public void car() {
		//1. 创建ioc容器
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(LifeConfig.class);
		System.out.println("容器创建完成");

		annotationConfigApplicationContext.close();
		System.out.println("容器关闭");


	}
}