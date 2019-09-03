package com.fulln.proxys.dao.system;

import cn.hutool.core.collection.CollectionUtil;
import com.fulln.proxys.ProxysApplicationTests;
import com.fulln.proxys.dao.basic.SysPermissionDao;
import com.fulln.proxys.model.SysPermission;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class SysPermissionDaoTest extends ProxysApplicationTests {

	@Resource
	private Sys1PermissionDao sys1PermissionDao;

	@Resource
	private SysPermissionDao sysPermissionDao;

	@Test
	public void queryAll() {
		List<SysPermission> sysPermissions = sys1PermissionDao.queryAll();
		List<SysPermission> byRole = sysPermissionDao.findByRole(1);
		System.out.println(CollectionUtil.join(sysPermissions,","));
		System.out.println(CollectionUtil.join(byRole,","));
	}
}