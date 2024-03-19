package com.edu.xmu.rag.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointCuts {
    @Pointcut("execution(public com.edu.xmu.rag.core.model.ReturnObject com.edu.xmu..controller..*.*(..))")
    public void controller() {}

    @Pointcut("execution(public * com.edu.xmu..dao..*.*(..))")
    public void dao() {}
}
