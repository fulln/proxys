package com.fulln.proxys.life.dao;

import lombok.ToString;
import org.springframework.stereotype.Repository;

@ToString
@Repository
public class BookDao {


	public  String lable = "1";


	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}
}
