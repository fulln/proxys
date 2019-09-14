package com.fulln.proxys.life.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogApects {

	@Pointcut("execution(public int com.fulln.proxys.life.aop.MathCalculate.*(..))")
	public void pointCut(){

	}

	/**
	 *
	 */
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint){
		System.out.println(joinPoint.getSignature().getName()+"运行，参数列表是"+ Arrays.toString(joinPoint.getArgs()));
	}

	@After("pointCut()")
	public void logEnd(JoinPoint joinPoint){
		System.out.println(joinPoint.getSignature().getName()+"结束");
	}

	@AfterReturning(value = "pointCut()",returning = "result")
	public void logReturn(JoinPoint joinPoint,Object result){
		System.out.println(joinPoint.getSignature().getName()+"正常返回，运行结果是"+result);
	}

	@AfterThrowing(value = "pointCut()",throwing = "e")
	public void logException(JoinPoint joinPoint,Exception e){
		System.out.println(joinPoint.getSignature().getName()+"异常，异常是"+e.getMessage());
	}

}
