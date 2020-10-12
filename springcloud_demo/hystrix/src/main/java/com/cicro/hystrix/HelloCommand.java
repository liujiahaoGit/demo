package com.cicro.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class HelloCommand extends HystrixCommand<String> {

    RestTemplate restTemplate;

    private String name;

    public HelloCommand(Setter setter, RestTemplate restTemplate,String name) {
        super(setter);
        this.restTemplate = restTemplate;
        this.name=name;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://provider/provider1?name={1}", String.class,name);
    }

    /**
     * 请求失败时的回调
     * @return
     */
    @Override
    protected String getFallback() {
        System.out.println(getExecutionException().getMessage());
        return  "服务降级了 降级方法";
    }

    /**
     * 这里缓存是用name做缓存的 所以返回时也直接返回name
     * @return
     */
    @Override
    protected String getCacheKey() {
        return name;
    }
}
