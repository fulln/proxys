package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponent;
import com.fulln.proxys.dto.DynamicSourceSwitchProp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
public class ScanDynamicResource {

	@Resource
	private DynamicSourceSwitchProp prop;

	@Pointcut("@annotation(com.fulln.proxys.annotation.DataSourceComponent)")
	private void scan() {}

	@Before("scan()")
	private void ChangeDatasource(JoinPoint joinPoint) {
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

		DataSourceComponent annotationClass = AnnotationUtils.findAnnotation(method,
				DataSourceComponent.class);
		if (annotationClass == null) {
			annotationClass = joinPoint.getTarget().getClass().getAnnotation(DataSourceComponent.class);
			if (annotationClass == null) {
				return;
			}
		}

		//获取注解上的数据源的值的信息
		String dataSourceKey = annotationClass.DataSource();
		//给当前的执行SQL的操作设置特殊的数据源的信息
		prop.putDataSourceKey(dataSourceKey);
	}

	@After("scan()")
	private void revertDatasource(JoinPoint joinPoint) {
		prop.clear();
	}
}