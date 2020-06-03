package com.fulln.proxys.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.AbstractSingletonProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author fulln
 * @description advice的factorybean
 * @date  Created in  10:30  2020-06-02.
 */
@SuppressWarnings("serial")
public class CustomAdviceFactoryBean extends AbstractSingletonProxyFactoryBean
		implements BeanFactoryAware {

	private final CustomInterceptor interceptor = new CustomInterceptor();
	private BeanFactory beanFactory;
	private Pointcut pointcut;
	/**
	 * Create the "main" interceptor for this proxy factory bean.
	 * Typically an Advisor, but can also be any type of Advice.
	 * <p>Pre-interceptors will be applied before, post-interceptors
	 * will be applied after this interceptor.
	 */
	@Override
	protected Object createMainInterceptor() {

		if (this.pointcut != null) {
			return new DefaultPointcutAdvisor(this.pointcut, this.interceptor);
		}
		else {
			// Rely on default pointcut.
			return new CustomPointcutAdvisor(this.interceptor);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	/**
	 * Set a pointcut, i.e a bean that can cause conditional invocation
	 * of the TransactionInterceptor depending on method and attributes passed.
	 * Note: Additional interceptors are always invoked.
	 * @see #setPreInterceptors
	 * @see #setPostInterceptors
	 */
	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}
}
