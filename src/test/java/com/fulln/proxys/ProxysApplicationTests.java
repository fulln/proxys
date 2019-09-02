package com.fulln.proxys;

import cn.hutool.core.collection.CollectionUtil;
import com.fulln.proxys.dao.basic.SysPermissionDao;
import com.fulln.proxys.dao.system.Sys1PermissionDao;
import com.fulln.proxys.model.SysPermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxysApplicationTests {

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
