package com.fulln.proxys.ex;

import com.fulln.proxys.life.entity.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefind implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("实现子接口新定义的方法，bean数量是"+registry.getBeanDefinitionCount());
		//使用root
//		registry.registerBeanDefinition("cars",new RootBeanDefinition(Car.class));
		//使用builder
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Car.class).getBeanDefinition();
		registry.registerBeanDefinition( "car2",beanDefinition);
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("实现父接口带的方法"+beanFactory.getBeanDefinitionCount());
	}
}
