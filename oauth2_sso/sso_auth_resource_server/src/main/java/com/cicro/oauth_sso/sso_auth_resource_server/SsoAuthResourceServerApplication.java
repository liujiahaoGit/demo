package com.cicro.oauth_sso.sso_auth_resource_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class SsoAuthResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoAuthResourceServerApplication.class, args);
    }

}
