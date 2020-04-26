package com.fulln.proxys.enums;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * @author fulln
 * @description 数据源类型的枚举；
 * @date  Created in  13:40  2020-04-26.
 */

@AllArgsConstructor
public enum  DatasourceTypeEnum {
	/**
	 *
	 */
	DRUID(1,"druid"),
	HIKARI(2,"hikari");

	private int code;
	private String type;

	public static String getTypeByCode(int code) {
		DatasourceTypeEnum datasourceTypeEnum = Stream.of(values()).filter(em -> em.code == code).findAny().orElse(DRUID);
		return datasourceTypeEnum.type;
	}

//	abstract public DataSource createDataSource();
}
