package com.fulln.proxys.ex;

import com.fulln.proxys.life.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.fulln.proxys.ex")
@Configuration
public class Exconfig {

	@Bean
	public Car car(){
		return new Car();
	}

}
