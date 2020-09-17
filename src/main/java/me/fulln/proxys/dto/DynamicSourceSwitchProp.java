package me.fulln.proxys.dto;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author fulln
 * @description  自定义一个class 用于传递参数
 * @date  Created in  17:03  2020-04-25.
 */
@Data
public class DynamicSourceSwitchProp {


	private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

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
			key = defaultDatasourceName;
		}

		if (LOCAL.get() != null && LOCAL.get().equals(key)) {
			return;
		}

		if (databaseName.contains(key)) {
			LOCAL.set(key);
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
		String s = LOCAL.get();
		if (StringUtils.isEmpty(s)) {
			LOCAL.set(defaultDatasourceName);
		}
		return LOCAL.get();
	}

	/**
	 * 清空数据源
	 */
	public void clear() {
		LOCAL.remove();
	}


	protected final class SwitchPropInfo {

		@Nullable
		private final String databaseName;

		public SwitchPropInfo(String databaseName) {
			this.databaseName = databaseName;
		}
	}


}
