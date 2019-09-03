package com.fulln.proxys.config;

import cn.hutool.core.io.FileUtil;
import com.fulln.proxys.annotation.DataSourceComponentScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.util.List;

public class HsfBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
		//拿到主类上的自定义注解的属性
		AnnotationAttributes annAttr = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DataSourceComponentScan.class.getName()));

		String[] basePackages = annAttr.getStringArray("value");

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = annAttr.getStringArray("basePackages");
		}

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = getPackagesFromClasses(annAttr.getClassArray("basePackageClasses"));
		}

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = new String[]{ClassUtils.getPackageName(annotationMetadata.getClassName())};
		}

		List<TypeFilter> includeFilters = extractTypeFilters(annAttr.getAnnotationArray("includeFilters"));

		//增加一个包含的过滤器,扫描到的类只要不是抽象的,接口,枚举,注解,及匿名类那么就算是符合的
		includeFilters.add(new HsfTypeFilter());

		List<TypeFilter> excludeFilters = extractTypeFilters(annAttr.getAnnotationArray("excludeFilters"));

		List<Class<?>> candidates = scanPackages(basePackages, includeFilters, excludeFilters);

		if (candidates.isEmpty()) {
			log.info("扫描指定包[{}]时未发现复合条件的类", basePackages.toString());
			return;
		}
		//注册处理器后,为 对象注入环境配置信息
		//通过该类对对象进行进一步操作
		//registerHsfBeanPostProcessor(registry);
		//注册
		registerBeanDefinitions(candidates, registry);

	}

	private String[] getPackagesFromClasses(Class<?>[] basePackageClasses) {
		return null;


	}
}
