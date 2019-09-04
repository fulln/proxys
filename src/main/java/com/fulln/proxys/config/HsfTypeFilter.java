package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponent;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

public class HsfTypeFilter extends AbstractClassTestingTypeFilter implements TypeFilter {

	@Override
	protected boolean match(ClassMetadata classMetadata) {


		Class<?> clazz = classMetadata.getClass();
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

	private boolean isAnnotatedBySpring(Class<?> clazz) {
		return false;
	}

}

