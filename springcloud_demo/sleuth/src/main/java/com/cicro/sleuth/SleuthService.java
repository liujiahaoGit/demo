package com.cicro.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SleuthService {

    @Async
    public String test(){
        log.info("test...");
        return "test";
    }
}
