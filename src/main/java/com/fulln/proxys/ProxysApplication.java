package com.fulln.proxys;

import com.fulln.proxys.annotation.EnableDynamicSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fulln
 * @description 测试类
 * @date  Created in  15:22  2020-04-25.
 */
@EnableDynamicSource
@EnableTransactionManagement
@SpringBootApplication
public class ProxysApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProxysApplication.class, args);
	}

}
