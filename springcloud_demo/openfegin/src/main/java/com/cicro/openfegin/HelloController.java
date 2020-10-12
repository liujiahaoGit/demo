package com.cicro.openfegin;

import com.cicro.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public void hello() throws UnsupportedEncodingException {
        helloService.hello();

        helloService.provide1("provide1");

        User user = new User();
        user.setId(1);
        user.setUsername("zhangsan");
        user.setPassword("123");
        helloService.test2(user);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("id", 2);
        map.add("username", "李四");
        map.add("password", "456");
       // helloService.test3(map);

        helloService.delete1(3);

        helloService.delete3(URLEncoder.encode("张三", "UTF-8"));
    }
}
