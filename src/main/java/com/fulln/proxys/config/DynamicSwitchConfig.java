package com.fulln.proxys.config;

import com.fulln.proxys.annotation.EnableDynamicSource;
import com.fulln.proxys.dto.DynamicSourceSwitchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * @author fulln
 * @description 动态数据源动态生成
 * @date Created in  15:30  2020-04-25.
 */
@Slf4j
public class DynamicSwitchConfig implements ImportBeanDefinitionRegistrar {


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
		if (annAttr != null) {
			applicationUrl = annAttr.getString("ApplicationUrl");
		}

		log.info("从配置文件中获取到对应的路径:{},开始注册配置类", applicationUrl);

		//直接构建一个pojo bean
		DefaultListableBeanFactory beanFactory =
				new DefaultListableBeanFactory();

		BeanDefinitionBuilder b =
				BeanDefinitionBuilder.rootBeanDefinition(DynamicSourceSwitchDto.class)
						.addPropertyValue("applicationUrl", applicationUrl);

		String beanName = ClassUtils.getShortNameAsProperty(DynamicSourceSwitchDto.class);

		beanFactory.registerBeanDefinition(beanName, b.getBeanDefinition());

		Object bean = beanFactory.getBean(beanName);

//		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DynamicSourceSwitchDto.class);
//
//		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
//
//		GenericBeanDefinition rawBeanDefinition = (GenericBeanDefinition)builder.getRawBeanDefinition();
//
//		rawBeanDefinition.getPropertyValues().add("applicationUrl",applicationUrl);
//
//		rawBeanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//
//		rawBeanDefinition.setBeanClass(DynamicSourceSwitchDto.class);
//		//注册bean
//		registry.registerBeanDefinition(ClassUtils.getShortNameAsProperty(DynamicSourceSwitchDto.class),rawBeanDefinition);

		log.info("注册单例bean:{}",beanName);
	}



}
