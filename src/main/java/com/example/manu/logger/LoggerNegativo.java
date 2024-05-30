package com.example.manu.logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerNegativo {

    private static final Logger logger = LoggerFactory.getLogger(LoggerNegativo.class);

    @Pointcut("execution(* com.example.manu.controller.NaveController.getNaveById(..)) && args(id)")
    public void getNaveByIdPointcut(Long id) {}

    @Before("getNaveByIdPointcut(id)")
    public void logBefore(Long id) {
        if (id < 0) {
            logger.warn("Han intentado buscar una Nave con un ID negativo: {}", id);
        }
    }}
