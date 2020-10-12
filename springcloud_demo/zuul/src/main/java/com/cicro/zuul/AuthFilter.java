package com.cicro.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthFilter extends ZuulFilter {

    /**
     * 过滤器类型 认证权限相关一般是pre
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器优先级
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;  //来自前端的所有请求均过滤
    }


    /**
       * 核心的过滤逻辑写在这里
       * @return 这个方法虽然有返回值，但是这个返回值目前无所谓 忽略
       * @throws ZuulException
       */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!"lisi".equals(username) || !"123".equals(password)){
            context.setSendZuulResponse(false);
            context.addZuulResponseHeader("content-type","text/html;charset=utf-8");
            context.setResponseBody("认证失败");
            context.setResponseStatusCode(401);

        }
        return null;
    }
}
