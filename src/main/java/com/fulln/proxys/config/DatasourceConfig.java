package com.fulln.proxys.config;

import com.fulln.proxys.dto.DynamicSourceSwitchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author fulln
 * @description 数据源动态创建
 * @date  Created in  14:36  2020-04-25.
 */
@Slf4j
@Conditional(DynamicSwitchCondition.class)
@Configuration
public class DatasourceConfig {

	@Resource
	private DynamicSourceSwitchDto dynamicSourceSwitchDto;

	public DatasourceConfig() {
		String applicationUrl = dynamicSourceSwitchDto.getApplicationUrl();
		log.info("datasource create ok! url is {}",applicationUrl);
	}

}
