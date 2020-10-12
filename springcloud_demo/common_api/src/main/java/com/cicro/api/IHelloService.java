package com.cicro.api;

import com.cicro.common.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

public interface IHelloService {

    @GetMapping("/provider")//调用该服务中的接口名
    String hello();

    @GetMapping("/provider1")
    String provide1(@RequestParam("name") String name);

    @PostMapping("/user1")
    User test2(@RequestBody User user);

    @DeleteMapping("/delete/{id}")
    void delete1(@PathVariable("id") Integer id);

    @DeleteMapping("/delete3")
    void delete3(@RequestHeader("name") String name) throws UnsupportedEncodingException;
}
