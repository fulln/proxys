package com.fulln.proxys.life.config;


import com.fulln.proxys.life.aop.LogApects;
import com.fulln.proxys.life.aop.MathCalculate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * aop：
 *   在程序运行的过程中注入指定切面中；
 *
 * 原理：【给容器注册了啥组件，这个组件是啥时候工作的，功能是啥】
 *  1。 @EnableAspectJAutoProxy
 *     @Import(AspectJAutoProxyRegistrar.class)  给容器导入AspectJAutoProxyRegistrar
 *     利用AspectJAutoProxyRegistrar 自定义容器中注册bean
 *     internalAutoProxyCreator =  AnnotationAwareAspectJAutoProxyCreator
 *     给容器中注册一个 AnnotationAwareAspectJAutoProxyCreator  ：
 * 2。 AnnotationAwareAspectJAutoProxyCreator
 *             -> AspectJAwareAdvisorAutoProxyCreator
 *             -> AbstractAdvisorAutoProxyCreator
 *              ->AbstractAutoProxyCreator extends ProxyProcessorSupport
 * 		implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                  关注后置处理器（在bena初始化完成前后做的工作），beanfactory
 *
 *  AnnotationAwareAspectJAutoProxyCreator. initBeanFactory()
 *
 *
 *  AbstractAdvisorAutoProxyCreator.setBeanFactory() => .initBeanFactory()
 *
 *
 *  AbstractAutoProxyCreator.setBeanFactory
 *  AbstractAutoProxyCreator 后置处理器的相关方法
 *
 *
 *  流程：
 *   1， 传入配置类，创建ioc容器
 *   2。 注册配置类，调用refresh 刷新容器
 *   3。registerBeanPostProcessors(beanFactory);  注册bean的后置处理器，来拦截bean的创建
 *     1。 先获取ioc中已经定义了的beanPostProcessor
 *     2。  给容器中添加别的beanPostProcessor
 *     3.  优先注册 PriorityOrdered接口的beanPostprocessor
 *     // First, register the BeanPostProcessors that implement PriorityOrdered.
 *     4。再给容器中注册了
 *     // Next, register the BeanPostProcessors that implement Ordered.
 *     5。  Now, register all regular BeanPostProcessors.
 *     6。  注册BeanPostProcessor ，实际上就是创建对象，保存；
 *           1。  创建bean 实例
 *           2。  populateBean(beanName, mbd, instanceWrapper)；  给 bean属性赋值
 *           3。  initializeBean(beanName, exposedObject, mbd);  初始化bean
 *             1。invokeAwareMethods(beanName, bean);  处理aware接口中的方法
 *             2。applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); 应用后置处理器的before方法
 *             3。invokeInitMethods(beanName, wrappedBean, mbd);执行初始化方法
 *             4。 applyBeanPostProcessorsAfterInitialization 应用后置处理器的after方法方法
 *           4。 BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator) 创建成功
 *      7。 beanFactory.addBeanPostProcessor(postProcessor);  把 BeanPostProcessor 注册到beanFactory中
 * ==========以上是创建和注册     AnnotationAwareAspectJAutoProxyCreator的过程
 *   4。finishBeanFactoryInitialization 完成beanfactory的初始化工作，创建剩下的单例
 *     1。  遍历获取容器中所有的bean  依次创建对象 getBean(beanName);
 *     getBean ->doGetBean -> getSingleton
 *     2。创建bean
 *
 *     【AnnotationAwareAspectJAutoProxyCreator  在所有的bean创建前有拦截，InstantiationAwareBeanPostProcessor 调用postProcessBeforeInstantiation方法】
 *      1。 先从缓存中获取当前bean，如果能获取，说明是之前创建过的 否则再创建，只要创建好的bean 都会被缓存
 *      2。 createBean  创建bean
 *      【BeanPostProcessor 是在bean 创建完成前后调用的】
 *      【InstantiationAwareBeanPostProcessor 是在bean 创建前尝试使用后置处理器返回的对象】
 *        1。 resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) 解析BeforeInstantiation
 *          希望后置处理器返回代理对象； 如果能返回代理对象就使用， 如果不能
 *          1。后置处理器返回对象
 *            				if (targetType != null) {
 * 					bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 * 					if (bean != null) {
 * 						bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                                        }                * 				}
 *
 *              拿到所有的后置处理器，如果后置处理器是  InstantiationAwareBeanPostProcessor  就执行后置处理器的 postProcessBeforeInstantiation 方法
 *        2。 doCreateBean(beanName, mbdToUse, args) ； 创建bean实例， 和之前3。6的流程相同
 *
 * AnnotationAwareAspectJAutoProxyCreator的作用
 *       1。每个bean创建之前 都会调用postProcessBeforeInstantiation()
 *        关注自定义的2个bean的创建：
 *         1。 判断当前bean  是否advisedBeans中（保存了所有需增强bean）
 *         2。 判断当前bean 是不是 infrastructure class  如 Advice ，Pointcut，Advisor，AopInfrastructureBean
 *           或者  aspectJAdvisorFactory.isAspect 是不是切面
 *         3。判断是否需要跳过
 *          1。 获取增强器   List<Advisor> candidateAdvisors 每个封装的方法的增强器
 *          2。返回父类判断 false
 *
 *        2。创建对象，
 *         调用之后的方法  postProcessAfterInitialization
 *         return wrapIfNecessary(bean, beanName, cacheKey) //包装，如果需要的话
 *         1。 获取当前bean的所有增强器（通知方法）
 *          1。找到在所有使用的增强器（找那些通知方法是需要切入当前bean方法的）
 *          2。获取到能在当前bean使用的增强器
 *          3。给增强器排序
 *         2。保存当前bean 到adviseBean中
 *         3。如果当前bean需要增强，创建当前bean的代理对象
 *          1。获取所有的增强器（通知方法）
 *          2。 保存到proxyFactory中
 *          3。 创建代理对象  spring 自动决定
 *            ObjenesisCglibAopProxy
 *            JdkDynamicAopProxy
 *          4。给容器中返回当前组件使用的cglib代理对象
 *          5。以后容器中获得到的就是这个代理对象，执行目标方法的时候，就会执行通知方法的流程

 *
 *
 *
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

	@Bean
	public MathCalculate mathCalculate(){
		return new MathCalculate();
	}

	@Bean
	public LogApects logApects(){
		return new LogApects();
	}


}
