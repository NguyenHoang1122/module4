package com.customermanageaspect.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class Logger {
    public void error() {
        System.out.println("[CMS] ERROR!");
    }

    @AfterThrowing(pointcut = "execution(public * com.customermanageaspect.service.CustomerService.findAll(..))")
    public void log() {
    }

    @AfterThrowing(pointcut = "execution(public * com.customermanageaspect.service.CustomerService.*(..))", throwing = "e")
    public void logClass(JoinPoint joinPoint, Exception e) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        System.out.printf("[CMS] co loi xay ra: %s.%s%s: %s%n", className, method, args, e.getMessage());
    }
}
