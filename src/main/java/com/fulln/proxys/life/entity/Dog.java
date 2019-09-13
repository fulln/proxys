package com.fulln.proxys.life.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

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
}
