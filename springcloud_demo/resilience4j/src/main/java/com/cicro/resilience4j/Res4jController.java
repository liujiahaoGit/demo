package com.cicro.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Res4jController {

    @Autowired
    Res4jService res4jService;


    @GetMapping("/test")
    public void test(){
        res4jService.test();
    }
}
