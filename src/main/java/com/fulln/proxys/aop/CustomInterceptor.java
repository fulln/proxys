package com.fulln.proxys.aop;

import com.fulln.proxys.constant.DynamicSourceConstant;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

/**
 * @author fulln
 * @description 自定义拦截
 * @date  Created in  17:46  2020-06-01.
 */
@Slf4j
public class CustomInterceptor extends CustomInterceptorSupport implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		log.info(DynamicSourceConstant.LOG_HEAD.concat("start change current datasource config" + targetClass.getName()));


		//开始执行相关操作
		return invocation.proceed();


	}
}
