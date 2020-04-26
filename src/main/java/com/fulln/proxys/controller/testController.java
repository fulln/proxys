package com.fulln.proxys.controller;

import com.fulln.proxys.dao.basic.SysPermissionDao;
import com.fulln.proxys.model.SysPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/")
public class testController {
	@Resource
	private SysPermissionDao sysPermissionDao;

	@GetMapping("index")
	public String get(){
		List<SysPermission> byRole = sysPermissionDao.findByRole(1);
		return "1";
	}
}
