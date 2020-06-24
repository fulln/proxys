package com.fulln.proxys.dto;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fulln
 * @description 自定义的相关注解配置类
 * @date Created in  10:00  2020-06-24.
 */
@ConfigurationProperties(prefix = "me.fulln")
public class CustomAnnotationProperties {

	private String defaultName;

	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

}
