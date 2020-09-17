package me.fulln.proxys.config;

import me.fulln.proxys.dto.DynamicSourceSwitchProp;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fulln
 * @description 动态数据源切换，使用不同的名称去提示
 * @date  Created in  15:11  2020-04-25.
 */
public class DynamicDataSourceSwitch extends AbstractRoutingDataSource {

    private DynamicSourceSwitchProp prop;
    /**
     *用于存储已实例的数据源map
     */
    private static Map<Object,Object> dataSourceMap=new HashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return prop.getDataSourceKey();
    }

    /**
     * 设置数据源
     * @param targetDataSources
     */
    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        dataSourceMap.putAll(targetDataSources);
        super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
    }

    public DynamicSourceSwitchProp getProp() {
        return prop;
    }

    public void setProp(DynamicSourceSwitchProp prop) {
        this.prop = prop;
    }
}