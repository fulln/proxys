package com.fulln.proxys.aop;

import com.fulln.proxys.annotation.DataSourceComponent;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import static com.fulln.proxys.constant.DynamicSourceConstant.LOG_HEAD;

/**
 * @author fulln
 * @description 自定义拦截
 * @date Created in  17:46  2020-06-01.
 */
@Slf4j
public class CustomInterceptor extends CustomInterceptorSupport implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

		//开始执行相关操作
		//1. 获取注解
		DataSourceComponent annotation = AnnotationUtils.findAnnotation(invocation.getMethod(), DataSourceComponent.class);

		if (annotation != null) {
			//获取注解上的数据源的值的信息
			String dataSourceKey = annotation.DataSource();
			//从bean中获取到之前设置的
			if (prop == null) {
				throw new RuntimeException("当前获取到的数据源配置异常!");
			}
			log.info(LOG_HEAD.concat("start change current datasource to [{}]"), StringUtils.isEmpty(dataSourceKey) ? prop.getDefaultDatasourceName() : dataSourceKey);
			//给当前的执行SQL的操作设置特殊的数据源的信息
			prop.putDataSourceKey(dataSourceKey);
		}

		Object proceed = invocation.proceed();

		if (prop != null) {
			prop.clear();
		}

		return proceed;

	}
}
