package com.fulln.proxys.annotation;

import com.fulln.proxys.config.DynamicSwitchConfig;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author fulln
 * @description  数据源动态切换的标志
 *   注册到后置处理器中，通过每一个
 * @date  Created in  15:35  2020-04-23.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DynamicSwitchConfig.class)
public @interface EnableDynamicSource {
	/**
	 * 配置文件默认扫描的路径
	 * @return
	 */
	@AliasFor("value")
	String ApplicationUrl() default  "spring.datasource";

	@AliasFor("ApplicationUrl")
	String value() default  "spring.datasource";

	/**
	 * 默认连接的数据源
	 * @return
	 */
	String defaultSourceName() default "";
}
