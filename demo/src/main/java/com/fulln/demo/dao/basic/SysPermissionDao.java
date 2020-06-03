package com.fulln.demo.dao.basic;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fulln.demo.model.SysPermission;
import com.fulln.proxys.annotation.DataSourceComponent;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: api
 * @description: 系统权限
 * @author: fulln
 * @create: 2018-10-19 13:38
 * @Version： 0.0.1
 **/
@Repository
@DataSourceComponent("data2")
public interface SysPermissionDao extends BaseMapper<SysPermission> {

    /**
     * 根据角色查询权限
     * @param roleId
     * @return
     */
    List<SysPermission> findByRole(Integer roleId);




}
