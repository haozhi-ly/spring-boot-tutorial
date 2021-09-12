package com.ly.advice;

import com.ly.annotation.SystemLogAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName SystemLogAspect
 * @Author ly
 * @Date 2019/6/25
 **/
@Aspect
@Component
public class SystemLogAdvice {
    private static final Logger log = LoggerFactory.getLogger(SystemLogAdvice.class);

    @Pointcut("@annotation(com.ly.annotation.SystemLogAnnotation)")
    public void logPointCut(){

    }

    @Around("logPointCut() && @annotation(systemLogAnnotation)")
    public void before(ProceedingJoinPoint joinPoint, SystemLogAnnotation systemLogAnnotation) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        SystemLogAnnotation annotation = method.getAnnotation(SystemLogAnnotation.class);
        System.out.println("log :" + systemLogAnnotation.menu() + "，operation:" + systemLogAnnotation.operation());
        Object[] args = joinPoint.getArgs();
        System.out.println(args);
        Object result = joinPoint.proceed();
        System.out.println("切面执行完毕");
    }


}
