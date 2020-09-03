package com.cicro.annotation.controller;

import com.cicro.annotation.idempotence.Idempotence;
import com.cicro.annotation.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TokenService tokenService;

    @Idempotence
    @PostMapping("/test1")
    public String test1(){
        return "111";
    }

    @PostMapping("/test2")
    public String test2(){
        return "222";
    }

    @GetMapping("/getToken")
    public String getToken(){
        return tokenService.createToken();
    }
}
