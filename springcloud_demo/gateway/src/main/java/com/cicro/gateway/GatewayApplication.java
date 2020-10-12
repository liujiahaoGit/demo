package com.cicro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

   /* @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
            .route("route",r->r.path("/get").uri("http://httpbin.org"))
            .build();
    }*/

}
