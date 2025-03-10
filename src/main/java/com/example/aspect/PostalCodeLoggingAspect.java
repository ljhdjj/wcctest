package com.example.aspect;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PostalCodeLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(PostalCodeLoggingAspect.class);

    @Around("execution(* com.example.controller.PostalCodeController.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Request: {} with arguments = {}", 
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        Object response = joinPoint.proceed();

        logger.info("Response from {}: {}", 
                joinPoint.getSignature().toShortString(),
                response);

        return response;
    }
}
