package com.fulln.proxys.life.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@ToString
@Data
public class Person {
	/**
	 * 使用value 赋值
	 * 1。基本数值
	 * 2。写SPEL #{}
	 * 3. 写${}  取出配置中的值
	 */
	@Value("张三")
	private String name;
	@Value("#{20-3}")
	private String age;
	@Value("${person.nickName}")
	private String nickName;





	public Person() {
		System.out.println("person construct ==>");
	}
}
