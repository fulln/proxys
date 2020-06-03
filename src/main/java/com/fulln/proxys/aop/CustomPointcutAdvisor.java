package com.fulln.proxys.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author fulln
 * @description  自定义的advisor 用于注入自定义的相关注解
 * @date  Created in  19:22  2020-05-28.
 */
public class CustomPointcutAdvisor extends AbstractPointcutAdvisor {

	private CustomPointCut customPointCut;
	private CustomInterceptor interceptor;



	@Override
	public Pointcut getPointcut() {
		return this.customPointCut;
	}

	@Override
	public Advice getAdvice() {
		return this.interceptor ;
	}

	public CustomPointcutAdvisor() {
		this.customPointCut =  new CustomPointCut();
		this.interceptor = new CustomInterceptor();
	}

	public CustomPointcutAdvisor(CustomInterceptor interceptor) {
		this.interceptor = new CustomInterceptor();
	}


}
