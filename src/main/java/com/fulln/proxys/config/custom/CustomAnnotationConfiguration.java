package com.fulln.proxys.config.custom;

import com.fulln.proxys.annotation.EnableDynamicSource;
import com.fulln.proxys.config.DatasourceConfig;
import com.fulln.proxys.config.DynamicSwitchConfig;
import com.fulln.proxys.dto.CustomAnnotationProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author fulln
 * @description 自定义注解的spring启动类，目前只提供operation 等功能实现再考虑template
 * @date Created in  09:54  2020-06-24.
 */
@AutoConfigureAfter(DatasourceConfig.class)
@EnableConfigurationProperties(CustomAnnotationProperties.class)
public class CustomAnnotationConfiguration {

//	@ConditionalOnSingleCandidate(DatasourceConfig.class)
//	public static class TransactionTemplateConfiguration {
//
//		@Bean
//		@ConditionalOnMissingBean(TransactionOperations.class)
//		public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
//			return new TransactionTemplate(transactionManager);
//		}
//
//	}

	/**
	 * 默认使用cglib的动态代理模式
	 * 目前不考虑用户自定义的情况
	 * 当没有使用自定义注解的时候
	 */
	@Configuration
	//	@ConditionalOnBean(DynamicDataSourceSwitch.class)
	@ConditionalOnMissingBean(DynamicSwitchConfig.class)
	public static class EnableCustomDynamicConfiguration {

		@Configuration
		@EnableDynamicSource
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "false",
				matchIfMissing = false)
		public static class JdkCustomDynamicAutoProxyConfiguration {

		}

		@Configuration
		@EnableDynamicSource
		@ConditionalOnProperty(prefix = "spring.aop", name = "proxy-target-class", havingValue = "true",
				matchIfMissing = true)
		public static class CglibCustomDynamicConfiguration {

		}

	}


}
