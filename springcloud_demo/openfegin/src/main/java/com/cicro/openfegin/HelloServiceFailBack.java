package com.cicro.openfegin;

import com.cicro.common.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Component
@RequestMapping("/s") //防止请求地址重复
public class HelloServiceFailBack implements HelloService {
    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String provide1(String name) {
        return "error2";
    }

    @Override
    public User test2(User user) {
        return null;
    }

    @Override
    public void delete1(Integer id) {

    }

    @Override
    public void delete3(String name) throws UnsupportedEncodingException {

    }
}
