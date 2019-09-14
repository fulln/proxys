package com.fulln.proxys.life.config;

import org.springframework.context.annotation.Configuration;

/**
 *  spring  可以为提供当前环境，动态的切换一系列功能
 *  proflie 指定那个环境下的情况才能注册到容器中，
 *   1。 加了环境标示的bean  只有这个环境才能激活，  默认是defalut环境
 *   2。 写在配置类上，所有配置的才能生效
 *   3。 没标示的都会被加载
 */
@Configuration
public class MainConfigOfProfile {




}
