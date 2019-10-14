package com.fulln.proxys.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;


	@Transactional
	public void update(){
		userDao.insert();
		System.out.println("插入完成");
		throw new RuntimeException("回滚异常");
	}

}
