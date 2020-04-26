package com.fulln.proxys.dto;

import lombok.Data;

import java.util.Set;

/**
 * @author fulln
 * @description  自定义一个class 用于传递参数
 * @date  Created in  17:03  2020-04-25.
 */
@Data
public class DynamicSourceSwitchProp {


	private static ThreadLocal<String> local = new ThreadLocal<>();


	private String applicationUrl;

	private String defaultDatasourceName;

	private Set<String> databaseName;

	/**
	 * 设置数据源key
	 *
	 * @param key
	 */
	public static void putDataSourceKey(String key) {
		local.set(key);
	}

	/**
	 * 获取数据源key
	 *
	 * @return
	 */
	public static String getDataSourceKey() {
		return local.get();
	}

	/**
	 * 清空数据源
	 */
	public static void clear() {
		local.remove();
	}
}
