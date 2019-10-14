package com.fulln.proxys.tx;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 声明式事务
 *
 * 配置数据源
 */
@EnableTransactionManagement
@ComponentScan("com.fulln.proxys.tx")
@Configuration
public class TxConfig {


	//数据源A


	@Bean
	public DataSource dataSource(){
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("qq1203943228");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/api?useSSL=false");
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return dataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(){
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public PlatformTransactionManager transactionManager (){
		return new DataSourceTransactionManager(dataSource());
	}

}
