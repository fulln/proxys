package com.fulln.proxys.ex;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DefaultService {

	@EventListener(classes = {ApplicationEvent.class})
	public void listen(){
		System.out.println( "一般bean能监听事件");
	}

}
