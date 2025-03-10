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

    // Intercept all methods in PostalCodeController
    @Around("execution(* com.example.controller.PostalCodeController.*(..))")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log the request details (method signature and arguments)
        logger.info("Request: {} with arguments = {}", 
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));

        // Execute the method and capture the response
        Object response = joinPoint.proceed();

        // Log the response details
        logger.info("Response from {}: {}", 
                joinPoint.getSignature().toShortString(),
                response);

        return response;
    }
}
