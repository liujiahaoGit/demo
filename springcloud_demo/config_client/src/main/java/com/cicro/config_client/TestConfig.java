package com.cicro.config_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RefreshScope
public class TestConfig {


    @Value("${active}")
    String active;

    @GetMapping("/test")
    public String test(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        return active;
    }
}
