package com.fulln.proxys.life.config;

import com.fulln.proxys.life.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//使用注解获取配置文件中的值
@PropertySource("classpath:person.properties")
@Configuration
public class mainConfigPropertyConfig {

	@Bean
	public Person person(){
		return  new Person();
	}
}
