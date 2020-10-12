package com.cicro.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@CircuitBreaker(name = "cba", fallbackMethod = "error")
public class Res4jService {

    @Autowired
    RestTemplate restTemplate;

    public void test() {
        for (int i = 0; i < 5; i++) {
            restTemplate.delete("http://localhost:1113/delete?id={1}", i + 1);
        }
    }

    public void error(Throwable t) {

        System.out.println("error" + t.getLocalizedMessage());
    }
}
