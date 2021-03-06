package com.fulln.proxys.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.fulln.proxys.config.custom.CustomAnnotationConfiguration;
import com.fulln.proxys.config.custom.DefaultDynamicConfiguration;
import com.fulln.proxys.dto.DynamicSourceSwitchProp;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fulln.proxys.constant.DynamicSourceConstant.LOG_HEAD;

/**
 * @author fulln
 * @description 数据源动态创建
 * @date Created in  14:36  2020-04-25.
 * 目前不知道怎么获取到spring的入口类，所以我想象中的根据入口类上面的注解信息有没有
 * 我自定义的注解de这个Condition方法走不通，战术性放弃，等以后记起来是怎么获取了再来放开这个地方
 */
@Slf4j
@EnableConfigurationProperties(MybatisProperties.class)
@Configuration
@ConditionalOnBean(DefaultDynamicConfiguration.class)
@ConditionalOnMissingBean({SqlSessionFactory.class})
@AutoConfigureAfter(CustomAnnotationConfiguration.EnableCustomDynamicConfiguration.class)
public class DatasourceConfig {

	/**
	 * 获取spring中全部的自定义配置文件
	 */
	@Autowired
	private ConfigurableEnvironment environment;

	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private MybatisProperties mybatisProperties;

	public DataSource doCreateDatasource(String applicationUrl, String dbname) {
		//可以指定选择什么连接池
		return createSimpleDatabase(1, applicationUrl, dbname, environment);

	}

	private DataSource createSimpleDatabase(int dbType, String applicationUrl, String dbname, Environment environment) {
//		String dbName = DatasourceTypeEnum.getTypeByCode(dbType);
		HikariDataSource dataSource = new HikariDataSource();

		String prefix = applicationUrl + "." + dbname + ".";

		dataSource.setJdbcUrl(environment.getProperty(prefix + "jdbc-url"));
		log.info(LOG_HEAD.concat("create database connection [{}] dynamically ，url =[{}] "), dbname, dataSource.getJdbcUrl());
		dataSource.setUsername(environment.getProperty(prefix + "username"));
		dataSource.setPassword(environment.getProperty(prefix + "password"));
		dataSource.setDriverClassName(environment.getProperty(prefix + "driver-class-name"));
		return dataSource;
	}

	@Bean
	@SuppressWarnings("uncheck")
	public DynamicDataSourceSwitch createDynamicDataSourceSwitch() {
		DynamicDataSourceSwitch dynamicDataSourceSwitch = new DynamicDataSourceSwitch();

		//从bean中获取到之前设置的
		DynamicSourceSwitchProp prop = beanFactory.getBean(ClassUtils.getShortNameAsProperty(DynamicSourceSwitchProp.class), DynamicSourceSwitchProp.class);

		dynamicDataSourceSwitch.setProp(prop);

		//获取前缀下的dbName 并存入bean中
		String prefix = prop.getApplicationUrl();

		//遍历自定义的source
		MutablePropertySources propertySources = environment.getPropertySources();

		propertySources.forEach(propertySource -> {
			//对应远程env设置
			if (propertySource instanceof CompositePropertySource
			) {
				Collection<PropertySource<?>> collection = ((CompositePropertySource) propertySource).getPropertySources();
				if (CollectionUtils.isEmpty(collection)) {
					log.info(LOG_HEAD.concat("can't find any PropertySource in CompositePropertySource,will it get from remote source?"));
					return;
				}
				CompositePropertySource compositePropertySource = (CompositePropertySource) collection.stream().findFirst().get();
				Collection<PropertySource<?>> propertyCollect = compositePropertySource.getPropertySources();
				if (CollectionUtils.isEmpty(propertyCollect)) {
					log.warn(LOG_HEAD.concat("can't find any PropertySource in MapPropertySource,will it get from remote source?"));
					return;
				}
				MapPropertySource mapPropertySource = (MapPropertySource) propertyCollect.stream().findFirst().get();
				Set<String> collect = mapPropertySource.getSource().keySet().stream()
						.filter(key -> key.contains(prefix)).
								map(single -> {
									String replace = single.replace(prefix + ".", "");
									return replace.split("\\.")[0];
								}).collect(Collectors.toSet());
				if (CollectionUtils.isEmpty(collect)) {
					log.info(LOG_HEAD.concat("can't find any database name from remote,will it properties from remote source?"));
					return;
				}
				if (!CollectionUtils.isEmpty(prop.getDatabaseName())) {
					prop.getDatabaseName().addAll(collect);
				} else {
					prop.setDatabaseName(collect);
				}
				return;
			}
			//对应本地env设置
			if (propertySource instanceof OriginTrackedMapPropertySource) {
				Map<String, String> map = (Map<String, String>) propertySource.getSource();
				Set<String> collect = map.keySet().stream().
						filter(key -> key.contains(prefix)).
						map(single -> {
							//这里可以用 char 方式更优雅
							String replace = single.replace(prefix + ".", "");
							return replace.split("\\.")[0];
						}).collect(Collectors.toSet());
				if (CollectionUtils.isEmpty(collect)) {
					log.info(LOG_HEAD.concat("can't find any database name from local properties,will it properties from local source?"));
					return;
				}
				if (!CollectionUtils.isEmpty(prop.getDatabaseName())) {
					prop.getDatabaseName().addAll(collect);
				} else {
					prop.setDatabaseName(collect);
				}

			}
		});


		Map<Object, Object> map = new HashMap<>(prop.getDatabaseName().size());
		//自定义数据源key值，将创建好的数据源对象，赋值到targetDataSources中,用于切换数据源时指定对应key即可切换
		prop.getDatabaseName().forEach(dbname -> {
					map.put(dbname, doCreateDatasource(prop.getApplicationUrl(), dbname));
					if (StringUtils.isEmpty(prop.getDefaultDatasourceName())) {
						prop.setDefaultDatasourceName(dbname);
					}
				}
		);

		//动态数据源
		dynamicDataSourceSwitch.setTargetDataSources(map);

		//设置默认数据源
		dynamicDataSourceSwitch.setDefaultTargetDataSource(map.get(prop.getDefaultDatasourceName()));

		return dynamicDataSourceSwitch;
	}

	/**
	 * 开启事务
	 *
	 * @return
	 */
	@Bean(name = "TransactionManager")
	@ConditionalOnMissingBean
	public DataSourceTransactionManager testTransactionManager(DynamicDataSourceSwitch dataSourceSwitch) {
		return new DataSourceTransactionManager(dataSourceSwitch);
	}

	/**
	 * session factory
	 *
	 * @param dataSourceSwitch
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSourceSwitch dataSourceSwitch) throws Exception {
		return buildSqlSessionFactory(dataSourceSwitch, mybatisProperties.resolveMapperLocations());
	}

	/**
	 * mybatis-plus构建sessionFactory
	 *
	 * @param dataSource  数据源
	 * @param resources  mapper-location
	 * @return sqlSessionFactory
	 * @throws Exception
	 */
	static SqlSessionFactory buildSqlSessionFactory(DataSource dataSource, Resource[] resources) throws Exception {
		//通用设置
		GlobalConfig globalConfig = new GlobalConfig();
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
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
		sqlSessionFactory.setMapperLocations(resources);

		return sqlSessionFactory.getObject();
	}

}
