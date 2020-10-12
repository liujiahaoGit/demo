package com.cicro.openfegin;

import com.cicro.common.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class HelloServiceFailBackFactory implements FallbackFactory<HelloService> {
    @Override
    public HelloService create(Throwable throwable) {
        return new HelloService() {
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
        };
    }
}
