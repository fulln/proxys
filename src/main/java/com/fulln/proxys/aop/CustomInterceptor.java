package com.fulln.proxys.aop;

import com.fulln.proxys.constant.DynamicSourceConstant;
import com.fulln.proxys.dto.CustomAnnotationProperties;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import java.io.Serializable;

/**
 * @author fulln
 * @description 自定义拦截
 * @date  Created in  17:46  2020-06-01.
 */
@Slf4j
public class CustomInterceptor implements MethodInterceptor, Serializable {

	private CustomAnnotationProperties properties;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		log.info(DynamicSourceConstant.LOG_HEAD.concat("start change current datasource config" + targetClass.getName()));
		//开始执行相关操作
		return invocation.proceed();
	}

	public CustomAnnotationProperties getProperties() {
		return properties;
	}

	public void setProperties(CustomAnnotationProperties properties) {
		this.properties = properties;
	}
}
