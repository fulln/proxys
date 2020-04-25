package com.fulln.proxys.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author fulln
 * @description
 * @date  Created in  23:05  2019-09-17.
 */
public class DynamicSwitchCondition implements Condition
{
	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
//		MultiValueMap<String, Object> allAnnotationAttributes = annotatedTypeMetadata.getAllAnnotationAttributes(EnableDynamicSource.class.getName());
//
//
//		return annotatedTypeMetadata.isAnnotated(EnableDynamicSource.class.getName());
		return true;
	}
}
