package com.cicro.openfegin;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OpenfeginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeginApplication.class, args);
    }

    @Bean
    Logger.Level level(){
        return Logger.Level.FULL;
    }

}
