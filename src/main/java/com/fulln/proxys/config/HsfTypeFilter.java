package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

@Slf4j
public class HsfTypeFilter extends AbstractClassTestingTypeFilter{

	@Override
	protected boolean match(ClassMetadata classMetadata) {


		Class<?> clazz = transformToClass(classMetadata.getClassName());
		if (clazz == null || !clazz.isAnnotationPresent(DataSourceComponent.class)) {
			return false;
		}
		DataSourceComponent hsfComponent = clazz.getAnnotation(DataSourceComponent.class);
		if (hsfComponent.registerBean() && isAnnotatedBySpring(clazz)) {
			throw new IllegalStateException("类{" + clazz.getName() + "}已经标识了Spring组件注解,不能再指定[registerBean = true]");
		}
		//过滤抽象类,接口,注解,枚举,内部类及匿名类
		return !classMetadata.isAbstract() && !clazz.isInterface() && !clazz.isAnnotation() && !clazz.isEnum()
				&& !clazz.isMemberClass() && !clazz.getName().contains("$");
	}


	/**
	 * @param className
	 * @return
	 */
	private Class<?> transformToClass(String className) {
		Class<?> clazz = null;
		try {
			clazz = ClassUtils.forName(className, this.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			log.info("未找到指定HSF基础类{}", className);
		}
		return clazz;
	}
	/**
	 * @param clazz
	 * @return
	 */
	private boolean isAnnotatedBySpring(Class<?> clazz) {
		return clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Configuration.class)
				|| clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Repository.class)
				|| clazz.isAnnotationPresent(Controller.class);
	}
}

