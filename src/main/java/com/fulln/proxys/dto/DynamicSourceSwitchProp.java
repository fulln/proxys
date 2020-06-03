package com.fulln.proxys.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author fulln
 * @description  自定义一个class 用于传递参数
 * @date  Created in  17:03  2020-04-25.
 */
@Data
public class DynamicSourceSwitchProp {


	private ThreadLocal<String> local = new ThreadLocal<>();

	private String applicationUrl;

	private String defaultDatasourceName;

	private Set<String> databaseName;

	/**
	 * 设置数据源key
	 *
	 * @param key
	 */
	public void putDataSourceKey(String key) {
		if(StringUtils.isEmpty(key)){
			local.set(defaultDatasourceName);
			return;
		}
		if (databaseName.contains(key)) {
			local.set(key);
			return;
		}
		throw new RuntimeException("未能找到对应数据");
	}
	/**
	 * 获取数据源key
	 *
	 * @return
	 */
	public String getDataSourceKey() {
		return local.get();
	}

	/**
	 * 清空数据源
	 */
	public void clear() {
		local.remove();
	}
}
