package com.cicro.oauth2_sso.client1;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}