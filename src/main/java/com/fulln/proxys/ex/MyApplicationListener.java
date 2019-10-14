package com.fulln.proxys.ex;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author fulln
 * @description
 * @date  Created in  23:51  2019-10-11.
 */
@Component
public class MyApplicationListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {

		System.out.println("收到事件，"+event);

	}
}
