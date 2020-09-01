package com.cicro.oauth2.oauth2_resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer //开启资源服务器的自动化配置
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;

    /**
     * 配置了一个 RemoteTokenServices 的实例，这是因为资源服务器和授权服务器是分开的，
     * 资源服务器和授权服务器是放在一起的，就不需要配置 RemoteTokenServices 了
     * RemoteTokenServices 中我们配置了 access_token 的校验地址、client_id、client_secret 这三个信息，
     * 当用户来资源服务器请求资源时，会携带上一个 access_token，通过这里的配置，就能够校验出 token 是否正确等。
     *
     * @return
     */
    /*@Bean
    RemoteTokenServices tokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        services.setClientId("javaboy");
        services.setClientSecret("123");
        return services;
    }*/

    /**
     * 这里配置好之后，会自动调用 JwtAccessTokenConverter 将 jwt 解析出来，
     * jwt 里边就包含了用户的基本信息，所以就不用远程校验 access_token 了。
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("res1").tokenStore(tokenStore);
    }

    /**
     * 配置security拦截规则
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**").hasRole("admin")
            .anyRequest().authenticated()
            .and().cors(); //使security支持跨域
    }
}
