package com.cicro.consumer_consul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;



    @GetMapping("/test")
    public String test() {
        ServiceInstance instance = loadBalancerClient.choose("provider_consul");
        System.out.println(instance.getUri());
        String s = restTemplate.getForObject(instance.getUri() + "/hello?name={1}", String.class, "zhangsan");
        return s;

    }
}
