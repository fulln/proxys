package com.fulln.proxys.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author fulln
 * @description pointcut的自定义字段
 * @date  Created in  17:42  2020-06-01.
 */
@Slf4j
public class CustomPointCut implements ICustomPointCut {
	/**
	 * @param clazz  注解的class
	 * @param method 获取注解的地方
	 * @return String
	 * @author fulln
	 * @description 从注解上获取对应的值，用来处理自定义的相关的逻辑， 目前没有用到
	 * @date Created in  2020-07-02  15:25.
	 **/
	@Override
	public String getAnnotationAttr(Class clazz, Method method) {
		return "[DYNAMIC]";
	}
}
