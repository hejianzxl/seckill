package org.seckill.core.DynamicDataSource;

import java.sql.SQLException;
import java.util.Random;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
@Order(-1)//
public class DataSourceAspect {
	// 配置切入点
	@Pointcut("execution(* org.seckill.core.controller..*.*(..))")
	public void aspect() {
	}

	/**
	 * 配置前置通知注册的切入点
	 * @throws SQLException 
	 */
	@Before("aspect()")
	public void before(JoinPoint point) throws SQLException {
		point.getArgs();
		point.getSignature().getName();
		Random random = new Random(); 
		int r = random.nextInt(100);
		String [] ds = {"dataSoruce1","dataSoruce2"};
		DynamicDataSourceHolder.putDataSource(ds[r%2]);
	}

	@After("aspect()")
	public void after(JoinPoint point) {
		System.out.println("after==========");
	}
}
