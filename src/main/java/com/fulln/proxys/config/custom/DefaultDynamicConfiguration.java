package com.fulln.proxys.config.custom;

import com.fulln.proxys.aop.CustomInterceptor;
import com.fulln.proxys.aop.CustomPointCut;
import com.fulln.proxys.aop.CustomPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author fulln
 * @description 默认的注解配置文件
 * @date Created in  15:18  2020-06-29.
 */
@Configuration
public class DefaultDynamicConfiguration extends AbstractDynamicConfiguration {

	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description Advisor 用来封装切面的所有信息，主要是上面两个，它用来充当 Advice 和 Pointcut 的适配器。
	 * @date Created in  2020-06-30  17:44.
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CustomPointcutAdvisor cpAdvisor() {
		CustomPointcutAdvisor advisor = new CustomPointcutAdvisor();
		advisor.setInterceptor(customInterceptor());
		advisor.setCustomPointCut(customPointCut());
		advisor.setOrder(this.enableDy.<Integer>getNumber("order"));
		return advisor;
	}

	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description Advice 用来定义拦截行为，在这里实现增强的逻辑
	 * @date Created in    .
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CustomInterceptor customInterceptor() {
		return new CustomInterceptor();
	}

	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description Pointcut 定义一个切点，可以在这个被拦截的方法前后进行切面逻辑
	 * @date Created in
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CustomPointCut customPointCut() {
		return new CustomPointCut();
	}


}
