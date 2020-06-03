package com.fulln.proxys.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author fulln
 * @description condition   看原启动类上面有没有添加对应的启动注解，还检查下当前的配置文件的路径是不是正常的。
 * @date Created in  23:05  2019-09-17.
 */
@Slf4j
public class DynamicSwitchCondition implements Condition {
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {

//		ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
//		String beanName = ClassUtils.getShortNameAsProperty(DynamicSwitchConfig.class);
//		try {
//			if (!beanFactory.containsBean(beanName)) {
//				return false;
//			}
//		} catch (Exception e) {
//			log.error("未能检测到注解的配置类,将不进行注册datasource行为", e);
//			return false;
//		}
		return true;
	}
}
