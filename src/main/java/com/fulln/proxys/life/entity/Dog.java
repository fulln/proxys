package com.fulln.proxys.life.entity;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public Dog() {
		System.out.println("Dog construct");
	}

	@PostConstruct
	public void init(){
		System.out.println("Dog  before    init");
	}

	@PreDestroy
	public void destory(){
		System.out.println("Dog before destroy");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
