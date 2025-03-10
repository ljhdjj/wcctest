package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);

    @Before("execution(* com.example.controller.DistanceController.getDistance(..))")
    public void logDistanceRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length >= 2) {
            String postcode1 = (String) args[0];
            String postcode2 = (String) args[1];
            logger.info("Received distance request: postcode1={}, postcode2={}", postcode1, postcode2);
        }
    }
}
