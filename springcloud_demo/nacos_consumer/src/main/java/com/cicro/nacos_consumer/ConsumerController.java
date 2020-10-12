package com.cicro.nacos_consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {


    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String consumer(){

        return restTemplate.getForObject("http://nacos/get",String.class);
    }
}
