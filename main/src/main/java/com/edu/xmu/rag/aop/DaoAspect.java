package com.edu.xmu.rag.aop;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DaoAspect {
    private final Logger logger = LoggerFactory.getLogger(DaoAspect.class);

    @Around("com.edu.xmu.rag.aop.CommonPointCuts.dao()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        Object obj;

        MethodSignature ms = (MethodSignature) jp.getSignature();
        Object target = jp.getTarget();

        try {
            obj = jp.proceed();
            logger.debug("doAround: obj = {}, method = {}", target, ms.getName());
        } catch(BusinessException e){
            throw e;
        } catch (Exception exception) {
            logger.error("doAround: obj = {}, method = {}, e = {}", target, ms.getName(), exception);
            throw new BusinessException(ReturnNo.INTERNAL_SERVER_ERR, exception.getMessage());
        }
        return obj;
    }
}
