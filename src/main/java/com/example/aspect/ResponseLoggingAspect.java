package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLoggingAspect.class);

    @Around("execution(* com.example.controller.DistanceController.getDistance(..))")
    public Object logResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        logger.info("Response from {}: {}", joinPoint.getSignature(), result);
        return result;
    }
}
