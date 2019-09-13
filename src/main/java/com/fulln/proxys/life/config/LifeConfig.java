package com.fulln.proxys.life.config;

import com.fulln.proxys.life.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fulln
 * @description :
 * bean 生命周期
 *   init 和  destroy
 *
 * 1。 指定@Bean initmethod
 * 2.  实现 initializingBean 和  disposableBean 指定bean的启动方法和销毁方法
 * 3.  使用JSR250
 *   @PostConstruct:
 *   @PreDestroy:
 * 4. BeanPostProcessor 接口
 *   初始化前后的方法实现
 *      在  initializeBean 中的执行顺序， 遍历得到容器中所有的beanPorcessor  然后  逐步执行
 *           if (mbd == null || !mbd.isSynthetic()) {
 *               wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
 *           }
 *
 *           try {
 *               this.invokeInitMethods(beanName, wrappedBean, mbd);
 *  *         } catch (Throwable var6) {
 *  *             throw new BeanCreationException(mbd != null ? mbd.getResourceDescription() : null, beanName, "Invocation of init method failed", var6);
 *  *         }
 *  *
 *  *         if (mbd == null || !mbd.isSynthetic()) {
 *  *             wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *  *         }
 *
 *
 * @date  Created in  21:22  2019-09-13.
 */
@ComponentScan("com.fulln.proxys.life.entity")
@Configuration
public class LifeConfig {

	@Bean(initMethod = "init",destroyMethod = "destroy")
	public Car car(){
		return new Car();
	}


}
