package com.fulln.proxys.aop;

import com.fulln.proxys.dto.CustomAnnotationProperties;
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

	private AbstractCustomPointcutDecorator pointcutDecorator = new AbstractCustomPointcutDecorator() {
		@Override
		public ICustomPointCut getCustomPointCut() {
			return customPointCut;
		}
	};

	private ICustomPointCut customPointCut;

	private CustomInterceptor interceptor;


	@Override
	public Pointcut getPointcut() {
		CustomAnnotationProperties properties = interceptor.getProperties();
		this.pointcutDecorator.setProperties(properties);
		return this.pointcutDecorator;
	}

	@Override
	public Advice getAdvice() {
		return this.interceptor ;
	}


	public void setCustomPointCut(ICustomPointCut customPointCut) {
		this.customPointCut = customPointCut;
	}

	public void setInterceptor(CustomInterceptor interceptor) {
		this.interceptor = interceptor;
	}

	public CustomPointcutAdvisor() {
	}

}
