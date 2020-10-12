package com.cicro.sentinel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@SpringBootTest
class SentinelApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate template = new RestTemplate();
        for (int i = 0; i < 15; i++) {

            String s = template.getForObject("http://localhost:8082/sentinel", String.class);
            System.out.println("s = " + s + " " + new Date());
        }
    }

}
