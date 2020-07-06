package com.fulln.proxys.aop;

import com.fulln.proxys.constant.DynamicSourceConstant;
import com.fulln.proxys.dto.CustomAnnotationProperties;
import com.fulln.proxys.dto.DynamicSourceSwitchProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.List;

/**
 * @author fulln
 * @description 用来初始化功能性的bean和属性
 * @date Created in  16:00  2020-07-02.
 **/
@Slf4j
public class CustomInterceptorSupport implements BeanFactoryAware, InitializingBean {

	@Nullable
	private BeanFactory beanFactory;

	@Nullable
	private CustomAnnotationProperties properties;

	@Nullable
	protected DynamicSourceSwitchProp prop;
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

	/**
	 * Invoked by the containing {@code BeanFactory} after it has set all bean properties
	 * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
	 * <p>This method allows the bean instance to perform validation of its overall
	 * configuration and final initialization when all bean properties have been set.
	 *
	 * @throws Exception in the event of misconfiguration (such as failure to set an
	 *                   essential property) or if initialization fails for any other reason
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.beanFactory == null) {
			throw new IllegalStateException(
					"Set the 'interceptor' property or make sure to run within a BeanFactory " +
							"containing a PlatformTransactionManager bean!");
		}
		if (this.properties == null) {
			throw new IllegalArgumentException(
					"Set the 'properties' property or make sure to run within a support " +
							"containing a PlatformTransactionManager bean!");
		}
		registerPropertiesBean();
		prop = getBeanFactory().getBean(ClassUtils.getShortNameAsProperty(DynamicSourceSwitchProp.class), DynamicSourceSwitchProp.class);
	}

	/**
	 * 注册一个配置bean  以提供给数据源使用
	 */
	private void registerPropertiesBean() {
		//因为是从context中获取到的bean Factory 所以在这边转换是没有问题的
		BeanDefinitionRegistry registry = (DefaultListableBeanFactory) beanFactory;

		String applicationUrl = properties.getApplicationUrl();

		String defaultDatasourceName = properties.getDefaultDatasourceName();

		log.info("从配置文件中获取到对应的路径:{},开始注册配置类", applicationUrl);

		BeanDefinitionBuilder builder =
				BeanDefinitionBuilder.genericBeanDefinition(DynamicSourceSwitchProp.class)
						.addPropertyValue(DynamicSourceConstant.APPLICATION_URL, applicationUrl)
						.addPropertyValue(DynamicSourceConstant.DEFAULT_DATASOURCE_NAME, defaultDatasourceName);

		String beanName = ClassUtils.getShortNameAsProperty(DynamicSourceSwitchProp.class);

		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

		List<String> packages = AutoConfigurationPackages.get(beanFactory);

		String urls = packages.stream().findAny().orElse("");

		properties.setDefaultPackageName(urls);
	}

	public BeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	public CustomAnnotationProperties getProperties() {
		return properties;
	}

	public void setProperties(CustomAnnotationProperties properties) {
		this.properties = properties;
	}


}
