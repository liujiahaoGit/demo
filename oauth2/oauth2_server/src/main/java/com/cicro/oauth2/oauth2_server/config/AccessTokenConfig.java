package com.cicro.oauth2.oauth2_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AccessTokenConfig {

    /*@Autowired
    RedisConnectionFactory redisConnectionFactory;*/

    //在 JWT 字符串生成的时候，我们需要一个签名，这个签名需要自己保存好。
    private static final String SIGNING_KEY = "javaboy";

    /**
     * TokenStore 我们使用 JwtTokenStore 这个实例。之前我们将 access_token 无论是存储在内存中，
     * 还是存储在 Redis 中，都是要存下来的，客户端将 access_token 发来之后，我们还要校验看对不对。
     * 但是如果使用了 JWT，access_token 实际上就不用存储了（无状态登录，服务端不需要保存信息），
     * 因为用户的所有信息都在 jwt 里边，所以这里配置的 JwtTokenStore 本质上并不是做存储。
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
        JwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //签名
        return converter;
    }

}
