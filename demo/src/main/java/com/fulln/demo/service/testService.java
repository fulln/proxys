package com.fulln.demo.service;

import com.fulln.demo.dao.basic.SysPermissionDao;
import com.fulln.demo.dao.system.Sys1PermissionDao;
import com.fulln.demo.model.SysPermission;
import com.fulln.proxys.annotation.DataSourceComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 可以通过写入缓存的方法避免重复的创建
 */

@Service
public class testService {

	@Resource
	private SysPermissionDao sysPermissionDao;

	@Resource
	private Sys1PermissionDao sys1PermissionDao;

	@DataSourceComponent
	public void test01(){
		List<SysPermission> sysPermissions = sys1PermissionDao.queryAll();
	}

	@DataSourceComponent("data2")
	public void test02(){
		List<SysPermission> sysPermissions = sys1PermissionDao.queryAll();
	}
}
