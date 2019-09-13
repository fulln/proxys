package com.fulln.proxys.life.entity;

/**
 * @author fulln
 * @description  simple entity
 * @date  Created in  21:27  2019-09-13.
 */
public class Car {

	public Car() {
		System.out.println("construct car ==>");
	}

	public void init(){
		System.out.println("init car ==>");
	}

	private void destroy() {
		System.out.println("desory car ==>");
	}


}
