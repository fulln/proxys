package com.fulln.proxys.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insert(){
		String sql  = "insert into `sys_role_permission` (role_id,permission_id) values(?,?) ";
		int id = 3;
		int code = 4;

		jdbcTemplate.update(sql,id,code);


	}

}
