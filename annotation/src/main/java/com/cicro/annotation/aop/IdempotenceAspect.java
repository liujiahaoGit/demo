package com.cicro.annotation.aop;

import com.cicro.annotation.exception.IdempotenceException;
import com.cicro.annotation.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class IdempotenceAspect {

    @Autowired
    TokenService tokenService;

    @Pointcut("@annotation(com.cicro.annotation.idempotence.Idempotence)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!tokenService.checkToken(request)){
            throw new IdempotenceException("请求重复处理"); //没有检验通过直接抛出异常
        }

    }
}
