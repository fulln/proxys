package com.fulln.proxys.dto;

import lombok.Data;

import java.util.List;

/**
 * @author fulln
 * @description  自定义一个class
 * @date  Created in  17:03  2020-04-25.
 */
@Data
public class DynamicSourceSwitchDto {


	private static ThreadLocal<String> local = new ThreadLocal<>();


	private String applicationUrl;


	private List<String> databaseName;

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
