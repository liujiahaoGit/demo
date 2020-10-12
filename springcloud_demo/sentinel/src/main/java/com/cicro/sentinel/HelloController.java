package com.cicro.sentinel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/sentinel")
    public String sentinel() {

        return "hello sentinel";
    }
}
