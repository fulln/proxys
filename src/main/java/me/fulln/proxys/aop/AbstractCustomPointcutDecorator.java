package me.fulln.proxys.aop;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.fulln.proxys.annotation.DataSourceComponent;
import me.fulln.proxys.constant.DynamicSourceConstant;
import me.fulln.proxys.dto.CustomAnnotationProperties;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.MethodClassKey;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Setter
public abstract class AbstractCustomPointcutDecorator extends StaticMethodMatcherPointcut {

	private ICustomPointCut customPointCut = getCustomPointCut();

	private CustomAnnotationProperties properties;

	private static final ICustomPointCut NULL_CUSTOM_POINTCUT = (clazz, method) -> null;

	/**
	 * 获取到注解相关信息
	 *
	 * @return
	 */
	public abstract ICustomPointCut getCustomPointCut();

	/**
	 * Cache of TransactionAttributes, keyed by method on a specific target class.
	 * <p>As this base class is not marked Serializable, the cache will be recreated
	 * after serialization - provided that the concrete subclass is Serializable.
	 */
	private final Map<Object, ICustomPointCut> attributeCache = new ConcurrentHashMap<>(1024);

	/**
	 * Determine a cache key for the given method and target class.
	 * <p>Must not produce same key for overloaded methods.
	 * Must produce same key for different instances of the same method.
	 *
	 * @param method      the method (never {@code null})
	 * @param targetClass the target class (may be {@code null})
	 * @return the cache key (never {@code null})
	 */
	protected Object getCacheKey(Method method, @Nullable Class<?> targetClass) {
		return new MethodClassKey(method, targetClass);
	}

	/**
	 * @param
	 * @return
	 * @author fulln
	 * @description 掉用match方法使用
	 * @date Created in  2020-07-06  15:12.
	 **/
	@Override
	public boolean matches(Method method, Class<?> aClass) {

		//跳过元数据
		if (method.getDeclaringClass() == Object.class) {
			return false;
		}
		// First, see if we have a cached value.
		Object cacheKey = getCacheKey(method, aClass);
		ICustomPointCut cached = this.attributeCache.get(cacheKey);
		if (cached != null){
			return cached != NULL_CUSTOM_POINTCUT;
		}

		//跳过非公开方法
		if (!Modifier.isPublic(method.getModifiers())) {
			this.attributeCache.put(cacheKey,NULL_CUSTOM_POINTCUT);
			return false;
		}
		// 跳过非用户定义的方法
		if (!ClassUtils.isUserLevelMethod(method)) {
			this.attributeCache.put(cacheKey,NULL_CUSTOM_POINTCUT);
			return false;
		}

		if (CustomPointcutAdvisor.class.isAssignableFrom(aClass)) {
			this.attributeCache.put(cacheKey,NULL_CUSTOM_POINTCUT);
			return false;
		}

		if (!aClass.getName().startsWith(properties.getDefaultPackageName())) {
			this.attributeCache.put(cacheKey,NULL_CUSTOM_POINTCUT);
			return false;
		}

		if (AnnotatedElementUtils.hasAnnotation(aClass, DataSourceComponent.class)) {
			this.attributeCache.put(cacheKey,customPointCut);
			log.info(DynamicSourceConstant.LOG_HEAD.concat("get Annotation class [{}] and get Annotation method [{}]: "), aClass.getName(), method.getName());
			return true;
		}

		if (AnnotatedElementUtils.hasAnnotation(method, DataSourceComponent.class)) {
			this.attributeCache.put(cacheKey,customPointCut);
			log.info(DynamicSourceConstant.LOG_HEAD.concat("get Annotation class [{}] and get Annotation method [{}]: "), aClass.getName(), method.getName());
			return true;
		}

		return false;
	}
}
