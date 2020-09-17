package me.fulln.proxys.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
/**
 * @author fulln
 * @description 使用注解的方式提示数据源的切换
 * @date  Created in  23:12  2019-09-03.
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceComponent {
	/**
	 * 数据源
	 *
	 * @return
	 */
	@AliasFor("value")
	String DataSource() default "";

	@AliasFor("DataSource")
	String value() default "";
}
