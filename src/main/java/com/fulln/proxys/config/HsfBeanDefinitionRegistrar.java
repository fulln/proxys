package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponent;
import com.fulln.proxys.annotation.DataSourceComponentScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
public class HsfBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	private static final HashMap UNDERLYING_MAPPING = new HashMap();

	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";


	@Override
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
		//拿到主类上的自定义注解的属性
		AnnotationAttributes annAttr = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DataSourceComponentScan.class.getName()));

		String[] basePackages = annAttr.getStringArray("value");

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = annAttr.getStringArray("basePackages");
		}

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = getPackagesFromClasses(annAttr.getClassArray("basePackageClasses"));
		}

		if (ObjectUtils.isEmpty(basePackages)) {
			basePackages = new String[]{ClassUtils.getPackageName(annotationMetadata.getClassName())};
		}

		List<TypeFilter> includeFilters = extractTypeFilters(annAttr.getAnnotationArray("includeFilters"));

		//增加一个包含的过滤器,扫描到的类只要不是抽象的,接口,枚举,注解,及匿名类那么就算是符合的
		includeFilters.add(new HsfTypeFilter());

		List<TypeFilter> excludeFilters = extractTypeFilters(annAttr.getAnnotationArray("excludeFilters"));


		List<Class<?>> candidates = scanPackages( includeFilters, excludeFilters,basePackages);

		if (candidates.isEmpty()) {
			log.info("扫描指定包[{}]时未发现复合条件的类", basePackages.toString());
			return;
		}
		//注册处理器后,为 对象注入环境配置信息
		//通过该类对对象进行进一步操作
		//registerHsfBeanPostProcessor(registry);
		//注册
		registerBeanDefinitions(candidates, registry);

	}

	private Set<String> getPackagesToScan(AnnotationMetadata metadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EntityScan.class.getName()));
		String[] basePackages = attributes.getStringArray("basePackages");
		Class<?>[] basePackageClasses = attributes.getClassArray("basePackageClasses");
		Set<String> packagesToScan = new LinkedHashSet();
		packagesToScan.addAll(Arrays.asList(basePackages));
		Class[] var6 = basePackageClasses;
		int var7 = basePackageClasses.length;

		for(int var8 = 0; var8 < var7; ++var8) {
			Class<?> basePackageClass = var6[var8];
			packagesToScan.add(ClassUtils.getPackageName(basePackageClass));
		}

		if (packagesToScan.isEmpty()) {
			String packageName = ClassUtils.getPackageName(metadata.getClassName());
			Assert.state(!StringUtils.isEmpty(packageName), "@EntityScan cannot be used with the default package");
			return Collections.singleton(packageName);
		} else {
			return packagesToScan;
		}
	}

	private List<Class<?>> scanPackages( List<TypeFilter> includeFilters, List<TypeFilter> excludeFilters,String... basePackages) {

//		org.springframework.hibernate5.LocalSessionFactoryBuilder.scanPackages();

//		Set<Class<?>> set = new LinkedHashSet<>();
//			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//					resolveBasePackage(basePackages[0]) + '/' + DEFAULT_RESOURCE_PATTERN;
//			try {
//				Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
//				for (Resource resource : resources) {
//					if (resource.isReadable()) {
//						MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
//						String className = metadataReader.getClassMetadata().getClassName();
//						Class<?> clazz;
//						try {
//							clazz = Class.forName(className);
//							set.add(clazz);
//						} catch (ClassNotFoundException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return new ArrayList<>(set);
		return null;

	}

	protected String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
	}

	private Environment getEnvironment() {
//		return applicationContext.getEnvironment();
		return  null;
	}


	public void registerBeanDefinitions(List<Class<?>> internalClasses, BeanDefinitionRegistry registry){
		for (Class<?> clazz : internalClasses) {
			if (UNDERLYING_MAPPING.values().contains(clazz)) {
				log.debug("重复扫描{}类,忽略重复注册", clazz.getName());
				continue;
			}
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();

			definition.getPropertyValues().add("interfaceClass", clazz);

			Enum value = clazz.getAnnotation(DataSourceComponent.class).DataSource();

			definition.getPropertyValues().add("value",value);
			definition.setBeanClass(InterfaceFactoryBean.class);
			definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
			if (registerSpringBean(clazz)) {
				log.debug("注册[{}]Bean", clazz.getName());
				registry.registerBeanDefinition(ClassUtils.getShortNameAsProperty(clazz), definition);
			}
			UNDERLYING_MAPPING.put(ClassUtils.getShortNameAsProperty(clazz), clazz);
		}
	}

	private boolean registerSpringBean(Class<?> clazz) {
		return false;
	}

	;

	private List<TypeFilter> extractTypeFilters(AnnotationAttributes[] excludeFilters) {
		return null;
	}

	private String[] getPackagesFromClasses(Class<?>[] basePackageClasses) {
		return null;


	}

}
