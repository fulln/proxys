package com.fulln.proxys.ex;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
public class MyBeanFactory implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("init yes");
		int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
		String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
		System.out.println("当前bean中有"+beanDefinitionCount+"个bean");
		System.out.println(Arrays.asList(beanDefinitionNames));
	}
}
