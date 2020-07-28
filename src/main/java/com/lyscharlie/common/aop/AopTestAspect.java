package com.lyscharlie.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lyscharlie.common.annotation.EagleEye;
import com.lyscharlie.common.annotation.RetryMethod;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class AopTestAspect {

	// 指定注解类型
	@Pointcut("@annotation(com.lyscharlie.common.annotation.EagleEye)")
	public void eagleEye() {

	}

	@Around("eagleEye()")
	public Object doAroundAccessCheck(ProceedingJoinPoint pjp) throws Throwable {

		log.info("aop around start");

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		EagleEye eagleEye = method.getAnnotation(EagleEye.class);
		log.info("eagleEye desc is {}", eagleEye.desc());
		log.info("request method is {}.{}", signature.getDeclaringTypeName(), signature.getName());

		Object retVal = pjp.proceed();

		stopWatch.stop();

		log.info("invoke method: " + pjp.getSignature().getName() + ", elapsed time: " + stopWatch.getTotalTimeMillis());

		log.info("aop around finish");

		return retVal;
	}

	@Around("@annotation(com.lyscharlie.common.annotation.RetryMethod)")
	public Object doRetry(ProceedingJoinPoint pjp) throws Throwable {
		log.info("aop after doRetry start");

		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		RetryMethod retryMethod = method.getAnnotation(RetryMethod.class);

		int times = retryMethod.times();
		int runTimes = 0;

		Object returnValue;

		do {

			returnValue = pjp.proceed();
			runTimes++;

			log.info("runTimes is {}", runTimes);

			if (returnValue instanceof Boolean) {
				boolean value = ((Boolean) returnValue).booleanValue();
				if (value) {
					break;
				}
			}else{
				break;
			}

		} while (runTimes <= times);

		log.info("aop after doRetry finish");

		return returnValue;
	}

	// 匹配包中的指定类的所有方法
	@Pointcut("execution(* com.lyscharlie.biz.service.UserService.*(..))")
	public void pointcut1() {

	}

	@Before("pointcut1() && args(userName, password)")
	public void doSomething1(String userName, String password) {
		log.info("userName=[{}], password=[{}]", userName, password);
	}

	// 匹配指定包中的所有的方法, 包括子包
	@Pointcut("within(com.lyscharlie.biz.service..*)")
	public void pointcut2() {

	}

	@After("pointcut2()")
	public void doSomething2(JoinPoint joinPoint) {
		log.info("after doing: {}", joinPoint.getSignature().toString());
	}

	// pointcut与advice合并配置，匹配以指定名字结尾的 Bean 中的所有方法
	@Around("bean(*Controller)")
	public Object doSomething3(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();

		log.info("请求连接：{}", request.getRequestURL().toString());
		log.info("请求类型：{}", request.getMethod());
		log.info("请求IP：{}", request.getRemoteAddr());
		// try {
		// 	boolean isFile = false;
		// 	for (Object arg : pjp.getArgs()) {
		// 		if (arg instanceof MultipartFile) {
		// 			isFile = true;
		// 			break;
		// 		}
		// 	}
		// 	if (!isFile) {
		// 		log.info("请求入参：{}", JSON.toJSONString(pjp.getArgs()));
		// 	}
		// } catch (Exception e) {
		// 	log.error("AopTestAspect.doSomething3", e);
		// }

		Object result = pjp.proceed();

		// try {
		// 	log.info("请求返回：{}", JSONObject.toJSONString(result));
		// } catch (Exception e) {
		// 	log.error("AopTestAspect.doSomething3", e);
		// }

		return result;
	}
}
