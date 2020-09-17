package me.fulln.proxys.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fulln
 * @description 自定义的相关注解配置类
 * @date Created in  10:00  2020-06-24.
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "me.fulln")
public class CustomAnnotationProperties {

	private String defaultPackageName;
	private String applicationUrl;
	private String defaultDatasourceName;

}
