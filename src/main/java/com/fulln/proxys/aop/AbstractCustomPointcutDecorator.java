package com.fulln.proxys.aop;

import com.fulln.proxys.annotation.DataSourceComponent;
import com.fulln.proxys.config.DynamicSwitchConfig;
import com.fulln.proxys.constant.DynamicSourceConstant;
import com.fulln.proxys.dto.CustomAnnotationProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Slf4j
@Setter
public abstract class AbstractCustomPointcutDecorator extends StaticMethodMatcherPointcut {

	private ICustomPointCut customPointCut = getCustomPointCut();

	private CustomAnnotationProperties properties;

	/**
	 * 获取到注解相关信息
	 *
	 * @return
	 */
	public abstract ICustomPointCut getCustomPointCut();

	@Override
	public boolean matches(Method method, Class<?> aClass) {


		//跳过元数据
		if (method.getDeclaringClass() == Object.class) {
			return false;
		}

		//跳过非公开方法
		if (!Modifier.isPublic(method.getModifiers())) {
			return false;
		}
		// 跳过非用户定义的方法
		if (!ClassUtils.isUserLevelMethod(method)) {
			return false;
		}

		if (CustomPointcutAdvisor.class.isAssignableFrom(aClass) ||
				DynamicSwitchConfig.class.isAssignableFrom(aClass)) {
			return false;
		}

		if (!aClass.getName().startsWith(properties.getDefaultPackageName())) {
			return false;
		}


		if (AnnotatedElementUtils.hasAnnotation(aClass, DataSourceComponent.class)) {
			log.info(DynamicSourceConstant.LOG_HEAD + "get Annotation class [{}] and get Annotation method [{}]: ", aClass.getName(), method.getName());
			return true;
		}


		if (AnnotatedElementUtils.hasAnnotation(method, DataSourceComponent.class)) {
			log.info(DynamicSourceConstant.LOG_HEAD + "get Annotation class [{}] and get Annotation method [{}]: ", aClass.getName(), method.getName());
			return true;
		}

		return false;
	}
}
