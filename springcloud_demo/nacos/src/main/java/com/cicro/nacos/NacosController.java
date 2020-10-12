package com.cicro.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class NacosController {

    @Autowired
    ApplicationContext applicationContext;

    @Value("${name}")
    private String name;

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/get")
    public String nacos() {

        String property = applicationContext.getEnvironment().getProperty("server.port");
        System.out.println(property);

        return name+port;
    }
}
