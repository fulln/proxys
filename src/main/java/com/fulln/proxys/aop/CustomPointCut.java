package com.fulln.proxys.aop;

import com.fulln.proxys.annotation.DataSourceComponent;
import com.fulln.proxys.config.DynamicSwitchConfig;
import com.fulln.proxys.constant.DynamicSourceConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author fulln
 * @description pointcut的自定义字段
 * @date  Created in  17:42  2020-06-01.
 */
@Slf4j
public class CustomPointCut extends StaticMethodMatcherPointcut implements Serializable {

	@Override
	public boolean matches(Method method, Class<?> aClass) {

		if (CustomPointcutAdvisor.class.isAssignableFrom(aClass) ||
				DynamicSwitchConfig.class.isAssignableFrom(aClass)) {
			return false;
		}

		log.info(DynamicSourceConstant.LOG_HEAD.concat("开始匹配符合要求的bean，当前bean为:{}"), aClass.getName());

		Annotation[] annotations = method.getAnnotations();
		if (Arrays.stream(annotations).anyMatch(annotation -> annotation.getClass().isAssignableFrom(DataSourceComponent.class))) {
			log.info("获取到需要动态注解的class"+aClass.getName());
			return true;
		}

		return false;
	}
}
