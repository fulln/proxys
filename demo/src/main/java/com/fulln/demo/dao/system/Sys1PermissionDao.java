package com.fulln.demo.dao.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fulln.demo.model.SysPermission;
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
public interface Sys1PermissionDao extends BaseMapper<SysPermission> {


	/**
	 * @Author: fulln
	 * @Description: 查询全部的权限
	 * @retun: List
	 * @Date: 2018/10/23 0023-14:56
	 */
	List<SysPermission> queryAll();


}
