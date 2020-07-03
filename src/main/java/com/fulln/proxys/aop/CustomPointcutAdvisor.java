package com.fulln.proxys.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author fulln
 * @description  自定义的advisor 用于注入自定义的相关注解
 * @date  Created in  19:22  2020-05-28.
 */
@Slf4j
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


	public void setCustomPointCut(CustomPointCut customPointCut) {
		this.customPointCut = customPointCut;
	}

	public void setInterceptor(CustomInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public CustomPointcutAdvisor() {
	}

}
