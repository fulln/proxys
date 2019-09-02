package com.fulln.proxys.config;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * 数据源2的配置(选)
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"com.fulln.proxys.dao.system"},sqlSessionFactoryRef = "sqlSessionFactory2")
public class DBBConfig {

    @Autowired
    private MybatisProperties mybatisProperties;

    /**
     * application.properteis中对应属性的前缀
     */
    @Bean(name = "db2")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.data1")
    public DataSource dataSource2() {
        return new HikariDataSource();
    }


    @Bean(name = "sqlSessionFactory2")
    @Primary
    public SqlSessionFactory sqlSessionFactory2(@Qualifier("db2")DataSource db2) throws Exception {
        return buildSqlSessionFactory(db2,mybatisProperties.getMapperLocations()[0]);
    }

    @Primary
    @Bean(name = "TransactionManager2")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("db2") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    static SqlSessionFactory buildSqlSessionFactory(DataSource dataSource, String sqlMapConfig) throws Exception {
        //通用设置
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
//        dbConfig.setTablePrefix("t_");
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setBanner(false);

        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);

        // 配置SQL打印，仅用于开发环境
        mybatisConfiguration.setLogImpl(StdOutImpl.class);

        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setPlugins(new PaginationInterceptor());
        sqlSessionFactory.setConfiguration(mybatisConfiguration);
        sqlSessionFactory.setGlobalConfig(globalConfig);
        sqlSessionFactory.setDataSource(dataSource);
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(sqlMapConfig);
            sqlSessionFactory.setMapperLocations(resources);
        } catch (IOException e) {
            log.error("加载 sqlmap 配置文件失败，路径={}, 详情={}", sqlMapConfig, e.getMessage(), e);
        }
        return sqlSessionFactory.getObject();
    }

}