package me.fulln.proxys.annotation;

import me.fulln.proxys.config.custom.CustomDynamicSwitchConfig;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
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
@Import(CustomDynamicSwitchConfig.class)
public @interface EnableDynamicSource {

	//proxyTargetClass = false表示是JDK动态代理支持接口代理。true表示是Cglib代理支持子类继承代理。
	boolean proxyTargetClass() default false;

	//事务通知模式(切面织入方式)，默认代理模式（同一个类中方法互相调用拦截器不会生效），可以选择增强型AspectJ
	//可以不写 目前只支持代理模式  但是方便扩展还是先写在这里
	AdviceMode mode() default AdviceMode.PROXY;

	/**
	 * 设置当前代理的顺序
	 */
	int order() default Ordered.LOWEST_PRECEDENCE;

	/**
	 * 配置文件默认扫描的路径
	 */
	@AliasFor("value")
	String applicationUrl() default "spring.datasource";

	@AliasFor("applicationUrl")
	String value() default  "spring.datasource";

	/**
	 * 默认连接的数据源
	 */
	String defaultDatasourceName() default "";
}
