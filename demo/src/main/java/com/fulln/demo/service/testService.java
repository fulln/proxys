package com.fulln.demo.service;

import com.fulln.demo.dao.basic.SysPermissionDao;
import com.fulln.demo.dao.system.Sys1PermissionDao;
import com.fulln.demo.model.SysPermission;
import com.fulln.proxys.annotation.DataSourceComponent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
@DataSourceComponent
public class testService {

	@Resource
	private SysPermissionDao sysPermissionDao;

	@Resource
	private Sys1PermissionDao sys1PermissionDao;

	public void test01(){
		List<SysPermission> sysPermissions = sys1PermissionDao.queryAll();
	}


	public void test02(){
		List<SysPermission> sysPermissions = sys1PermissionDao.queryAll();
	}
}
