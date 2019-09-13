package com.fulln.proxys.life.entity;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class cat implements InitializingBean, DisposableBean
{
	@Override
	public void destroy() throws Exception {
		System.out.println("cat  destroy");
	}

	public cat() {
		System.out.println("cat construct");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println( "cat 在properties 设置之后");
	}
}
