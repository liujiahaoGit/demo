package com.cicro.provider_consul;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @GetMapping("/hello")
    public String test(String name) {
        return "provider:" + name;
    }
}
