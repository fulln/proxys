package com.fulln.proxys.annotation;

import com.fulln.proxys.config.HsfBeanDefinitionRegistrar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author fulln
 * @description import  传入的3种方法
 * @date Created in  23:36  2019-09-03.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(HsfBeanDefinitionRegistrar.class)
public @interface DataSourceComponentScan {

	/**
	 * @return
	 */
	String[] value() default {};

	/**
	 * 扫描包
	 *
	 * @return
	 */
	String[] basePackages() default {};

	/**
	 * 扫描的基类
	 *
	 * @return
	 */
	Class<?>[] basePackageClasses() default {};

	/**
	 * 包含过滤器
	 *
	 * @return
	 */
	ComponentScan.Filter[] includeFilters() default {};

	/**
	 * 排斥过滤器
	 *
	 * @return
	 */
	ComponentScan.Filter[] excludeFilters() default {};
}