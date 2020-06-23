package com.fulln.proxys.config;

import com.fulln.proxys.annotation.EnableDynamicSource;
import com.fulln.proxys.dto.DynamicSourceSwitchProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author fulln
 * @description 动态数据源动态生成
 * @date Created in  15:30  2020-04-25.
 */
@Slf4j
public class DynamicSwitchConfig implements BeanFactoryAware, ImportBeanDefinitionRegistrar {

	private BeanFactory beanFactory;

	/**
	 * Register bean definitions as necessary based on the given annotation metadata of
	 * the importing {@code @Configuration} class.
	 * <p>Note that {@link } types may <em>not</em> be
	 * registered here, due to lifecycle constraints related to {@code @Configuration}
	 * class processing.
	 *
	 * @param importingClassMetadata annotation metadata of the importing class
	 * @param registry               current bean definition registry
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		//拿到类上的自定义注解的属性
		AnnotationAttributes annAttr = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableDynamicSource.class.getName()));


		String applicationUrl = "spring.datasource";
		String defaultDatasourceName = "";
		if (annAttr != null) {
			applicationUrl = annAttr.getString("ApplicationUrl");
			defaultDatasourceName = annAttr.getString("defaultSourceName");
		}

		log.info("从配置文件中获取到对应的路径:{},开始注册配置类", applicationUrl);

		BeanDefinitionBuilder builder =
				BeanDefinitionBuilder.genericBeanDefinition(DynamicSourceSwitchProp.class)
						.addPropertyValue("applicationUrl", applicationUrl)
						.addPropertyValue("defaultDatasourceName", defaultDatasourceName);

		String beanName = ClassUtils.getShortNameAsProperty(DynamicSourceSwitchProp.class);

		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

		log.info("注册bean:{},检查是否成功:{}", beanName, beanFactory.getBean(beanName) != null);

		List<String> packages = AutoConfigurationPackages.get(this.beanFactory);

		if (ObjectUtils.isEmpty(packages)) {
			packages = Collections.singletonList(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
		}

		String urls = packages.stream().findAny().orElse("");

		if (!StringUtils.isEmpty(urls)) {
			//InfrastructureAdvisorAutoProxyCreator
		}

	}


	/**
	 * Callback that supplies the owning factory to a bean instance.
	 * <p>Invoked after the population of normal bean properties
	 * but before an initialization callback such as
	 * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
	 *
	 * @param beanFactory owning BeanFactory (never {@code null}).
	 *                    The bean can immediately call methods on the factory.
	 * @throws BeansException in case of initialization errors
	 * @see BeanInitializationException
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}


	@Configuration
	@ConditionalOnBean(DatasourceConfig.class)
	public static class ScanPackagesAndRegistered implements InitializingBean {

		@Override
		public void afterPropertiesSet() throws Exception {
			log.info("current bean registered ok");
		}
	}

}
