package com.fulln.proxys.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fulln
 * @description pointcut的自定义字段
 * @date  Created in  17:42  2020-06-01.
 */
@Slf4j
public class CustomPointCut implements ICustomPointCut {

	private final Map<Object, String> attributeCache = new ConcurrentHashMap<>(1024);
	/**
	 * @param clazz  注解的class
	 * @param method 获取注解的地方
	 * @return String的值
	 * @author fulln
	 * @description 从注解上获取对应的值，
	 * @date Created in  2020-07-02  15:25.
	 **/
	@Override
	public String getAnnotationAttr(Class clazz, Method method) {
		return null;
	}
}
