package com.fulln.proxys.life.config;

import com.fulln.proxys.life.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 *      spring 利用依赖注入 完成赋值
 *      1. autowired  自动注入
 *        1。 默认优先按照类型获取
 *        2。 找到多个，再按照属性名字查询
 *        3. Qualifier
 *        4. 自动装配的时候找不到直接报错
 *        5. primary 进行自动装配的时候使用首选的bean
 *            也可以使用 Qualifier
 *      2. spring 还支持使用resource 和 inject [java规范]
 *        resource 首先使用名称查询，没有能支持 5和  require=false
 *        inject 需要导入包，和autowired一样支持 5  不支持 require = false
 *      3. autowired  构造器，
 *        1。 方法上 @bean +参数  可以不用写autowired  也是自动装配
 *        2。构造器上 如果只有一个有can构造器， 这个上的autowired 可以省略
 *        3。 参数
 *      4. 自定义注解想要实现spring底层的组件
 *        自定义实现aware 创建对象的时候，调用接口对饮的aware
 *      5。 profile
 */
@ComponentScan({"com.fulln.proxys.life.controller","com.fulln.proxys.life.service","com.fulln.proxys.life.dao"})
@Configuration
public class mainConfigAutowired {

	/**
	 * 可以有参数的时候 ，省略autowired
	 * @return
	 */
	@Bean("bookDao2")
	public BookDao bookDao(){
		BookDao bookDao = new BookDao();
		bookDao.setLable("2");
		return bookDao;
	}

}
