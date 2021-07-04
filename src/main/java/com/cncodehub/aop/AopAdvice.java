package com.cncodehub.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopAdvice {
    /**
     * execytion :在方法执行时触发
     * * 返回任意类型
     * com.cncodehub.controller包路径
     * .* 任意类
     * .* 任意方法
     * (..) 任意参数
     */
    @Pointcut("execution(* com.cncodehub.controller.*.*(..))")
    public void test(){

    }

    @Before("test()")
    public void beforeAdvice(){
        System.out.println("beforeAdvice");
    }

    @After("test()")
    public void afterAdvice(){
        System.out.println("afterAdvice");
    }


    /**
     * Around可以决定目标方法在什么时候执行，如何执行，甚至可以完全阻止目标方法的执行。
     * 在@Around 方法体内，调用ProceedingJoinPoint的proceed()方法才会执行目标方法，
     * 但一定要记得返回目标方法执行的结果，否则前端是获取不到结果的
     * @param proceedingJoinPoint
     * @return
     */
    @Around("test()")
    public Object aroudAdvice(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("before");
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after");
        return object;
    }

}

