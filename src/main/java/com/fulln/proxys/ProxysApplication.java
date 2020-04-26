package com.fulln.proxys;

import com.fulln.proxys.annotation.EnableDynamicSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fulln
 * @description 测试类
 * @date  Created in  15:22  2020-04-25.
 */
@EnableDynamicSource
@MapperScan(value = "com.fulln.proxys.dao")
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
public class ProxysApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProxysApplication.class, args);
	}

}
