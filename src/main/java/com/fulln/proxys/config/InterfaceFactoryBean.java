package com.fulln.proxys.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Objects;

public class InterfaceFactoryBean<T> implements FactoryBean {

	@Override
	public Object getObject() throws Exception {

		// 检查 h 不为空，否则抛异常
		Objects.requireNonNull();
		return (T) Enhancer.create(,new DymicInvocationHandler());
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}
}
