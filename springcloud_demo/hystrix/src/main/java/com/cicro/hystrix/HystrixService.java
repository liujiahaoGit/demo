package com.cicro.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class HystrixService {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 在这个方法中，我们将发起一个远程调用，去调用 provider 中提供的 /provider测试接口
     * 但是，这个调用可能会失败。
     * 我们在这个方法上添加 @HystrixCommand 注解，配置 fallbackMethod 属性，这个属性表示该方法调用失败时的临时替代方法
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "error", ignoreExceptions = ArithmeticException.class)
    public String hystrix() {
        int i = 1 / 0;
        return restTemplate.getForObject("http://provider/provider", String.class);
    }

    @HystrixCommand(fallbackMethod = "error")
    public Future<String> hystrix1() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return restTemplate.getForObject("http://provider/provider", String.class);
            }
        };
    }

    /**
     * 注意，这个方法名字要和 fallbackMethod一致 方法返回值也要和对应的方法一致
     * 当然,如果有需要,也可以在这降级的方法上在进行降级
     * 添加 @HystrixCommand(fallbackMethod = "xxx")  然后准备xxx方法即可 以此类推
     *
     * @return
     */
    public String error(Throwable t) {
        System.out.println(t.getMessage());
        return "服务降级了 预备页面";
    }

    /**
     * @param name
     * @return
     * @CacheResult 这个注解表示该方法的请求结果会被缓存起来，默认情况下，缓存的 key 就是方法的参数，缓存的 value 就是方法的返回值。
     */
    @HystrixCommand(fallbackMethod = "error1")
    @CacheResult
    public String hystrix2(String name) {

        return restTemplate.getForObject("http://provider/provider1?name={1}", String.class, name);

    }

    @HystrixCommand(fallbackMethod = "error1")
    @CacheRemove(commandKey = "hystrix2")
    public String deleteHystrix(String name) {
        return null;

    }

    public String error1(String name, Throwable t) {
        System.out.println(t.getMessage());
        return "服务降级了 预备页面";
    }

}
