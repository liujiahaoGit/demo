package com.cicro.provider_consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderConsulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderConsulApplication.class, args);
    }

}
