package com.fulln.proxys.config;

import com.fulln.proxys.annotation.DataSourceComponentScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.core.io.support.ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

@Slf4j
public class HsfBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	private static final HashMap UNDERLYING_MAPPING = new HashMap();

	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";


	/**
	 *
	 * @param annotationMetadata 当前类的注解信息
	 * @param registry bean定义的注册类
	 *            吧所有需要添加到容器中的bean，调用下面的方法。
	 */
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
		registerMsgSrvBeanPostProcessor(registry);
		//注册
		registerBeanDefinitions(candidates, registry);

	}

	/**
	 * 注册MsgSrv后处理器
	 *
	 * @param registry
	 */
	private void registerMsgSrvBeanPostProcessor(BeanDefinitionRegistry registry) {
		String beanName = ClassUtils.getShortNameAsProperty(DataSourceComponentScan.class);
		if (!registry.containsBeanDefinition(beanName)) {
			registry.registerBeanDefinition(beanName, new RootBeanDefinition(DataSourceComponentScan.class));
		}
	}


	private List<Class<?>> scanPackages( List<TypeFilter> includeFilters, List<TypeFilter> excludeFilters,String... basePackages) {
		List<Class<?>> candidates = new ArrayList<Class<?>>();
		for (String pkg : basePackages) {
			try {
				candidates.addAll(findCandidateClasses(pkg, includeFilters, excludeFilters));
			} catch (IOException e) {
				log.error("扫描指定MsgSrv基础包[{}]时出现异常", pkg);
				continue;
			}
		}
		return candidates;
	}

	/**
	 * 获取符合要求的名称
	 *
	 * @param basePackage
	 * @return
	 * @throws IOException
	 */
	private List<Class<?>> findCandidateClasses(String basePackage, List<TypeFilter> includeFilters, List<TypeFilter> excludeFilters) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("开始扫描指定包{}下的所有类" + basePackage);
		}
		List<Class<?>> candidates = new ArrayList<Class<?>>();
		String packageSearchPath = CLASSPATH_ALL_URL_PREFIX + replaceDotByDelimiter(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		MetadataReaderFactory readerFactory = new SimpleMetadataReaderFactory(resourceLoader);
		Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(packageSearchPath);
		for (Resource resource : resources) {
			MetadataReader reader = readerFactory.getMetadataReader(resource);
			if (isCandidateResource(reader, readerFactory, includeFilters, excludeFilters)) {
				Class<?> candidateClass = transform(reader.getClassMetadata().getClassName());
				if (candidateClass != null) {
					candidates.add(candidateClass);
					log.debug("扫描到符合要求的基础类:{}" + candidateClass.getName());
				}
			}
		}
		return candidates;
	}

	/**
	 * @param className
	 * @return
	 */
	private Class<?> transform(String className) {
		Class<?> clazz = null;
		try {
			clazz = ClassUtils.forName(className, this.getClass().getClassLoader());
		} catch (ClassNotFoundException e) {
			log.info("未找到指定基础类{%s}", className);
		}
		return clazz;
	}

	/**
	 *  去除和包含对应的包url
	 * @param reader
	 * @param readerFactory
	 * @param includeFilters
	 * @param excludeFilters
	 * @return
	 * @throws IOException
	 */
	protected boolean isCandidateResource(MetadataReader reader, MetadataReaderFactory readerFactory, List<TypeFilter> includeFilters,
	                                      List<TypeFilter> excludeFilters) throws IOException {
		for (TypeFilter tf : excludeFilters) {
			if (tf.match(reader, readerFactory)) {
				return false;
			}
		}
		for (TypeFilter tf : includeFilters) {
			if (tf.match(reader, readerFactory)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用"/"替换包路径中"."
	 *
	 * @param path
	 * @return
	 */
	private String replaceDotByDelimiter(String path) {
		return StringUtils.replace(path, ".", "/");
	}


//	protected String resolveBasePackage(String basePackage) {
//		return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
//	}
//
//	private Environment getEnvironment() {
//		return applicationContext.getEnvironment();
//	}




	public void registerBeanDefinitions(List<Class<?>> internalClasses, BeanDefinitionRegistry registry){
		for (Class<?> clazz : internalClasses) {
			if (UNDERLYING_MAPPING.values().contains(clazz)) {
				log.debug("重复扫描{}类,忽略重复注册", clazz.getName());
				continue;
			}
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();

			definition.getPropertyValues().add("interfaceClass", clazz);

//			Enum value = clazz.getAnnotation(DataSourceComponent.class).DataSource();

//			definition.getPropertyValues().add("value",value);
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
		List<TypeFilter> typeFilters = new ArrayList<>();
		for (AnnotationAttributes filter : excludeFilters) {
			typeFilters.addAll(typeFiltersFor(filter));
		}
		return typeFilters;

	}

	/**
	 * @param filterAttributes
	 * @return
	 */
	private List<TypeFilter> typeFiltersFor(AnnotationAttributes filterAttributes) {
		List<TypeFilter> typeFilters = new ArrayList<TypeFilter>();
		FilterType filterType = filterAttributes.getEnum("type");

		for (Class<?> filterClass : filterAttributes.getClassArray("value")) {
			switch (filterType) {
				case ANNOTATION:
					Assert.isAssignable(Annotation.class, filterClass,
							"@DataSourceComponentScan 注解类型的Filter必须指定一个注解");
					Class<Annotation> annotationType = (Class<Annotation>)filterClass;
					typeFilters.add(new AnnotationTypeFilter(annotationType));
					break;
				case ASSIGNABLE_TYPE:
					typeFilters.add(new AssignableTypeFilter(filterClass));
					break;
				case CUSTOM:
					Assert.isAssignable(TypeFilter.class, filterClass,
							"@DataSourceComponentScan 自定义Filter必须实现TypeFilter接口");
					TypeFilter filter = BeanUtils.instantiateClass(filterClass, TypeFilter.class);
					typeFilters.add(filter);
					break;
				default:
					throw new IllegalArgumentException("当前TypeFilter不支持: " + filterType);
			}
		}
		return typeFilters;
	}

	private String[] getPackagesFromClasses(Class<?>[] basePackageClasses) {
		if (ObjectUtils.isEmpty(basePackageClasses)) {
			return null;
		}
		List<String> basePackages = new ArrayList<>(basePackageClasses.length);
		for (Class<?> clazz : basePackageClasses) {
			basePackages.add(ClassUtils.getPackageName(clazz));
		}
		return (String[])basePackages.toArray();


	}

}
