package com.fulln.proxys.aop;

import com.fulln.proxys.constant.DynamicSourceConstant;
import com.fulln.proxys.dto.DynamicSourceSwitchProp;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author fulln
 * @description  自定义的advisor 用于注入自定义的相关注解
 * @date  Created in  19:22  2020-05-28.
 */
@Slf4j
public class CustomPointcutAdvisor extends AbstractPointcutAdvisor implements ImportBeanDefinitionRegistrar {

	private CustomPointCut customPointCut;
	private CustomInterceptor interceptor;
	private AnnotationAttributes enableDy;



	@Override
	public Pointcut getPointcut() {
		return this.customPointCut;
	}

	@Override
	public Advice getAdvice() {
		return this.interceptor ;
	}


	public void setCustomPointCut(CustomPointCut customPointCut) {
		this.customPointCut = customPointCut;
	}

	public void setInterceptor(CustomInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public CustomPointcutAdvisor() {
	}

	public CustomPointcutAdvisor(CustomInterceptor interceptor) {
		this.interceptor = new CustomInterceptor();
	}


	/**
	 * 用来注册相关的配置
	 *
	 * @param importingClassMetadata annotation metadata of the importing class
	 * @param registry               current bean definition registry
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		this.setOrder(enableDy.<Integer>getNumber("order"));
		String applicationUrl, defaultDatasourceName;
		applicationUrl = enableDy.getString("ApplicationUrl");
		defaultDatasourceName = enableDy.getString("defaultSourceName");

		log.info(DynamicSourceConstant.LOG_HEAD.concat("从配置文件中获取到对应的路径:{},开始注册配置类{}"), applicationUrl);

		BeanDefinitionBuilder builder =
				BeanDefinitionBuilder.genericBeanDefinition(DynamicSourceSwitchProp.class)
						.addPropertyValue("applicationUrl", applicationUrl)
						.addPropertyValue("defaultDatasourceName", defaultDatasourceName);

		String beanName = ClassUtils.getShortNameAsProperty(DynamicSourceSwitchProp.class);

		registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
	}

	public void setProp(AnnotationAttributes enableDy) {
		this.enableDy = enableDy;
	}
}
