package com.fulln.proxys.config.custom;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author fulln
 * @description 用于获取beanFactory
 * @date Created in  10:58  2020-07-02.
 **/
@Configuration
public class CustomSwitchManageSource implements BeanFactoryAware {


	/**
	 * Callback that supplies the owning factory to a bean instance.
	 * <p>Invoked after the population of normal bean properties
	 * but before an initialization callback such as
	 * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
	 *
	 * @param beanFactory owning BeanFactory (never {@code null}).
	 *                    The bean can immediately call methods on the factory.
	 * @throws BeansException in case of initialization errors
	 * @see BeanInitializationException
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

	}
}
