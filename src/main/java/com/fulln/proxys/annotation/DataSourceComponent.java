package com.fulln.proxys.annotation;

import com.fulln.proxys.enums.DatasourceEnum;

import java.lang.annotation.*;
/**
 * @author fulln
 * @description 枚举
 * @date  Created in  23:12  2019-09-03.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceComponent {
	/**
	 * 数据源
	 *
	 * @return
	 */
	DatasourceEnum DataSource() default DatasourceEnum.DB1;

	/**
	 * 是否要将标识此注解的类注册为Spring的Bean
	 *
	 * @return
	 */
	boolean registerBean() default false;
}
