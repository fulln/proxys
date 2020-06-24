package com.fulln.proxys.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Objects;

public class InterfaceFactoryBean<T> implements FactoryBean {

	Class<T> clazz;

	@Override
	public Object getObject() throws Exception {
		// 检查 h 不为空，否则抛异常
		Objects.requireNonNull(clazz);
		return (T) Enhancer.create(clazz, new DynamicInvocationHandler());
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
