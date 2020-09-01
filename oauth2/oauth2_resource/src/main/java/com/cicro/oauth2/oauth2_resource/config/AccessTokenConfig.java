package com.cicro.oauth2.oauth2_resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AccessTokenConfig {

    /*@Autowired
    RedisConnectionFactory redisConnectionFactory;*/

    private static final String SIGNING_KEY = "javaboy";

    /**
     * 将生成的token存入redis中
     *
     * @return
     */
    @Bean
    TokenStore tokenStore() {
        //将生成的token存入内存中
        //return new InMemoryTokenStore();
        // return new RedisTokenStore(redisConnectionFactory);

        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 这个 JwtAccessTokenConverter 可以实现将用户信息和 JWT 进行转换
     * （将用户信息转为 jwt 字符串，或者从 jwt 字符串提取出用户信息）。
     * @return
     */
    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //签名
        return converter;
    }

}
