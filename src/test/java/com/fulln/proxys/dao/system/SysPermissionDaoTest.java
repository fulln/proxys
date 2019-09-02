package com.fulln.proxys.dao.system;

import cn.hutool.core.collection.CollectionUtil;
import com.fulln.proxys.model.SysPermission;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class SysPermissionDaoTest {

	@Resource
	private Sys1PermissionDao sysPermissionDao;

	@Test
	public void queryAll() {
		List<SysPermission> sysPermissions = sysPermissionDao.queryAll();
		System.out.println(CollectionUtil.join(sysPermissions,","));
	}
}