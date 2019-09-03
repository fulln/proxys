package com.fulln.proxys;

import com.fulln.proxys.annotation.DataSourceComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@DataSourceComponentScan("com.fulln.proxy.dao")
@EnableTransactionManagement
@SpringBootApplication
public class ProxysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxysApplication.class, args);
	}

}
