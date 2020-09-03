package com.cicro.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    RedisService redisService;

    /**
     * 创建token 并存入redis
     * @return
     */
    public String createToken() {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        if (redisService.setExp(token, token, 1000L)) {
            return token;
        }
        return null;
    }

    /**
     * 校验token
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                return false;
            }
        }

        if (!redisService.exists(token)) {
            return false;
        }

        if (!redisService.remove(token)) {
            return false;
        }
        return true;
    }

}
