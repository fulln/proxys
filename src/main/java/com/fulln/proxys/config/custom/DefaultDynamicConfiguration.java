package com.fulln.proxys.config.custom;

import com.fulln.proxys.aop.CustomInterceptor;
import com.fulln.proxys.aop.CustomPointCut;
import com.fulln.proxys.aop.CustomPointcutAdvisor;
import com.fulln.proxys.aop.ICustomPointCut;
import com.fulln.proxys.dto.CustomAnnotationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.fulln.proxys.constant.DynamicSourceConstant.APPLICATION_URL;
import static com.fulln.proxys.constant.DynamicSourceConstant.DEFAULT_DATASOURCE_NAME;

/**
 * @author fulln
 * @description 默认的注解配置文件
 * @date Created in  15:18  2020-06-29.
 */
@Configuration
public class DefaultDynamicConfiguration extends AbstractDynamicConfiguration {

	@Autowired
	private CustomAnnotationProperties properties;
	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description Advisor 用来封装切面的所有信息，主要是上面两个，它用来充当 Advice 和 Pointcut 的适配器。
	 * @date Created in  2020-06-30  17:44.
	 * it's used in this place，to get pointcut（）
	 * {@link org.springframework.aop.support.AopUtils#canApply(org.springframework.aop.Advisor, java.lang.Class, boolean)}
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CustomPointcutAdvisor cpAdvisor() {
		CustomPointcutAdvisor advisor = new CustomPointcutAdvisor();
		advisor.setInterceptor(customInterceptor());
		advisor.setCustomPointCut(customPointCut());
		advisor.setOrder(enableDy.<Integer>getNumber("order"));
		return advisor;
	}

	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description Pointcut 定义一个切点，可以在这个被拦截的方法前后进行切面逻辑  ,这地方可以将需要参数带到
	 * @date Created in
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public ICustomPointCut customPointCut() {
		return new CustomPointCut();
	}

	/**
	 * @author fulln
	 * @description Advice 用来定义拦截行为，在这里实现增强的逻辑
	 * @date Created in  2020-07-02  16:06.
	 * @param
	 * @return
	 **/
	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public CustomInterceptor customInterceptor() {
		CustomInterceptor customInterceptor = new CustomInterceptor();
		if (checkProperties(properties)) {
			CustomAnnotationProperties customAnnotationProperties = new CustomAnnotationProperties();
			customAnnotationProperties.setApplicationUrl(this.enableDy.getString(APPLICATION_URL));
			customAnnotationProperties.setDefaultDatasourceName(this.enableDy.getString(DEFAULT_DATASOURCE_NAME));
			customInterceptor.setProperties(customAnnotationProperties);
		} else {
			customInterceptor.setProperties(properties);
		}
		return customInterceptor;
	}

	private boolean checkProperties(CustomAnnotationProperties properties) {
		return Objects.isNull(properties) || StringUtils.isEmpty(properties.getApplicationUrl())
				|| StringUtils.isEmpty(properties.getDefaultDatasourceName()) ||
				StringUtils.isEmpty(properties.getDefaultPackageName());
	}

}
