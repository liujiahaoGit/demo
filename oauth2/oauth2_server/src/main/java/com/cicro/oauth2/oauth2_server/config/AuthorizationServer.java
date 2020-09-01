package com.cicro.oauth2.oauth2_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/*
 * @className: AuthorizationServer
 * @description 授权服务器
 * @since JDK1.8
 * @author ljh
 * @createdAt  2020/8/25 0025
 * @version 1.0.0
 **/
@Configuration
@EnableAuthorizationServer //开启授权服务器的自动化配置
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    CustomAdditionalInformation customAdditionalInformation;

    /**
     * 用来配置令牌的存储
     *
     * @return
     */
    @Bean
    AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService());
        services.setSupportRefreshToken(true); //token是否支持刷新
        services.setTokenStore(tokenStore); //token存储位置
        TokenEnhancerChain tokenRnhancer = new TokenEnhancerChain();
        tokenRnhancer.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter,customAdditionalInformation));
        services.setTokenEnhancer(tokenRnhancer);
        return services;
    }

    /**
     * 用来配置令牌端点的安全约束，也就是这个端点谁能访问，谁不能访问。checkTokenAccess 是指一个 Token 校验的端点，
     * 这个端点我们设置为可以直接访问（在后面，当资源服务器收到 Token 之后，需要去校验 Token 的合法性，就会访问这个端点）。
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()")
            .allowFormAuthenticationForClients();
    }

    /**
     * 用来配置客户端的详细信息
     * 授权服务器要做两方面的检验，一方面是校验客户端，另一方面则是校验用户，
     * 校验用户，我们前面已经配置了，这里就是配置校验客户端。客户端的信息我们可以存在数据库中，
     * 这其实也是比较容易的，和用户信息存到数据库中类似，但是这里为了简化代码，我还是将客户端信息存在内存中，
     * 这里我们分别配置了客户端的 id，secret、资源 id、授权类型、授权范围以及重定向 uri。授权类型一共有四种，
     * 四种之中不包含 refresh_token 这种类型，但是在实际操作中，refresh_token 也被算作一种
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.inMemory()
            .withClient("javaboy")
            .secret(new BCryptPasswordEncoder().encode("123"))
            .resourceIds("res1")
            .authorizedGrantTypes("refresh_token", "authorization_code", "implicit", "password",
                "client_credentials") //支持授权码模式 简化模式 密码模式 客户端模式
            .scopes("all")
            .autoApprove(true)  //登录后不用手动点击授权 直接可以访问资源服务器
            .redirectUris("http://localhost:8082/login.html");*/

        //客户端的信息存在数据库中
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 用来配置令牌的访问端点和令牌服务
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
            .authenticationManager(authenticationManager)
            .tokenServices(tokenServices());
    }

    /**
     * 用来配置授权码的存储，这里我们是存在在内存中
     *
     * @return
     */
    @Bean
    AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Bean
    ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
}
