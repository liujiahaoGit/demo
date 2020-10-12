package com.cicro.provider;

import com.cicro.api.IHelloService;
import com.cicro.common.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

@RestController
public class ProviderController implements IHelloService {

    @Value("${server.port}")
    private int port;

    @Override
    public String hello() {
        return "hello provider:" + port;
    }

    @Override
    public String provide1(String name) {
        System.out.println(new Date());
        return "hello" + name;
    }

    /**
     * 以key-value方式传参
     *
     * @param user
     * @return
     */
    @PostMapping("/user")
    public User test1(User user) {
        return user;
    }

    /**
     * 以json方式传参
     *
     * @param user
     * @return
     */
    @PostMapping("/user1")
    public User test2(@RequestBody User user) {
        System.out.println("user = " + user);
        return user;
    }

    /**
     * 以key-value方式传参
     * @param user
     * @return
     */
  /*  @PutMapping("/user3")
    public void test3(User user) {
        System.out.println("user = " + user);
    }*/

    /**
     * 以json方式传参
     *
     * @param user
     * @return
     */
    @PutMapping("/user4")
    public void test4(@RequestBody User user) {
        System.out.println("user = " + user);
    }

    /**
     * 以地址栏路径传参
     *
     * @param id
     * @return
     */
    @Override
    public void delete1(@PathVariable Integer id) {
        System.out.println("id >>>>>= " + id);
    }

    /**
     * 以key-value方式传参
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @RateLimiter(name="rla") //这里通过 @RateLimiter 注解来标记该接口限流。
    public void delete2(Integer id) {
        System.out.println("id = " + id + "  " + new Date());
    }

    @Override
    public void delete3(@RequestHeader String name) throws UnsupportedEncodingException {
        System.out.println("name = " + URLDecoder.decode(name, "UTF-8"));
    }
}
