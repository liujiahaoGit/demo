package com.cicro.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class SleuthController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test() {
        log.info("sleuth start");
        log.info("sleuth end");
        return "sleuth";
    }

    @GetMapping("/consumer")
    public String consumer() {
        log.info("consumer start");
        String s = restTemplate.getForObject("http://localhost:8080/consumer1", String.class);
        log.info("consumer end");
        return s;
    }

    @Autowired
    private SleuthService sleuthService;

    @GetMapping("/consumer1")
    public String consumer1() {
        log.info("consumer1 start");
        String s = sleuthService.test();
        log.info("consumer1 end");
        return s;
    }

    @GetMapping("/consumer2")
    @Scheduled(cron = "0/5 * * * * ?")
    public String consumer2() {
        sleuthService.test();
        log.info("consumer2...");
        return "consumer2";
    }
}
