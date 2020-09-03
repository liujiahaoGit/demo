package com.cicro.annotation.interceptor;

import com.cicro.annotation.idempotence.Idempotence;
import com.cicro.annotation.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/*
 * @className: IdempotenceInterceptor
 * @description 对有此注解的相关接口作拦截处理,没有此注解的接口直接放行
 * @since JDK1.8
 * @author ljh
 * @createdAt  2020/9/3 0003
 * @version 1.0.0
 **/
@Component
public class IdempotenceInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        Idempotence annotation = method.getAnnotation(Idempotence.class);
        if (annotation != null) {
            if (tokenService.checkToken(request)) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

    }
}
