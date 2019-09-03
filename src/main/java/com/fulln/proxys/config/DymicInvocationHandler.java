package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponent;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

public class  DymicInvocationHandler implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		Class<?> declaringClass = method.getDeclaringClass();

		DataSourceComponent declaredAnnotation = AnnotationUtils.findAnnotation(declaringClass, DataSourceComponent.class);
		//dosomthing
		//。。。
		Object object = methodProxy.invokeSuper(o, objects);
		//dosomthing
		//。。。
		return object;
	}
}
