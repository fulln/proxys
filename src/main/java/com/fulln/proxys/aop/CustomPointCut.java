package com.fulln.proxys.aop;

import com.fulln.proxys.annotation.DataSourceComponent;
import com.fulln.proxys.config.DynamicSwitchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * @author fulln
 * @description pointcut的自定义字段
 * @date  Created in  17:42  2020-06-01.
 */
@Slf4j
public class CustomPointCut extends StaticMethodMatcherPointcut implements ICustomPointCut {

	@Override
	public boolean matches(Method method, Class<?> aClass) {

		if (CustomPointcutAdvisor.class.isAssignableFrom(aClass) ||
				DynamicSwitchConfig.class.isAssignableFrom(aClass)) {
			return false;
		}

		if (AnnotatedElementUtils.hasAnnotation(method, DataSourceComponent.class)) {
			log.info("获取到需要动态注解的class"+aClass.getName());
			return true;
		}

		return false;
	}

	/**
	 * @param clazz  注解的class
	 * @param method 获取注解的地方
	 * @return String的值
	 * @author fulln
	 * @description 从注解上获取对应的值，
	 * @date Created in  2020-07-02  15:25.
	 **/
	@Override
	public String getAnnotationAttr(Class clazz, Method method) {
		return null;
	}
}
