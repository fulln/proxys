package com.fulln.proxys.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author fulln
 * @description 动态数据源切换，使用不同的名称去提示
 * @date  Created in  15:11  2020-04-25.
 */
public class DynamicDataSourceSwitch extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
//        return DynamicDataSourceHolder.getDataSourceKey();
        return "data1";
    }



}