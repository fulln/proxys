package me.fulln.proxys.config.custom;

import lombok.extern.slf4j.Slf4j;
import me.fulln.proxys.annotation.EnableDynamicSource;
import me.fulln.proxys.constant.DynamicSourceConstant;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

/**
 * @author fulln
 * @description 自定义的注解bean注入, 这个类的目的在于可以选择你想要加载的配置类
 * 目前是只有使用代理模式。不会用到字节码增强模式（ASPECTJ 模式可以说是使用的ajc。其使用的曲线有点陡）
 * @date Created in  16:33  2020-06-24.
 */
@Slf4j
public class CustomDynamicSwitchConfig extends AdviceModeImportSelector<EnableDynamicSource> {


	@Override
	protected String[] selectImports(AdviceMode adviceMode) {
		if (adviceMode == AdviceMode.PROXY) {
			return new String[]{AutoProxyRegistrar.class.getName(),
					DefaultDynamicConfiguration.class.getName()};
		} else if (adviceMode == AdviceMode.ASPECTJ) {
			log.warn(DynamicSourceConstant.LOG_HEAD.concat("current adviceMode isn't support!"));
		}
		return null;
	}

}
