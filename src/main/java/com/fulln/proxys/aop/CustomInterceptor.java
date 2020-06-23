package com.fulln.proxys.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
/**
 * @author fulln
 * @description 自定义拦截
 * @date  Created in  17:46  2020-06-01.
 */
public class CustomInterceptor implements MethodInterceptor, Serializable {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		//使用代理进行数据yuan切换
		return null;
	}
}
