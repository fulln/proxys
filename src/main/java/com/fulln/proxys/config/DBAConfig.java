package com.fulln.proxys.config;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import static com.fulln.proxys.config.DBBConfig.buildSqlSessionFactory;

/**
 * 数据源2的配置(选)
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"com.fulln.proxyx.dao.basic"},sqlSessionFactoryRef = "sqlSessionFactory1")
public class DBAConfig {

    @Autowired
    private MybatisProperties mybatisProperties;

    /**
     * application.properteis中对应属性的前缀
     */
    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.data2")
    public DataSource dataSource2() {
        return new HikariDataSource();
    }


    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("db1")DataSource db2) throws Exception {
        return buildSqlSessionFactory(db2,mybatisProperties.getMapperLocations()[1]);
    }

    @Bean(name = "TransactionManager1")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("db1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}