package com.cicro.openfegin;

import com.cicro.api.IHelloService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "provider",fallbackFactory = HelloServiceFailBackFactory.class) //注册到eureka中的服务名(想要调用哪个服务)
public interface HelloService extends IHelloService {

}
