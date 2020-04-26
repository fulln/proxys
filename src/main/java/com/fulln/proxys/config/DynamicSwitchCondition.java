package com.fulln.proxys.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author fulln
 * @description condition   看原启动类上面有没有添加对应的启动注解，还检查下当前的配置文件的路径是不是正常的。
 * @date  Created in  23:05  2019-09-17.
 */
public class DynamicSwitchCondition implements Condition
{
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		return true;
	}
}
