package com.cicro.hystrix;

import com.cicro.common.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand
    public List<User> getUsers(List<Integer> ids) {
        User[] users = restTemplate.getForObject("http://provider/user/{1}", User[].class, StringUtils.join(ids,
            ","));
        return Arrays.asList(users);
    }

    @HystrixCollapser(batchMethod = "getUsers",collapserProperties = {@HystrixProperty(name="timerDelayInMilliseconds",value = "200")})
    public Future<User> getUser(Integer id){
        return null;
    }
}
