package com.fulln.proxys.ex;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExconfigTest {

	@Test
	public void car() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Exconfig.class);
		annotationConfigApplicationContext.publishEvent(new ApplicationEvent("发布了一个事件") {
			@Override
			public Object getSource() {
				return super.getSource();
			}
		});

		annotationConfigApplicationContext.close();

	}
}