package me.fulln.proxys.config.custom;

import lombok.extern.slf4j.Slf4j;
import me.fulln.proxys.annotation.EnableDynamicSource;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * @author fulln
 * @description
 * @date Created in  15:02  2020-06-29.
 */
@Slf4j
public class AbstractDynamicConfiguration implements ImportAware {

	@Nullable
	protected AnnotationAttributes enableDy;

	@Override
	public void setImportMetadata(AnnotationMetadata importMetadata) {
		//拿到类上的自定义注解的属性
		this.enableDy = AnnotationAttributes.fromMap(
				importMetadata.getAnnotationAttributes(EnableDynamicSource.class.getName(), false));

		if (this.enableDy == null) {
			throw new IllegalArgumentException(
					"@EnableDynamicSource is not present on importing class " + importMetadata.getClassName());
		}

	}

}
