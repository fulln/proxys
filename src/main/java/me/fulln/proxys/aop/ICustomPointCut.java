package me.fulln.proxys.aop;

import java.lang.reflect.Method;

public interface ICustomPointCut {
	/**
	 * @param clazz  注解的class
	 * @param method 获取注解的地方
	 * @return String的值
	 * @author fulln
	 * @description 从注解上获取对应的值，
	 * @date Created in  2020-07-02  15:25.
	 **/
	String getAnnotationAttr(Class clazz, Method method);

}
